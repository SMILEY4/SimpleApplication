package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChronologyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectableItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectableItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectionIndexMarker;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LayoutProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SearchableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpinnerFactoryProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabClosingPolicyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabDisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabPaneMenuSideProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabTitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.Side;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.util.StringConverter;

import java.time.chrono.Chronology;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Properties {


	/**
	 * Hidden constructor of utility class.
	 */
	private Properties() {
		// do nothing
	}




	/**
	 * Checks whether the given properties are valid.
	 * Throws an exception if the list contains illegal properties
	 * (not registered ad the {@link de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry}).
	 *
	 * @param nodeType   the type of the node
	 * @param properties the given properties to check
	 */
	public static void validate(final Class<?> nodeType, final SuiProperty... properties) {
		validate(nodeType, SuiRegistry.get().getEntry(nodeType).getProperties(), properties);
	}




	/**
	 * Checks whether the given properties are valid.
	 * Throws an exception if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	public static void validate(final Class<?> nodeType, final Set<Class<? extends SuiProperty>> allowedProperties,
								final SuiProperty... properties) {
		final List<Class<? extends SuiProperty>> propKeyList = propertiesAsKeyList(properties);
		final Set<Class<? extends SuiProperty>> propKeySet = new HashSet<>(propKeyList);
		checkIllegal(nodeType, allowedProperties, propKeySet);
		checkDuplicates(propKeyList);
		checkConflicting(propKeySet);
	}




	/**
	 * @param properties the properties
	 * @return a list of keys of all the given properties.
	 */
	private static List<Class<? extends SuiProperty>> propertiesAsKeyList(final SuiProperty... properties) {
		return Stream.of(properties)
				.map(SuiProperty::getKey)
				.collect(Collectors.toList());
	}




	/**
	 * Checks whether the given properties are allowed.
	 * Throws a validation exception if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	private static void checkIllegal(final Class<?> nodeType,
									 final Set<Class<? extends SuiProperty>> allowedProperties,
									 final Set<Class<? extends SuiProperty>> properties) {
		Set<Class<? extends SuiProperty>> illegal = findIllegal(allowedProperties, properties);
		if (!illegal.isEmpty()) {
			final String message =
					"Illegal properties for "
							+ nodeType.getSimpleName()
							+ " ["
							+ properties.stream().map(Class::getSimpleName).collect(Collectors.joining(", "))
							+ "]";
			Validations.STATE.fail().exception(message);
		}
	}




	/**
	 * Finds all illegal properties.
	 *
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the list of properties to check
	 * @return the list of illegal properties.
	 */
	private static Set<Class<? extends SuiProperty>> findIllegal(final Set<Class<? extends SuiProperty>> allowedProperties,
																 final Set<Class<? extends SuiProperty>> properties) {
		final Set<Class<? extends SuiProperty>> illegal = new HashSet<>();
		properties.forEach(property -> {
			if (!allowedProperties.contains(property)) {
				illegal.add(property);
			}
		});
		return illegal;
	}




	/**
	 * Checks that no property type was added more than once.
	 * Throws a validation exception if the list contains duplicate properties.
	 *
	 * @param properties the given properties to check
	 */
	private static void checkDuplicates(final List<Class<? extends SuiProperty>> properties) {
		final Set<Class<? extends SuiProperty>> items = new HashSet<>();
		final List<Class<? extends SuiProperty>> duplicates = properties.stream()
				.filter(p -> !items.add(p))
				.collect(Collectors.toList());
		if (!duplicates.isEmpty()) {
			final String messageList = duplicates.stream().map(Object::toString).collect(Collectors.joining(", "));
			Validations.STATE.fail().exception("Duplicate properties: [" + messageList + "]");
		}
	}




	/**
	 * Check if any properties in the given list conflict with any other of the properties.
	 * Throws a validation exception if a conflict was found.
	 *
	 * @param properties the properties to check
	 */
	private static void checkConflicting(final Set<Class<? extends SuiProperty>> properties) {
		if (properties.contains(ItemProperty.class) && properties.contains(ItemListProperty.class)) {
			Validations.STATE.fail().exception("Conflicting Properties: \"ItemProperty\" and \"ItemListProperty\"");
		}
	}




	/**
	 * @param id the id of the element. Unique among the siblings.
	 * @return an {@link IdProperty}.
	 */
	public static SuiProperty id(final String id) {
		Validations.INPUT.notEmpty(id).exception("Id can not be null or empty.");
		return new IdProperty(id);
	}




	/**
	 * @param minWidth        the minimum width.
	 * @param minHeight       the minimum height.
	 * @param preferredWidth  the preferred width.
	 * @param preferredHeight the preferred height.
	 * @param maxWidth        the maximum width.
	 * @param maxHeight       the maximum height.
	 * @return a {@link SizeProperty}. Has lower priority than the other size properties.
	 */
	public static SuiProperty size(final Number minWidth, final Number minHeight,
								   final Number preferredWidth, final Number preferredHeight,
								   final Number maxWidth, final Number maxHeight) {
		Validations.INPUT.notNull(minWidth).exception("The min width can not be null.");
		Validations.INPUT.notNull(minHeight).exception("The min height can not be null.");
		Validations.INPUT.notNull(preferredWidth).exception("The preferred width can not be null.");
		Validations.INPUT.notNull(preferredHeight).exception("The preferred height can not be null.");
		Validations.INPUT.notNull(maxWidth).exception("The max width can not be null.");
		Validations.INPUT.notNull(maxHeight).exception("The max height can not be null.");
		return new SizeProperty(minWidth, minHeight, preferredWidth, preferredHeight, maxWidth, maxHeight);
	}




	/**
	 * @param width  the minimum width.
	 * @param height the minimum height.
	 * @return a {@link SizeMinProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static SuiProperty minSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The min width can not be null.");
		Validations.INPUT.notNull(height).exception("The min height can not be null.");
		return new SizeMinProperty(width, height);
	}




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 * @return a {@link SizePreferredProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static SuiProperty preferredSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The preferred width can not be null.");
		Validations.INPUT.notNull(height).exception("The preferred height can not be null.");
		return new SizePreferredProperty(width, height);
	}




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 * @return a {@link SizeMaxProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static SuiProperty maxSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The max width can not be null.");
		Validations.INPUT.notNull(height).exception("The max height can not be null.");
		return new SizeMaxProperty(width, height);
	}




	/**
	 * @return a {@link DisabledProperty} with disabled set to true.
	 */
	public static SuiProperty disabled() {
		return disabled(true);
	}




	/**
	 * @param disabled whether the element is disabled.
	 * @return a {@link DisabledProperty}.
	 */
	public static SuiProperty disabled(final boolean disabled) {
		return new DisabledProperty(disabled);
	}




	/**
	 * @return a {@link WrapTextProperty} with the wrap-text set to true.
	 */
	public static SuiProperty wrapText() {
		return wrapText(true);
	}




	/**
	 * @param wrap whether the text content should be wrapped.
	 * @return a {@link WrapTextProperty}.
	 */
	public static SuiProperty wrapText(final boolean wrap) {
		return new WrapTextProperty(wrap);
	}




	/**
	 * @param text the text content.
	 * @return a {@link TextContentProperty}.
	 */
	public static SuiProperty textContent(final String text) {
		Validations.INPUT.notNull(text).exception("The text can not be null.");
		return new TextContentProperty(text);
	}




	/**
	 * @param text the prompt text.
	 * @return a {@link TextContentProperty}.
	 */
	public static SuiProperty promptText(final String text) {
		Validations.INPUT.notNull(text).exception("The prompt text can not be null.");
		return new PromptTextProperty(text);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static SuiProperty items(final NodeFactory... items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static SuiProperty items(final Collection<NodeFactory> items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static SuiProperty items(final Stream<NodeFactory> items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		return new ItemListProperty(items);
	}




	/**
	 * @param factory the factory for the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static SuiProperty items(final ItemListProperty.ItemListFactory factory) {
		Validations.INPUT.notNull(factory).exception("The factory can not be null.");
		return new ItemListProperty(factory);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static SuiProperty itemsInjectable(final String injectionPointId) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		return new InjectableItemListProperty(injectionPointId);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            factories for default items
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static SuiProperty itemsInjectable(final String injectionPointId,
											  final InjectionIndexMarker indexMarker,
											  final NodeFactory... items) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(indexMarker).exception("The index marker can not be null.");
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new InjectableItemListProperty(injectionPointId, indexMarker, items);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            factories for default items
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static SuiProperty itemsInjectable(final String injectionPointId,
											  final InjectionIndexMarker indexMarker,
											  final Collection<NodeFactory> items) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(indexMarker).exception("The index marker can not be null.");
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new InjectableItemListProperty(injectionPointId, indexMarker, items);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            factories for default items
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static SuiProperty itemsInjectable(final String injectionPointId,
											  final InjectionIndexMarker indexMarker,
											  final Stream<NodeFactory> items) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(indexMarker).exception("The index marker can not be null.");
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		return new InjectableItemListProperty(injectionPointId, indexMarker, items);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param factory          the factory for the factories for default child items.
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static SuiProperty itemsInjectable(final String injectionPointId,
											  final InjectionIndexMarker indexMarker,
											  final ItemListProperty.ItemListFactory factory) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(indexMarker).exception("The index marker can not be null.");
		Validations.INPUT.notNull(factory).exception("The factory can not be null.");
		return new InjectableItemListProperty(injectionPointId, indexMarker, factory);
	}




	/**
	 * @param item the factory for the child item.
	 * @return an {@link ItemProperty}.
	 */
	public static SuiProperty item(final NodeFactory item) {
		Validations.INPUT.notNull(item).exception("The item can not be null.");
		return new ItemProperty(item);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemProperty}.
	 */
	public static SuiProperty itemInjectable(final String injectionPointId) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		return new InjectableItemProperty(injectionPointId);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param item             factory for default the default item
	 * @return an {@link InjectableItemProperty}.
	 */
	public static SuiProperty itemInjectable(final String injectionPointId, final NodeFactory item) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(item).exception("The item can not be null.");
		return new InjectableItemProperty(injectionPointId, item);
	}




	/**
	 * @return an {@link AnchorProperty}
	 */
	public static SuiProperty anchorFitParent() {
		return anchor(0, 0, 0, 0);
	}




	/**
	 * @param top    the value for the top anchor or null.
	 * @param bottom the value for the bottom anchor or null.
	 * @param left   the value for the left anchor or null.
	 * @param right  the value for the right anchor or null.
	 * @return an {@link AnchorProperty}
	 */
	public static SuiProperty anchor(final Number top, final Number bottom, final Number left, final Number right) {
		return new AnchorProperty(top, bottom, left, right);
	}




	/**
	 * @return a {@link FitToWidthProperty} with the fit set to true.
	 */
	public static SuiProperty fitToWidth() {
		return fitToWidth(true);
	}




	/**
	 * @param fitToWidth whether the element should fit the width of its parent element.
	 * @return a {@link FitToWidthProperty}.
	 */
	public static SuiProperty fitToWidth(final boolean fitToWidth) {
		return new FitToWidthProperty(fitToWidth);
	}




	/**
	 * @return a {@link FitToWidthProperty} with the fit set to true.
	 */
	public static SuiProperty fitToHeight() {
		return fitToHeight(true);
	}




	/**
	 * @param fitToHeight whether the element should fit the height of its parent element.
	 * @return a {@link FitToWidthProperty}.
	 */
	public static SuiProperty fitToHeight(final boolean fitToHeight) {
		return new FitToHeightProperty(fitToHeight);
	}




	/**
	 * @param horizontal the behaviour of the horizontal scrollbar.
	 * @param vertical   the behaviour of the vertical scrollbar.
	 * @return a {@link ShowScrollbarsProperty}
	 */
	public static SuiProperty showScrollbars(final ScrollPane.ScrollBarPolicy horizontal, final ScrollPane.ScrollBarPolicy vertical) {
		Validations.INPUT.notNull(horizontal).exception("The horizontal scrollbar policy can not be null.");
		Validations.INPUT.notNull(vertical).exception("The vertical scrollbar policy can not be null.");
		return new ShowScrollbarsProperty(horizontal, vertical);
	}




	/**
	 * @param spacing the spacing between the elements
	 * @return a {@link SpacingProperty}
	 */
	public static SuiProperty spacing(final double spacing) {
		return new SpacingProperty(spacing);
	}




	/**
	 * @param alignment the alignment of the node(s)
	 * @return an {@link AlignmentProperty}
	 */
	public static SuiProperty alignment(final Pos alignment) {
		Validations.INPUT.notNull(alignment).exception("The alignment can not be null.");
		return new AlignmentProperty(alignment);
	}




	/**
	 * @param orientation the orientation of the node(s)
	 * @return an {@link OrientationProperty}
	 */
	public static SuiProperty orientation(final Orientation orientation) {
		Validations.INPUT.notNull(orientation).exception("The orientation can not be null.");
		return new OrientationProperty(orientation);
	}




	/**
	 * @param style the css style as a string
	 * @return an {@link StyleProperty}
	 */
	public static SuiProperty style(final String style) {
		Validations.INPUT.notNull(style).exception("The style can not be null.");
		return new StyleProperty(style);
	}




	/**
	 * @param style the css style as a file/resource
	 * @return an {@link StyleProperty}
	 */
	public static SuiProperty style(final Resource style) {
		Validations.INPUT.notNull(style).exception("The style can not be null.");
		return new StyleProperty(style);
	}




	/**
	 * @param choices the list of possible choices. The class of the choice should have an implementation of the equals-method.
	 * @return an {@link ChoicesProperty}
	 */
	public static <T> SuiProperty choices(final List<T> choices) {
		Validations.INPUT.notNull(choices).exception("The choices can not be null.");
		return new ChoicesProperty<>(List.copyOf(choices));
	}




	/**
	 * @param type      the type to convert to/from string
	 * @param converter the converter for the displayed choice box items
	 * @return an {@link ChoicesConverterProperty}
	 */
	public static <T> SuiProperty choicesConverter(final Class<T> type, final StringConverter<T> converter) {
		return choicesConverter(null, type, converter);
	}




	/**
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param type       the type to convert to/from string
	 * @param converter  the converter for the displayed choice box items
	 * @return an {@link ChoicesConverterProperty}
	 */
	public static <T> SuiProperty choicesConverter(final String propertyId, final Class<T> type, final StringConverter<T> converter) {
		Validations.INPUT.notNull(type).exception("The type can not be null.");
		Validations.INPUT.notNull(converter).exception("The converter can not be null.");
		return new ChoicesConverterProperty<>(propertyId, converter);
	}




	/**
	 * @param type       the type to convert to/from string
	 * @param fromString converter from a string to an object
	 * @param toString   converter from an object to a string
	 * @return an {@link ChoicesConverterProperty}
	 */
	public static <T> SuiProperty choicesConverter(final Class<T> type,
												   final ChoicesConverterProperty.FromStringConverter<T> fromString,
												   final ChoicesConverterProperty.ToStringConverter<T> toString) {
		return choicesConverter(null, type, fromString, toString);
	}




	/**
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param type       the type to convert to/from string
	 * @param fromString converter from a string to an object
	 * @param toString   converter from an object to a string
	 * @return an {@link ChoicesConverterProperty}
	 */
	public static <T> SuiProperty choicesConverter(final String propertyId,
												   final Class<T> type,
												   final ChoicesConverterProperty.FromStringConverter<T> fromString,
												   final ChoicesConverterProperty.ToStringConverter<T> toString) {
		Validations.INPUT.notNull(type).exception("The type can not be null.");
		Validations.INPUT.notNull(fromString).exception("The converter from strings can not be null.");
		Validations.INPUT.notNull(toString).exception("The converter to strings can not be null.");
		return new ChoicesConverterProperty<>(propertyId, fromString, toString);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#DEFAULT}
	 */
	public static SuiProperty behaviourDefault() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.DEFAULT, null);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#STATIC_NODE}
	 */
	public static SuiProperty behaviourStaticNode() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE, null);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#STATIC_SUBTREE}
	 */
	public static SuiProperty behaviourStaticSubtree() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE, null);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#STATIC}
	 */
	public static SuiProperty behaviourStatic() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.STATIC, null);
	}




	/**
	 * @param behaviour the {@link MutationBehaviourProperty.MutationBehaviour}.
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static SuiProperty mutationBehaviour(final MutationBehaviourProperty.MutationBehaviour behaviour) {
		return mutationBehaviour(behaviour, null);
	}




	/**
	 * @param behaviour the {@link MutationBehaviourProperty.MutationBehaviour}.
	 * @param condition the condition (or null). See table at {@link MutationBehaviourProperty}.
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static SuiProperty mutationBehaviour(final MutationBehaviourProperty.MutationBehaviour behaviour,
												final TagConditionExpression condition) {
		Validations.INPUT.notNull(behaviour).exception("The mutation behaviour can not be null.");
		return new MutationBehaviourProperty(behaviour, condition);
	}




	/**
	 * @param propertyId     see {@link SuiProperty#getPropertyId()}
	 * @param layoutFunction the function used to calculate the layout of the child nodes
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static SuiProperty layout(final String propertyId, final LayoutProperty.LayoutFunction layoutFunction) {
		Validations.INPUT.notEmpty(propertyId).exception("The layout id can not be null or empty.");
		Validations.INPUT.notNull(layoutFunction).exception("The layout function can not be null.");
		return new LayoutProperty(propertyId, layoutFunction);
	}




	/**
	 * @return a {@link EditableProperty}
	 */
	public static SuiProperty editable() {
		return editable(true);
	}




	/**
	 * @param editable whether the control is editable
	 * @return a {@link EditableProperty}
	 */
	public static SuiProperty editable(final boolean editable) {
		return new EditableProperty(editable);
	}




	/**
	 * @return a {@link SearchableProperty}
	 */
	public static SuiProperty searchable() {
		return searchable(true);
	}




	/**
	 * @param searchable whether the control is searchable
	 * @return a {@link SearchableProperty}
	 */
	public static SuiProperty searchable(final boolean searchable) {
		return new SearchableProperty(searchable);
	}




	/**
	 * @param locale the locale to use
	 * @return a {@link ChronologyProperty}
	 */
	public static SuiProperty chronology(final Locale locale) {
		Validations.INPUT.notNull(locale).exception("The locale may not be null.");
		return chronology(Chronology.ofLocale(locale));
	}




	/**
	 * @param chronology the {@link Chronology} to use
	 * @return a {@link ChronologyProperty}
	 */
	public static SuiProperty chronology(final Chronology chronology) {
		Validations.INPUT.notNull(chronology).exception("The chronology may not be null.");
		return new ChronologyProperty(chronology);
	}




	/**
	 * @param min the min value (inclusive) (set to null to use the default min value)
	 * @param max the max value (inclusive) (set to null to use the default max value)
	 * @return a {@link MinMaxProperty}
	 */
	public static SuiProperty minMax(final Number min, final Number max) {
		return new MinMaxProperty(
				min == null ? -Double.MAX_VALUE : min,
				max == null ? +Double.MAX_VALUE : max);
	}




	/**
	 * @param increment the increment value
	 * @return a {@link BlockIncrementProperty}
	 */
	public static SuiProperty blockIncrement(final Number increment) {
		Validations.INPUT.notNull(increment).exception("The increment-value may not be null.");
		Validations.INPUT.isNotNegative(increment.doubleValue()).exception("The increment-value may not be negative.");
		return new BlockIncrementProperty(increment);
	}




	/**
	 * @param formatter the formatting function
	 * @return a {@link LabelFormatterProperty}
	 */
	public static SuiProperty labelFormatter(final Function<Double, String> formatter) {
		return labelFormatter(null, formatter);
	}




	/**
	 * @param propertyId see {@link SuiProperty#getPropertyId()}
	 * @param formatter  the formatting function
	 * @return a {@link LabelFormatterProperty}
	 */
	public static SuiProperty labelFormatter(final String propertyId, final Function<Double, String> formatter) {
		Validations.INPUT.notNull(formatter).exception("The formatting-function may not be null.");
		return new LabelFormatterProperty(propertyId, formatter);
	}




	/**
	 * @param tickMarkStyle the style of the tick marks
	 * @return a {@link TickMarkProperty}
	 */
	public static SuiProperty tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle) {
		return tickMarks(
				tickMarkStyle,
				TickMarkProperty.DEFAULT_MAJOR_TICK_UNIT,
				TickMarkProperty.DEFAULT_MINOR_TICK_COUNT,
				TickMarkProperty.DEFAULT_SNAP_TO_TICKS);
	}




	/**
	 * @param tickMarkStyle  the style of the tick marks
	 * @param majorTickUnit  the unit of the major tick marks
	 * @param minorTickCount how many minor tick marks for each major mark
	 * @return a {@link TickMarkProperty}
	 */
	public static SuiProperty tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle,
										final Number majorTickUnit,
										final int minorTickCount) {
		return tickMarks(tickMarkStyle, majorTickUnit, minorTickCount, TickMarkProperty.DEFAULT_SNAP_TO_TICKS);
	}




	/**
	 * @param tickMarkStyle  the style of the tick marks
	 * @param majorTickUnit  the unit of the major tick marks
	 * @param minorTickCount how many minor tick marks for each major mark
	 * @param snapToTicks    whether to snap to the tick marks
	 * @return a {@link TickMarkProperty}
	 */
	public static SuiProperty tickMarks(final TickMarkProperty.TickMarkStyle tickMarkStyle,
										final Number majorTickUnit,
										final int minorTickCount,
										final boolean snapToTicks) {
		Validations.INPUT.notNull(tickMarkStyle).exception("The tick-mark style may not be null.");
		Validations.INPUT.notNull(majorTickUnit).exception("The major tick unit may not be null.");
		Validations.INPUT.isNotNegative(majorTickUnit.doubleValue()).exception("The major tick unit may not be negative.");
		Validations.INPUT.isNotNegative(minorTickCount).exception("The minor tick count may not be negative.");
		return new TickMarkProperty(tickMarkStyle, majorTickUnit, minorTickCount, snapToTicks);
	}




	/**
	 * @param size the min, preferred and max size
	 * @return a {@link LabelSizeProperty}
	 */
	public static SuiProperty labelSize(final Number size) {
		return labelSize(size, size, size);
	}




	/**
	 * @param minSize  the min width or height of the label.
	 * @param prefSize the preferred width or height of the label.
	 * @param maxSize  the max width or height of the label.
	 * @return a {@link LabelSizeProperty}
	 */
	public static SuiProperty labelSize(final Number minSize, final Number prefSize, final Number maxSize) {
		Validations.INPUT.notNull(minSize).exception("The min size may not be null.");
		Validations.INPUT.notNull(prefSize).exception("The preferred size may not be null.");
		Validations.INPUT.notNull(maxSize).exception("The max size may not be null.");
		return new LabelSizeProperty(minSize, prefSize, maxSize);
	}




	/**
	 * @param propertyId the id of the property.
	 * @param min        the min value (inclusive)
	 * @param max        the max value (inclusive)
	 * @param stepSize   the amount to step by
	 * @param value      the (initial) value
	 * @return the {@link SpinnerFactoryProperty}
	 */
	public static SuiProperty integerSpinnerValues(final String propertyId,
												   final int min,
												   final int max,
												   final int stepSize,
												   final int value) {
		Validations.INPUT.notNull(propertyId).exception("The propertyId may not be null.");
		Validations.INPUT.isLessThan(min, max).exception(
				"The min value must be smaller than the max value: min={}, max={}", min, max);
		Validations.INPUT.isNotNegative(stepSize).exception("The step size may not be negative.");
		Validations.INPUT.inRange(value, min, max).exception(
				"The current value must be between the min and max value. min={}, max={}, current={}", min, max, value);
		return new SpinnerFactoryProperty(propertyId, min, max, stepSize, value);
	}




	/**
	 * @param propertyId the id of the property.
	 * @param min        the min value (inclusive)
	 * @param max        the max value (inclusive)
	 * @param stepSize   the amount to step by
	 * @param value      the (initial) value
	 * @return the {@link SpinnerFactoryProperty}
	 */
	public static SuiProperty floatingPointSpinnerValues(final String propertyId,
														 final double min,
														 final double max,
														 final double stepSize,
														 final double value) {
		Validations.INPUT.notNull(propertyId).exception("The propertyId may not be null.");
		Validations.INPUT.isLessThan(min, max).exception(
				"The min value must be smaller than the max value: min={}, max={}", min, max);
		Validations.INPUT.isNotNegative(stepSize).exception("The step size may not be negative.");
		Validations.INPUT.inRange(value, min, max).exception(
				"The current value must be between the min and max value. min={}, max={}, current={}", min, max, value);
		return new SpinnerFactoryProperty(propertyId, min, max, stepSize, value);
	}




	/**
	 * @param propertyId the id of the property.
	 * @param items      the items
	 * @param wrapAround whether to wrap around or stop at the end/start
	 * @return the {@link SpinnerFactoryProperty}
	 */
	public static SuiProperty listSpinnerValues(final String propertyId, final List<String> items, final boolean wrapAround) {
		Validations.INPUT.notNull(propertyId).exception("The propertyId may not be null.");
		Validations.INPUT.notNull(items).exception("The items-list must not be null.");
		return new SpinnerFactoryProperty(propertyId, items, wrapAround);
	}




	/**
	 * @param title the title of the tab.
	 * @return the {@link TabTitleProperty}
	 */
	public static SuiProperty tabTitle(final String title) {
		Validations.INPUT.notNull(title).exception("The title may not be null.");
		return new TabTitleProperty(title);
	}




	/**
	 * @param disabled whether the tab is disabled
	 * @return the {@link TabDisabledProperty}
	 */
	public static SuiProperty tabDisabled(final boolean disabled) {
		return new TabDisabledProperty(disabled);
	}




	/**
	 * @param side the side of the tab menu
	 * @return the {@link TabPaneMenuSideProperty}
	 */
	public static SuiProperty tabMenuSide(final Side side) {
		return new TabPaneMenuSideProperty(side);
	}




	/**
	 * @param tabClosingPolicy the tab closing policy
	 * @return the {@link TabClosingPolicyProperty}
	 */
	public static SuiProperty tabClosingPolicy(final TabPane.TabClosingPolicy tabClosingPolicy) {
		return new TabClosingPolicyProperty(tabClosingPolicy);
	}

}
