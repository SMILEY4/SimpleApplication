package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.properties.ActionListenerProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import org.assertj.core.data.Percentage;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTestUtils {


	public static void assertActionListenerProperty(SNode node) {
		assertThat(node.getProperties()).containsKey(ActionListenerProperty.class);
		assertThat(node.getProperty(ActionListenerProperty.class)).isNotNull();
	}




	public static void assertAnchorProperty(SNode node, Number top, Number botton, Number left, Number right) {
		assertThat(node.getProperties()).containsKey(AnchorProperty.class);
		final AnchorProperty property = node.getProperty(AnchorProperty.class);
		assertThat(property.getTop()).isEqualTo(top);
		assertThat(property.getBottom()).isEqualTo(botton);
		assertThat(property.getLeft()).isEqualTo(left);
		assertThat(property.getRight()).isEqualTo(right);
	}




	public static void assertDisabledProperty(SNode node, boolean disabled) {
		assertThat(node.getProperties()).containsKey(DisabledProperty.class);
		final DisabledProperty property = node.getProperty(DisabledProperty.class);
		assertThat(property.isDisabled()).isEqualTo(disabled);
	}




	public static void assertIdProperty(SNode node, String id) {
		assertThat(node.getProperties()).containsKey(IdProperty.class);
		final IdProperty property = node.getProperty(IdProperty.class);
		assertThat(property.getId()).isEqualTo(id);
	}




	public static void assertSizeMinProperty(SNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizeMinProperty.class);
		final SizeMinProperty property = node.getProperty(SizeMinProperty.class);
		assertThat(property.getWidth()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizePreferredProperty(SNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizePreferredProperty.class);
		final SizePreferredProperty property = node.getProperty(SizePreferredProperty.class);
		assertThat(property.getWidth()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeMaxProperty(SNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizeMaxProperty.class);
		final SizeMaxProperty property = node.getProperty(SizeMaxProperty.class);
		assertThat(property.getWidth()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeProperty(SNode node, Double minWidth, Double minHeight, Double prefWidth, Double prefHeight, Double maxWidth, Double maxHeight) {
		assertThat(node.getProperties()).containsKey(SizeProperty.class);
		final SizeProperty property = node.getProperty(SizeProperty.class);
		assertThat(property.getMinWidth()).isCloseTo(minWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMinHeight()).isCloseTo(minHeight, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredWidth()).isCloseTo(prefWidth, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredHeight()).isCloseTo(prefHeight, Percentage.withPercentage(0.01));
		assertThat(property.getMaxWidth()).isCloseTo(maxWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMaxHeight()).isCloseTo(maxHeight, Percentage.withPercentage(0.01));
	}




	public static void assertTextContentProperty(SNode node, String text) {
		assertThat(node.getProperties()).containsKey(TextContentProperty.class);
		final TextContentProperty property = node.getProperty(TextContentProperty.class);
		assertThat(property.getText()).isEqualTo(text);
	}




	public static void assertWrapTextProperty(SNode node, boolean wrap) {
		assertThat(node.getProperties()).containsKey(WrapTextProperty.class);
		final WrapTextProperty property = node.getProperty(WrapTextProperty.class);
		assertThat(property.isWrap()).isEqualTo(wrap);
	}




	public static void assertFitToWidthProperty(final SNode node, final boolean fitToWidth) {
		assertThat(node.getProperties()).containsKey(FitToWidthProperty.class);
		final FitToWidthProperty property = node.getProperty(FitToWidthProperty.class);
		assertThat(property.isFitToWidth()).isEqualTo(fitToWidth);
	}




	public static void assertFitToHeightProperty(final SNode node, final boolean fitToHeight) {
		assertThat(node.getProperties()).containsKey(FitToHeightProperty.class);
		final FitToHeightProperty property = node.getProperty(FitToHeightProperty.class);
		assertThat(property.isFitToHeight()).isEqualTo(fitToHeight);
	}




	public static void assertShowScrollBarProperty(final SNode node, final ScrollPane.ScrollBarPolicy hor, final ScrollPane.ScrollBarPolicy vert) {
		assertThat(node.getProperties()).containsKey(ShowScrollbarsProperty.class);
		final ShowScrollbarsProperty property = node.getProperty(ShowScrollbarsProperty.class);
		assertThat(property.getHorizontal()).isEqualTo(hor);
		assertThat(property.getVertical()).isEqualTo(vert);
	}




	public static void assertSpacingProperty(final SNode node, final double spacing) {
		assertThat(node.getProperties()).containsKey(SpacingProperty.class);
		final SpacingProperty property = node.getProperty(SpacingProperty.class);
		assertThat(property.getSpacing()).isCloseTo(spacing, Percentage.withPercentage(0.01));
	}




	public static void assertAlignment(final SNode node, final Pos alignment) {
		assertThat(node.getProperties()).containsKey(AlignmentProperty.class);
		final AlignmentProperty property = node.getProperty(AlignmentProperty.class);
		assertThat(property.getAlignment()).isEqualTo(alignment);
	}




	public static void assertOrientation(final SNode node, final Orientation orientation) {
		assertThat(node.getProperties()).containsKey(OrientationProperty.class);
		final OrientationProperty property = node.getProperty(OrientationProperty.class);
		assertThat(property.getOrientation()).isEqualTo(orientation);
	}

}
