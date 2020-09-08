package de.ruegnerlukas.simpleapplication.simpleui.utils;

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
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import org.assertj.core.data.Percentage;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertyTestUtils {


	public static void assertAnchorProperty(SuiBaseNode node, Number top, Number botton, Number left, Number right) {
		assertThat(node.getPropertyStore().has(AnchorProperty.class)).isTrue();
		final AnchorProperty property = node.getPropertyStore().get(AnchorProperty.class);
		assertThat(property.getTop()).isEqualTo(top);
		assertThat(property.getBottom()).isEqualTo(botton);
		assertThat(property.getLeft()).isEqualTo(left);
		assertThat(property.getRight()).isEqualTo(right);
	}




	public static void assertDisabledProperty(SuiBaseNode node, boolean disabled) {
		assertThat(node.getPropertyStore().has(DisabledProperty.class)).isTrue();
		final DisabledProperty property = node.getPropertyStore().get(DisabledProperty.class);
		assertThat(property.isDisabled()).isEqualTo(disabled);
	}




	public static void assertIdProperty(SuiBaseNode node, String id) {
		assertThat(node.getPropertyStore().has(IdProperty.class)).isTrue();
		final IdProperty property = node.getPropertyStore().get(IdProperty.class);
		assertThat(property.getId()).isEqualTo(id);
	}




	public static void assertSizeMinProperty(SuiBaseNode node, Double width, Double height) {
		assertThat(node.getPropertyStore().has(SizeMinProperty.class)).isTrue();
		final SizeMinProperty property = node.getPropertyStore().get(SizeMinProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizePreferredProperty(SuiBaseNode node, Double width, Double height) {
		assertThat(node.getPropertyStore().has(SizePreferredProperty.class)).isTrue();
		final SizePreferredProperty property = node.getPropertyStore().get(SizePreferredProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeMaxProperty(SuiBaseNode node, Double width, Double height) {
		assertThat(node.getPropertyStore().has(SizeMaxProperty.class)).isTrue();
		final SizeMaxProperty property = node.getPropertyStore().get(SizeMaxProperty.class);
		assertThat(property.getWidth().doubleValue()).isCloseTo(width, Percentage.withPercentage(0.01));
		assertThat(property.getHeight().doubleValue()).isCloseTo(height, Percentage.withPercentage(0.01));
	}




	public static void assertSizeProperty(SuiBaseNode node, Double minWidth, Double minHeight, Double prefWidth, Double prefHeight, Double maxWidth, Double maxHeight) {
		assertThat(node.getPropertyStore().has(SizeProperty.class)).isTrue();
		final SizeProperty property = node.getPropertyStore().get(SizeProperty.class);
		assertThat(property.getMinWidth().doubleValue()).isCloseTo(minWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMinHeight().doubleValue()).isCloseTo(minHeight, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredWidth().doubleValue()).isCloseTo(prefWidth, Percentage.withPercentage(0.01));
		assertThat(property.getPreferredHeight().doubleValue()).isCloseTo(prefHeight, Percentage.withPercentage(0.01));
		assertThat(property.getMaxWidth().doubleValue()).isCloseTo(maxWidth, Percentage.withPercentage(0.01));
		assertThat(property.getMaxHeight().doubleValue()).isCloseTo(maxHeight, Percentage.withPercentage(0.01));
	}




	public static void assertTextContentProperty(SuiBaseNode node, String text) {
		assertThat(node.getPropertyStore().has(TextContentProperty.class)).isTrue();
		final TextContentProperty property = node.getPropertyStore().get(TextContentProperty.class);
		assertThat(property.getText()).isEqualTo(text);
	}




	public static void assertWrapTextProperty(SuiBaseNode node, boolean wrap) {
		assertThat(node.getPropertyStore().has(WrapTextProperty.class)).isTrue();
		final WrapTextProperty property = node.getPropertyStore().get(WrapTextProperty.class);
		assertThat(property.isWrap()).isEqualTo(wrap);
	}




	public static void assertFitToWidthProperty(final SuiBaseNode node, final boolean fitToWidth) {
		assertThat(node.getPropertyStore().has(FitToWidthProperty.class)).isTrue();
		final FitToWidthProperty property = node.getPropertyStore().get(FitToWidthProperty.class);
		assertThat(property.isFitToWidth()).isEqualTo(fitToWidth);
	}




	public static void assertFitToHeightProperty(final SuiBaseNode node, final boolean fitToHeight) {
		assertThat(node.getPropertyStore().has(FitToHeightProperty.class)).isTrue();
		final FitToHeightProperty property = node.getPropertyStore().get(FitToHeightProperty.class);
		assertThat(property.isFitToHeight()).isEqualTo(fitToHeight);
	}




	public static void assertShowScrollBarProperty(final SuiBaseNode node, final ScrollPane.ScrollBarPolicy hor, final ScrollPane.ScrollBarPolicy vert) {
		assertThat(node.getPropertyStore().has(ShowScrollbarsProperty.class)).isTrue();
		final ShowScrollbarsProperty property = node.getPropertyStore().get(ShowScrollbarsProperty.class);
		assertThat(property.getHorizontal()).isEqualTo(hor);
		assertThat(property.getVertical()).isEqualTo(vert);
	}




	public static void assertSpacingProperty(final SuiBaseNode node, final double spacing) {
		assertThat(node.getPropertyStore().has(SpacingProperty.class)).isTrue();
		final SpacingProperty property = node.getPropertyStore().get(SpacingProperty.class);
		assertThat(property.getSpacing()).isCloseTo(spacing, Percentage.withPercentage(0.01));
	}




	public static void assertAlignment(final SuiBaseNode node, final Pos alignment) {
		assertThat(node.getPropertyStore().has(AlignmentProperty.class)).isTrue();
		final AlignmentProperty property = node.getPropertyStore().get(AlignmentProperty.class);
		assertThat(property.getAlignment()).isEqualTo(alignment);
	}




	public static void assertOrientation(final SuiBaseNode node, final Orientation orientation) {
		assertThat(node.getPropertyStore().has(OrientationProperty.class)).isTrue();
		final OrientationProperty property = node.getPropertyStore().get(OrientationProperty.class);
		assertThat(property.getOrientation()).isEqualTo(orientation);
	}




	public static void assertStyle(final SuiBaseNode node, final String style) {
		assertThat(node.getPropertyStore().has(StyleProperty.class)).isTrue();
		final StyleProperty property = node.getPropertyStore().get(StyleProperty.class);
		assertThat(property.getStrStyle()).isEqualTo(style);
	}




	public static void assertMutationBehaviour(final SuiBaseNode node, final MutationBehaviourProperty.MutationBehaviour behaviour) {
		assertThat(node.getPropertyStore().has(MutationBehaviourProperty.class)).isTrue();
		final MutationBehaviourProperty property = node.getPropertyStore().get(MutationBehaviourProperty.class);
		assertThat(property.getBehaviour()).isEqualTo(behaviour);
	}

}
