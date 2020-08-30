package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
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
	public static Property size(final Double minWidth, final Double minHeight,
								final Double preferredWidth, final Double preferredHeight,
								final Double maxWidth, final Double maxHeight) {
		return new SizeProperty(minWidth, minHeight, preferredWidth, preferredHeight, maxWidth, maxHeight);
	}




	/**
	 * @param width  the minimum width.
	 * @param height the minimum height.
	 * @return a {@link SizeMinProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static Property minSize(final Double width, final Double height) {
		return new SizeMinProperty(width, height);
	}




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 * @return a {@link SizePreferredProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static Property preferredSize(final Double width, final Double height) {
		return new SizePreferredProperty(width, height);
	}




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 * @return a {@link SizeMaxProperty}. Has higher priority than {@link SizeProperty}.
	 */
	public static Property maxSize(final Double width, final Double height) {
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
		return new TextContentProperty(text);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final NodeFactory... items) {
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final Collection<NodeFactory> items) {
		return new ItemListProperty(items);
	}




	/**
	 * @param items the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final Stream<NodeFactory> items) {
		return new ItemListProperty(items);
	}




	/**
	 * @param factory the factory for the factories for child items.
	 * @return an {@link ItemListProperty}.
	 */
	public static Property items(final ItemListProperty.ItemListFactory factory) {
		return new ItemListProperty(factory);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemListProperty}.
	 */
	public static Property itemsInjectable(final String injectionPointId) {
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
		return new InjectableItemListProperty(injectionPointId, indexMarker, factory);
	}




	/**
	 * @param item the factory for the child item.
	 * @return an {@link ItemProperty}.
	 */
	public static Property item(final NodeFactory item) {
		return new ItemProperty(item);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @return an {@link InjectableItemProperty}.
	 */
	public static Property itemInjectable(final String injectionPointId) {
		return new InjectableItemProperty(injectionPointId);
	}




	/**
	 * @param injectionPointId the unique id of this injection point.
	 * @param item             factory for default the default item
	 * @return an {@link InjectableItemProperty}.
	 */
	public static Property itemInjectable(final String injectionPointId, final NodeFactory item) {
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
		return new AlignmentProperty(alignment);
	}




	/**
	 * @param orientation the orientation of the node(s)
	 * @return an {@link OrientationProperty}
	 */
	public static Property orientation(final Orientation orientation) {
		return new OrientationProperty(orientation);
	}




	/**
	 * @param style the css style as a string
	 * @return an {@link StyleProperty}
	 */
	public static Property style(final String style) {
		return new StyleProperty(style);
	}




	/**
	 * @param style the css style as a file/resource
	 * @return an {@link StyleProperty}
	 */
	public static Property style(final Resource style) {
		return new StyleProperty(style);
	}




	/**
	 * @param choices the list of possible choices
	 * @return an {@link ChoicesProperty}
	 */
	public static <T> Property choices(final List<T> choices) {
		return new ChoicesProperty<>(List.copyOf(choices));
	}




	/**
	 * @param converter the converter for the displayed choice box items
	 * @return an {@link ChoiceBoxConverterProperty}
	 */
	public static <T> Property choiceBoxConverter(final Class<T> type, final StringConverter<T> converter) {
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
		return new MutationBehaviourProperty(behaviour);
	}




	/**
	 * @param layoutId       the id of the layout. This property will only be mutated, when this id changes.
	 * @param layoutFunction the function used to calculate the layout of the child nodes
	 * @return a {@link MutationBehaviourProperty}
	 */
	public static Property layout(final String layoutId, final LayoutProperty.LayoutFunction layoutFunction) {
		return new LayoutProperty(layoutId, layoutFunction);
	}


}
