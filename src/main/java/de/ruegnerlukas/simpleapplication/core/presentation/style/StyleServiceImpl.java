package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Node;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
public class StyleServiceImpl implements StyleService {


	/**
	 * All registered {@link Style}s. The name of the registered style is the key.
	 */
	private Map<String, Style> styles = new HashMap<>();

	/**
	 * All nodes and their applied styles.
	 */
	private Map<Node, List<Style>> targets = new HashMap<>();




	@Override
	public Style createFromResource(final String name, final Resource resource) {
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		Validations.INPUT.notNull(resource).exception("The resource can not be null.");
		final Style style = Style.fromResource(resource);
		registerStyle(style, name);
		return style;
	}




	@Override
	public Style createFromString(final String name, final String... string) {
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		Validations.INPUT.notNull(string).exception("The style strings can not be null.");
		final Style style = Style.fromString(string);
		registerStyle(style, name);
		return style;
	}




	@Override
	public void registerStyle(final Style style, final String name) {
		Validations.INPUT.notNull(style).exception("The style can not be null.");
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		if (styles.containsKey(name)) {
			final List<Node> targets = getTargets(name);
			deregisterStyle(name);
			styles.put(name, style);
			targets.forEach(target -> applyStyleTo(name, target));
		} else {
			styles.put(name, style);
		}
	}




	@Override
	public void deregisterStyle(final String name) {
		getTargets(name).forEach(target -> removeStyleFrom(name, target));
		styles.remove(name);
	}




	@Override
	public void applyStyleTo(final String name, final Node target) {
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		Validations.INPUT.notNull(target).exception("The target node can not be null.");
		final Optional<Style> styleOptional = findStyle(name);
		styleOptional.ifPresentOrElse(style -> {
			style.applyTo(target);
			targets.computeIfAbsent(target, k -> new ArrayList<>()).add(style);
		}, () -> log.warn("The style '{}' does not exist and will not be applied to the given target.", name));
	}




	@Override
	public void applyStylesTo(final List<String> names, final Node target) {
		Validations.INPUT.notNull(names).exception("The names can not be null.");
		Validations.INPUT.notNull(target).exception("The target node can not be null.");
		names.forEach(name -> applyStyleTo(name, target));
	}




	@Override
	public void applyStyleToExclusive(final String name, final Node target) {
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		Validations.INPUT.notNull(target).exception("The target node can not be null.");
		final Optional<Style> styleOptional = findStyle(name);
		styleOptional.ifPresentOrElse(style -> {
			style.applyExclusive(target);
			targets.put(target, new ArrayList<>(List.of(style)));
		}, () -> log.warn("The style '{}' does not exist and will not be applied to the given target.", name));
	}




	@Override
	public void removeStyleFrom(final String name, final Node target) {
		Validations.INPUT.notBlank(name).exception("The name can not be null or empty.");
		Validations.INPUT.notNull(target).exception("The target node can not be null.");
		final Optional<Style> styleOptional = findStyle(name);
		styleOptional.ifPresentOrElse(style -> {
			style.removeFrom(target);
			targets.computeIfAbsent(target, k -> new ArrayList<>()).remove(style);
		}, () -> log.warn("The style '{}' does not exist and will not be removed from the given target.", name));
	}




	@Override
	public void disconnectNode(final Node node) {
		getAppliedStyles(node).forEach(style -> style.removeFrom(node));
		targets.remove(node);
	}




	@Override
	public void reloadStyle(final String name) {
		final Optional<Style> styleOptional = findStyle(name);
		styleOptional.ifPresentOrElse(style -> {
			final List<Node> targets = getTargets(name);
			targets.forEach(target -> {
				style.removeFrom(target);
				style.applyTo(target);
			});
		}, () -> log.warn("The style '{}' does not exist and can not be reloaded.", name));
	}




	@Override
	public void reloadAll() {
		targets.forEach((target, styles) -> {
			styles.forEach(style -> style.removeFrom(target));
			styles.forEach(style -> style.applyTo(target));
		});
	}




	@Override
	public Optional<Style> findStyle(final String name) {
		return Optional.ofNullable(styles.get(name));
	}




	@Override
	public List<Style> getAppliedStyles(final Node target) {
		return Optional.ofNullable(targets.get(target)).orElse(List.of());
	}




	@Override
	public List<String> getAppliedStyleNames(final Node target) {
		return getAppliedStyles(target).stream().map(this::getNameOfStyle).collect(Collectors.toList());
	}




	/**
	 * @param style the {@link Style}
	 * @return the name of the registered style.
	 */
	private String getNameOfStyle(final Style style) {
		return styles.entrySet().stream()
				.filter(entry -> entry.getValue().equals(style))
				.map(Map.Entry::getKey)
				.findFirst().orElse(null);
	}




	@Override
	public List<Node> getTargets(final String styleName) {
		final List<Node> nodes = new ArrayList<>();
		final Optional<Style> styleOptional = findStyle(styleName);
		styleOptional.ifPresent(style -> targets.forEach((node, styles) -> {
			if (styles.contains(style)) {
				nodes.add(node);
			}
		}));
		return nodes;
	}


}
