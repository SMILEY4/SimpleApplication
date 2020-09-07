package de.ruegnerlukas.simpleapplication.simpleui.utils;

import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import org.assertj.core.data.Percentage;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTestUtils {


	public static void assertAnchorProperty(SuiNode node, Number top, Number botton, Number left, Number right) {
		assertThat(node.getProperties()).containsKey(AnchorProperty.class);
		final AnchorProperty property = node.getProperty(AnchorProperty.class);
		assertThat(property.getTop()).isEqualTo(top);
		assertThat(property.getBottom()).isEqualTo(botton);
		assertThat(property.getLeft()).isEqualTo(left);
		assertThat(property.getRight()).isEqualTo(right);
	}




	public static void assertDisabledProperty(SuiNode node, boolean disabled) {
		assertThat(node.getProperties()).containsKey(DisabledProperty.class);
		final DisabledProperty property = node.getProperty(DisabledProperty.class);
		assertThat(property.isDisabled()).isEqualTo(disabled);
	}




	public static void assertIdProperty(SuiNode node, String id) {
		assertThat(node.getProperties()).containsKey(IdProperty.class);
		final IdProperty property = node.getProperty(IdProperty.class);
		assertThat(property.getId()).isEqualTo(id);
	}




	public static void assertSizeMinProperty(SuiNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizeMinProperty.class);
		final SizeMinProperty property = node.getProperty(SizeMinProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizePreferredProperty(SuiNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizePreferredProperty.class);
		final SizePreferredProperty property = node.getProperty(SizePreferredProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeMaxProperty(SuiNode node, Double width, Double height) {
		assertThat(node.getProperties()).containsKey(SizeMaxProperty.class);
		final SizeMaxProperty property = node.getProperty(SizeMaxProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeProperty(SuiNode node, Double minWidth, Double minHeight, Double prefWidth, Double prefHeight, Double maxWidth, Double maxHeight) {
		assertThat(node.getProperties()).containsKey(SizeProperty.class);
		final SizeProperty property = node.getProperty(SizeProperty.class);
		assertThat(property.getMinWidth().doubleValue()).isCloseTo(minWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMinHeight().doubleValue()).isCloseTo(minHeight, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredWidth().doubleValue()).isCloseTo(prefWidth, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredHeight().doubleValue()).isCloseTo(prefHeight, Percentage.withPercentage(0.01));
		assertThat(property.getMaxWidth().doubleValue()).isCloseTo(maxWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMaxHeight().doubleValue()).isCloseTo(maxHeight, Percentage.withPercentage(0.01));
	}




	public static void assertTextContentProperty(SuiNode node, String text) {
		assertThat(node.getProperties()).containsKey(TextContentProperty.class);
		final TextContentProperty property = node.getProperty(TextContentProperty.class);
		assertThat(property.getText()).isEqualTo(text);
	}




	public static void assertWrapTextProperty(SuiNode node, boolean wrap) {
		assertThat(node.getProperties()).containsKey(WrapTextProperty.class);
		final WrapTextProperty property = node.getProperty(WrapTextProperty.class);
		assertThat(property.isWrap()).isEqualTo(wrap);
	}




	public static void assertFitToWidthProperty(final SuiNode node, final boolean fitToWidth) {
		assertThat(node.getProperties()).containsKey(FitToWidthProperty.class);
		final FitToWidthProperty property = node.getProperty(FitToWidthProperty.class);
		assertThat(property.isFitToWidth()).isEqualTo(fitToWidth);
	}




	public static void assertFitToHeightProperty(final SuiNode node, final boolean fitToHeight) {
		assertThat(node.getProperties()).containsKey(FitToHeightProperty.class);
		final FitToHeightProperty property = node.getProperty(FitToHeightProperty.class);
		assertThat(property.isFitToHeight()).isEqualTo(fitToHeight);
	}




	public static void assertShowScrollBarProperty(final SuiNode node, final ScrollPane.ScrollBarPolicy hor, final ScrollPane.ScrollBarPolicy vert) {
		assertThat(node.getProperties()).containsKey(ShowScrollbarsProperty.class);
		final ShowScrollbarsProperty property = node.getProperty(ShowScrollbarsProperty.class);
		assertThat(property.getHorizontal()).isEqualTo(hor);
		assertThat(property.getVertical()).isEqualTo(vert);
	}




	public static void assertSpacingProperty(final SuiNode node, final double spacing) {
		assertThat(node.getProperties()).containsKey(SpacingProperty.class);
		final SpacingProperty property = node.getProperty(SpacingProperty.class);
		assertThat(property.getSpacing()).isCloseTo(spacing, Percentage.withPercentage(0.01));
	}




	public static void assertAlignment(final SuiNode node, final Pos alignment) {
		assertThat(node.getProperties()).containsKey(AlignmentProperty.class);
		final AlignmentProperty property = node.getProperty(AlignmentProperty.class);
		assertThat(property.getAlignment()).isEqualTo(alignment);
	}




	public static void assertOrientation(final SuiNode node, final Orientation orientation) {
		assertThat(node.getProperties()).containsKey(OrientationProperty.class);
		final OrientationProperty property = node.getProperty(OrientationProperty.class);
		assertThat(property.getOrientation()).isEqualTo(orientation);
	}




	public static void assertStyle(final SuiNode node, final String style) {
		assertThat(node.getProperties()).containsKey(StyleProperty.class);
		final StyleProperty property = node.getProperty(StyleProperty.class);
		assertThat(property.getStrStyle()).isEqualTo(style);
	}




	public static void assertMutationBehaviour(final SuiNode node, final MutationBehaviourProperty.MutationBehaviour behaviour) {
		assertThat(node.getProperties()).containsKey(MutationBehaviourProperty.class);
		final MutationBehaviourProperty property = node.getProperty(MutationBehaviourProperty.class);
		assertThat(property.getBehaviour()).isEqualTo(behaviour);
	}

}
