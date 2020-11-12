package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.Getter;

import java.util.Arrays;
import java.util.function.BiFunction;

public class StyleProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<StyleProperty, StyleProperty, Boolean> COMPARATOR = (a, b) -> {
		if (a.usesResourceStyle() == b.usesResourceStyle()) {
			if (a.usesResourceStyle()) {
				final String aPath = a.getResStyle().getPath();
				final String bPath = b.getResStyle().getPath();
				return aPath.equals(bPath) && a.getResStyle().isInternal() == b.getResStyle().isInternal();
			} else {
				return a.getStrStyle().equals(b.getStrStyle());
			}
		} else {
			return false;
		}
	};

	/**
	 * The style as a string
	 */
	@Getter
	private final String strStyle;

	/**
	 * The style as a file / resource
	 */
	@Getter
	private final Resource resStyle;




	/**
	 * @param style the style as a string / strings
	 */
	public StyleProperty(final String... style) {
		super(StyleProperty.class, COMPARATOR);
		Validations.INPUT.notNull(style).exception("The style may not be null.");
		this.strStyle = String.join("; " + Arrays.asList(style));
		this.resStyle = null;
	}




	/**
	 * @param style the style as a file / resource
	 */
	public StyleProperty(final Resource style) {
		super(StyleProperty.class, COMPARATOR);
		Validations.INPUT.notNull(style).exception("The style may not be null.");
		this.strStyle = null;
		this.resStyle = style;
	}




	/**
	 * @return whether this property gets the style from a string or a resource.
	 */
	private boolean usesResourceStyle() {
		return resStyle != null;
	}




	/**
	 * @return this style as a path.
	 */
	private String getResStyleAsPath() {
		if (usesResourceStyle()) {
			String path = resStyle.getPath();
			if (!resStyle.isInternal()) {
				path = "file:" + path;
			}
			return path;
		} else {
			return "";
		}
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param style the style as a css-string.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T style(final String style) {
			getBuilderProperties().add(new StyleProperty(style));
			return (T) this;
		}

		/**
		 * @param style the resource pointing to a css-file
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T style(final Resource style) {
			getBuilderProperties().add(new StyleProperty(style));
			return (T) this;
		}


	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<StyleProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final StyleProperty property,
						  final Node fxNode) {
			setStyle(property, fxNode);
		}




		@Override
		public MutationResult update(final StyleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setStyle(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final StyleProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			if (property.usesResourceStyle()) {
				((Parent) fxNode).getStylesheets().clear();
			} else {
				fxNode.setStyle("");
			}
			return MutationResult.MUTATED;
		}




		/**
		 * @param property the style property
		 * @param fxNode   the javafx node to apply the style to
		 */
		private void setStyle(final StyleProperty property, final Node fxNode) {
			if (property.usesResourceStyle()) {
				((Parent) fxNode).getStylesheets().clear();
				((Parent) fxNode).getStylesheets().add(property.getResStyleAsPath());
			} else {
				fxNode.setStyle(property.getStrStyle());
			}
		}

	}


}



