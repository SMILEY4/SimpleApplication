package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Properties {


	public static void checkIllegal(final Class<?> nodeType, final Set<Class<? extends Property>> allowedProperties, final Property... properties) {
		checkIllegal(nodeType, allowedProperties, List.of(properties));
	}




	public static void checkIllegal(final Class<?> nodeType, final Set<Class<? extends Property>> allowedProperties, final List<Property> properties) {
		List<Property> illegal = findIllegal(allowedProperties, properties);
		if (!illegal.isEmpty()) {
			throw new IllegalPropertiesException(nodeType, illegal);
		}
	}




	public static List<Property> findIllegal(final Set<Class<? extends Property>> allowedProperties, final List<Property> properties) {
		List<Property> illegal = new ArrayList<>();
		properties.forEach(property -> {
			if (!allowedProperties.contains(property.getKey())) {
				illegal.add(property);
			}
		});
		return illegal;
	}




	public static Property id(final String id) {
		return new IdProperty(id);
	}




	public static Property buttonListener(final ActionListenerProperty.ActionListener listener) {
		return new ActionListenerProperty(listener);
	}




	public static Property boolStateListener(final BooleanStateListenerProperty.BooleanStateListener listener) {
		return new BooleanStateListenerProperty(listener);
	}




	public static Property size(final Double minWidth, final Double minHeight,
								final Double preferredWidth, final Double preferredHeight,
								final Double maxWidth, final Double maxHeight) {
		return new SizeProperty(minWidth, minHeight, preferredWidth, preferredHeight, maxWidth, maxHeight);
	}




	public static Property minSize(final Double width, final Double height) {
		return new SizeMinProperty(width, height);
	}




	public static Property preferredSize(final Double width, final Double height) {
		return new SizePreferredProperty(width, height);
	}




	public static Property maxSize(final Double width, final Double height) {
		return new SizeMaxProperty(width, height);
	}




	public static Property disabled() {
		return disabled(true);
	}




	public static Property disabled(final boolean disabled) {
		return new DisabledProperty(disabled);
	}




	public static Property wrapText() {
		return wrapText(true);
	}




	public static Property selected() {
		return selected(true);
	}




	public static Property selected(final boolean selected) {
		return new SelectedProperty(selected);
	}




	public static Property textContent(final String text) {
		return new TextContentProperty(text);
	}




	public static Property wrapText(final boolean wrap) {
		return new WrapTextProperty(wrap);
	}




	public static Property items(final NodeFactory... items) {
		return new ItemListProperty(items);
	}




	public static Property items(final Collection<NodeFactory> items) {
		return new ItemListProperty(items);
	}




	public static Property items(final Stream<NodeFactory> items) {
		return new ItemListProperty(items);
	}




	public static Property items(final ItemListProperty.ItemListFactory factory) {
		return new ItemListProperty(factory);
	}




	public static Property item(final NodeFactory item) {
		return new ItemProperty(item);
	}




	public static Property anchor(final Number top, final Number bottom, final Number left, final Number right) {
		return new AnchorProperty(top, bottom, left, right);
	}




	public static Property fitToWidth() {
		return fitToWidth(true);
	}




	public static Property fitToWidth(final boolean fitToWidth) {
		return new FitToWidthProperty(fitToWidth);
	}




	public static Property fitToHeight() {
		return fitToHeight(true);
	}




	public static Property fitToHeight(final boolean fitToHeight) {
		return new FitToHeightProperty(fitToHeight);
	}




	public static Property showScrollbars(final ScrollPane.ScrollBarPolicy horizontal, final ScrollPane.ScrollBarPolicy vertial) {
		return new ShowScrollbarsProperty(horizontal, vertial);
	}




	public static Property spacing(final double spacing) {
		return new SpacingProperty(spacing);
	}




	public static Property alignment(final Pos alignment) {
		return new AlignmentProperty(alignment);
	}




	public static Property orientation(final Orientation orientation) {
		return new OrientationProperty(orientation);
	}

}
