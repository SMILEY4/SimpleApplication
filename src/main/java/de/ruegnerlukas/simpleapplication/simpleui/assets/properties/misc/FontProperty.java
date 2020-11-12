package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Labeled;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class FontProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<FontProperty, FontProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getFont(), b.getFont());

	/**
	 * the font.
	 */
	@Getter
	private final Font font;




	/**
	 * @param font the font
	 */
	public FontProperty(final Font font) {
		super(FontProperty.class, COMPARATOR);
		this.font = font;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param size the font size. Uses the default font family name
		 * @return this builder for chaining
		 */
		default T font(final double size) {
			return font(Font.font(size));
		}

		/**
		 * @param family the font family. Uses the default font size
		 * @return this builder for chaining
		 */
		default T font(final String family) {
			return font(Font.font(family));
		}

		/**
		 * @param family the font family
		 * @param size   the font size
		 * @return this builder for chaining
		 */
		default T font(final String family, final double size) {
			return font(Font.font(family, size));
		}

		/**
		 * @param family  the font family
		 * @param posture the font style (e.g. italics)
		 * @param size    the font size
		 * @return this builder for chaining
		 */
		default T font(final String family, final FontPosture posture, final double size) {
			return font(Font.font(family, posture, size));
		}

		/**
		 * @param family  the font family
		 * @param posture the font style (e.g. "italics")
		 * @param weight  the font weight (e.g. "bold")
		 * @param size    the font size
		 * @return this builder for chaining
		 */
		default T font(final String family, final FontWeight weight, final FontPosture posture, final double size) {
			return font(Font.font(family, weight, posture, size));
		}

		/**
		 * @param resource the resource to the font file
		 * @param size     the font size
		 * @return this builder for chaining
		 */
		default T font(final Resource resource, final double size) {
			if (resource.isInternal()) {
				return font(Font.loadFont(resource.asInputStream(), size));
			} else {
				return font(Font.loadFont(resource.getPath(), size));
			}
		}

		/**
		 * @param font the font
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T font(final Font font) {
			getBuilderProperties().add(new FontProperty(font));
			return (T) this;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<FontProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final FontProperty property, final Labeled fxNode) {
			fxNode.setFont(property.getFont());
		}




		@Override
		public MutationResult update(final FontProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setFont(property.getFont());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FontProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setFont(null);
			return MutationResult.MUTATED;
		}

	}






	public static class TextInputControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<FontProperty, TextInputControl> {


		@Override
		public void build(final SuiNode node, final FontProperty property, final TextInputControl fxNode) {
			fxNode.setFont(property.getFont());
		}




		@Override
		public MutationResult update(final FontProperty property, final SuiNode node, final TextInputControl fxNode) {
			fxNode.setFont(property.getFont());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FontProperty property, final SuiNode node, final TextInputControl fxNode) {
			fxNode.setFont(null);
			return MutationResult.MUTATED;
		}

	}




	public static class SpinnerUpdatingBuilder implements PropFxNodeUpdatingBuilder<FontProperty, Spinner<?>> {


		@Override
		public void build(final SuiNode node, final FontProperty property, final Spinner<?> fxNode) {
			fxNode.getEditor().setFont(property.getFont());
		}




		@Override
		public MutationResult update(final FontProperty property, final SuiNode node, final Spinner<?> fxNode) {
			fxNode.getEditor().setFont(property.getFont());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FontProperty property, final SuiNode node, final Spinner<?> fxNode) {
			fxNode.getEditor().setFont(null);
			return MutationResult.MUTATED;
		}

	}

	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<FontProperty, Pane> {


		@Override
		public void build(final SuiNode node, final FontProperty property, final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setFont(property.getFont());
		}




		@Override
		public MutationResult update(final FontProperty property, final SuiNode node, final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setFont(property.getFont());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final FontProperty property, final SuiNode node, final Pane fxNode) {
			SuiLabeledSlider.getLabel(fxNode).setFont(null);
			return MutationResult.MUTATED;
		}

	}

}



