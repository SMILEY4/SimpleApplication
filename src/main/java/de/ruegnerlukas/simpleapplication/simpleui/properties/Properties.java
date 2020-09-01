package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.util.StringConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
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
	 * Throws an {@link IllegalPropertiesException} for if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	public static void validate(final Class<?> nodeType, final Set<Class<? extends Property>> allowedProperties,
								final Property... properties) {
		checkIllegal(nodeType, allowedProperties, List.of(properties));
		checkConflicting(List.of(properties));
	}




	/**
	 * Checks whether the given properties are allowed.
	 * Throws an {@link IllegalPropertiesException} for if the list contains illegal properties (not contained in allowed list)
	 *
	 * @param nodeType          the type of the node
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the given properties to check
	 */
	public static void checkIllegal(final Class<?> nodeType, final Set<Class<? extends Property>> allowedProperties,
									final List<Property> properties) {
		List<Property> illegal = findIllegal(allowedProperties, properties);
		if (!illegal.isEmpty()) {
			throw new IllegalPropertiesException(nodeType, illegal);
		}
	}




	/**
	 * Checks whether the given properties do not conflict with each other, for example when a property type was added more than once.
	 * Throws an {@link IllegalPropertiesException} for if the list contains conflicting properties.
	 *
	 * @param properties the given properties to check
	 */
	public static void checkConflicting(final List<Property> properties) {
		final List<Property> duplicates = new ArrayList<>();
		for (Property property : properties) {
			final Class<? extends Property> key = property.getKey();
			int count = 0;
			for (Property p : properties) {
				if (p.getKey().equals(key)) {
					count++;
				}
			}
			if (count > 1) {
				duplicates.add(property);
			}
		}
		if (!duplicates.isEmpty()) {
			throw new DuplicatePropertiesException(duplicates);
		}
	}




	/**
	 * Finds all illegal properties.
	 *
	 * @param allowedProperties the list of allowed properties
	 * @param properties        the list of properties to check
	 * @return the list of illegal properties.
	 */
	public static List<Property> findIllegal(final Set<Class<? extends Property>> allowedProperties, final List<Property> properties) {
		List<Property> illegal = new ArrayList<>();
		properties.forEach(property -> {
			if (!allowedProperties.contains(property.getKey())) {
				illegal.add(property);
			}
		});
		return illegal;
	}




	/**
	 * @param id the id of the element. Unique among the siblings.
	 * @return an {@link IdProperty}.
	 */
	public static Property id(final String id) {
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
	public static Property size(final Number minWidth, final Number minHeight,
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
	public static Property minSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The min width can not be null.");
		Validations.INPUT.notNull(height).exception("The min height can not be null.");
		return new SizeMinProperty(width, height);
	}




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 * @return a {@link SizePreferredProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static Property preferredSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The preferred width can not be null.");
		Validations.INPUT.notNull(height).exception("The preferred height can not be null.");
		return new SizePreferredProperty(width, height);
	}




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 * @return a {@link SizeMaxProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static Property maxSize(final Number width, final Number height) {
		Validations.INPUT.notNull(width).exception("The max width can not be null.");
		Validations.INPUT.notNull(height).exception("The max height can not be null.");
		return new SizeMaxProperty(width, height);
	}




	/**
	 * @return a {@link DisabledProperty} with disabled set to true.
	 */
	public static Property disabled() {
		return disabled(true);
	}




	/**
	 * @param disabled whether the element is disabled.
	 * @return a {@link DisabledProperty}.
	 */
	public static Property disabled(final boolean disabled) {
		return new DisabledProperty(disabled);
	}




	/**
	 * @return a {@link WrapTextProperty} with the wrap-text set to true.
	 */
	public static Property wrapText() {
		return wrapText(true);
	}




	/**
	 * @param wrap whether the text content should be wrapped.
	 * @return a {@link WrapTextProperty}.
	 */
	public static Property wrapText(final boolean wrap) {
		return new WrapTextProperty(wrap);
	}




	/**
	 * @param text the text content.
	 * @return a {@link TextContentProperty}.
	 */
	public static Property textContent(final String text) {
		Validations.INPUT.notNull(text).exception("The text can not be null.");
		return new TextContentProperty(text);
	}




	/**
	 * @param text the prompt text.
	 * @return a {@link TextContentProperty}.
	 */
	public static Property promptText(final String text) {
		Validations.INPUT.notNull(text).exception("The prompt text can not be null.");
		return new PromptTextProperty(text);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final NodeFactory... items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final Collection<NodeFactory> items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contains null-values.");
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final Stream<NodeFactory> items) {
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		return new ItemListProperty(items);
	}




	/**
	 * @param factory the factory for the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final ItemListProperty.ItemListFactory factory) {
		Validations.INPUT.notNull(factory).exception("The factory can not be null.");
		return new ItemListProperty(factory);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static Property itemsInjectable(final String injectionPointId) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		return new InjectableItemListProperty(injectionPointId);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            factories for default items
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static Property itemsInjectable(final String injectionPointId,
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
	public static Property itemsInjectable(final String injectionPointId,
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
	public static Property itemsInjectable(final String injectionPointId,
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
	public static Property itemsInjectable(final String injectionPointId,
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
	public static Property item(final NodeFactory item) {
		Validations.INPUT.notNull(item).exception("The item can not be null.");
		return new ItemProperty(item);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemProperty}.
	 */
	public static Property itemInjectable(final String injectionPointId) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		return new InjectableItemProperty(injectionPointId);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param item             factory for default the default item
	 * @return an {@link InjectableItemProperty}.
	 */
	public static Property itemInjectable(final String injectionPointId, final NodeFactory item) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null.");
		Validations.INPUT.notNull(item).exception("The item can not be null.");
		return new InjectableItemProperty(injectionPointId, item);
	}




	/**
	 * @param top    the value for the top anchor or null.
	 * @param bottom the value for the bottom anchor or null.
	 * @param left   the value for the left anchor or null.
	 * @param right  the value for the right anchor or null.
	 * @return a {@link AnchorProperty}
	 */
	public static Property anchor(final Number top, final Number bottom, final Number left, final Number right) {
		return new AnchorProperty(top, bottom, left, right);
	}




	/**
	 * @return a {@link FitToWidthProperty} with the fit set to true.
	 */
	public static Property fitToWidth() {
		return fitToWidth(true);
	}




	/**
	 * @param fitToWidth whether the element should fit the width of its parent element.
	 * @return a {@link FitToWidthProperty}.
	 */
	public static Property fitToWidth(final boolean fitToWidth) {
		return new FitToWidthProperty(fitToWidth);
	}




	/**
	 * @return a {@link FitToWidthProperty} with the fit set to true.
	 */
	public static Property fitToHeight() {
		return fitToHeight(true);
	}




	/**
	 * @param fitToHeight whether the element should fit the height of its parent element.
	 * @return a {@link FitToWidthProperty}.
	 */
	public static Property fitToHeight(final boolean fitToHeight) {
		return new FitToHeightProperty(fitToHeight);
	}




	/**
	 * @param horizontal the behaviour of the horizontal scrollbar.
	 * @param vertical   the behaviour of the vertical scrollbar.
	 * @return a {@link ShowScrollbarsProperty}
	 */
	public static Property showScrollbars(final ScrollPane.ScrollBarPolicy horizontal, final ScrollPane.ScrollBarPolicy vertical) {
		Validations.INPUT.notNull(horizontal).exception("The horizontal scrollbar policy can not be null.");
		Validations.INPUT.notNull(vertical).exception("The vertical scrollbar policy can not be null.");
		return new ShowScrollbarsProperty(horizontal, vertical);
	}




	/**
	 * @param spacing the spacing between the elements
	 * @return a {@link SpacingProperty}
	 */
	public static Property spacing(final double spacing) {
		return new SpacingProperty(spacing);
	}




	/**
	 * @param alignment the alignment of the node(s)
	 * @return an {@link AlignmentProperty}
	 */
	public static Property alignment(final Pos alignment) {
		Validations.INPUT.notNull(alignment).exception("The alignment can not be null.");
		return new AlignmentProperty(alignment);
	}




	/**
	 * @param orientation the orientation of the node(s)
	 * @return an {@link OrientationProperty}
	 */
	public static Property orientation(final Orientation orientation) {
		Validations.INPUT.notNull(orientation).exception("The orientation can not be null.");
		return new OrientationProperty(orientation);
	}




	/**
	 * @param style the css style as a string
	 * @return an {@link StyleProperty}
	 */
	public static Property style(final String style) {
		Validations.INPUT.notNull(style).exception("The style can not be null.");
		return new StyleProperty(style);
	}




	/**
	 * @param style the css style as a file/resource
	 * @return an {@link StyleProperty}
	 */
	public static Property style(final Resource style) {
		Validations.INPUT.notNull(style).exception("The style can not be null.");
		return new StyleProperty(style);
	}




	/**
	 * @param choices the list of possible choices
	 * @return an {@link ChoicesProperty}
	 */
	public static <T> Property choices(final List<T> choices) {
		Validations.INPUT.notNull(choices).exception("The choices can not be null.");
		return new ChoicesProperty<>(List.copyOf(choices));
	}




	/**
	 * @param converter the converter for the displayed choice box items
	 * @return an {@link ChoiceBoxConverterProperty}
	 */
	public static <T> Property choiceBoxConverter(final Class<T> type, final StringConverter<T> converter) {
		Validations.INPUT.notNull(type).exception("The type can not be null.");
		Validations.INPUT.notNull(converter).exception("The converter can not be null.");
		return new ChoiceBoxConverterProperty<>(converter);
	}




	/**
	 * @param fromString converter from a string to an object
	 * @param toString   converter from an object to a string
	 * @return an {@link ChoiceBoxConverterProperty}
	 */
	public static <T> Property choiceBoxConverter(final Class<T> type,
												  final ChoiceBoxConverterProperty.FromStringConverter<T> fromString,
												  final ChoiceBoxConverterProperty.ToStringConverter<T> toString) {
		Validations.INPUT.notNull(type).exception("The type can not be null.");
		Validations.INPUT.notNull(fromString).exception("The converter from strings can not be null.");
		Validations.INPUT.notNull(toString).exception("The converter to strings can not be null.");
		return new ChoiceBoxConverterProperty<>(fromString, toString);
	}






	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#DEFAULT}
	 */
	public static Property defaultMutationBehaviour() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.DEFAULT);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#STATIC_NODE}
	 */
	public static Property staticNode() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.STATIC_NODE);
	}




	/**
	 * @return an {@link MutationBehaviourProperty} with {@link MutationBehaviourProperty.MutationBehaviour#STATIC_SUBTREE}
	 */
	public static Property staticSubtree() {
		return new MutationBehaviourProperty(MutationBehaviourProperty.MutationBehaviour.STATIC_SUBTREE);
	}




	/**
	 * @param behaviour the {@link MutationBehaviourProperty.MutationBehaviour}.
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static Property mutationBehaviour(final MutationBehaviourProperty.MutationBehaviour behaviour) {
		Validations.INPUT.notNull(behaviour).exception("The mutation behaviour can not be null.");
		return new MutationBehaviourProperty(behaviour);
	}




	/**
	 * @param layoutId       the id of the layout. This property will only be mutated, when this id changes.
	 * @param layoutFunction the function used to calculate the layout of the child nodes
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static Property layout(final String layoutId, final LayoutProperty.LayoutFunction layoutFunction) {
		Validations.INPUT.notEmpty(layoutId).exception("The layout id can not be null or empty.");
		Validations.INPUT.notNull(layoutFunction).exception("The layout function can not be null.");
		return new LayoutProperty(layoutId, layoutFunction);
	}




	/**
	 * @param editable whether the control is editable
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static Property editable(final boolean editable) {
		return new EditableProperty(editable);
	}


}
