package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

public class StyleProperty extends Property {


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
	protected boolean isPropertyEqual(final Property other) {
		final StyleProperty otherProp = (StyleProperty) other;
		if (usesResourceStyle() != otherProp.usesResourceStyle()) {
			return false;
		}
		if (usesResourceStyle()) {
			return getResStyle().getPath().equals(otherProp.getResStyle().getPath())
					&& getResStyle().isInternal() == otherProp.getResStyle().isInternal();
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




	public static class StyleUpdatingBuilder implements PropFxNodeUpdatingBuilder<StyleProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final StyleProperty property,
						  final Node fxNode) {
			setStyle(property, fxNode);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final StyleProperty property,
									 final SuiNode node, final Node fxNode) {
			setStyle(property, fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final StyleProperty property,
									 final SuiNode node, final Node fxNode) {
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



