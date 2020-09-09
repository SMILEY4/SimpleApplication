package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.Getter;

public class StyleProperty extends SuiProperty {


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
	 * @param style the style as a string
	 */
	public StyleProperty(final String style) {
		super(StyleProperty.class);
		this.strStyle = style;
		this.resStyle = null;
	}




	/**
	 * @param style the style as a file / resource
	 */
	public StyleProperty(final Resource style) {
		super(StyleProperty.class);
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




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		final StyleProperty otherProp = (StyleProperty) other;
		if (usesResourceStyle() != otherProp.usesResourceStyle()) {
			return false;
		}
		if (usesResourceStyle()) {
			final String pathThis = getResStyle().getPath();
			final String pathOther = otherProp.getResStyle().getPath();
			return pathThis.equals(pathOther) && getResStyle().isInternal() == otherProp.getResStyle().isInternal();
		} else {
			return getStrStyle().equals(otherProp.getStrStyle());
		}
	}




	@Override
	public String printValue() {
		if (usesResourceStyle()) {
			return getResStyle().getPath() + (getResStyle().isInternal() ? " (internal)" : "");
		} else {
			return getStrStyle();
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



