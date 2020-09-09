package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.style.ResourceStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StringStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.Style;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.scene.Node;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ManagedStyleProperty extends SuiProperty {


	/**
	 * The style managed by the style service.
	 */
	@Getter
	private final Style style;

	/**
	 * The style service managing the style
	 */
	@Getter
	private final StyleService styleService;




	/**
	 * @param style        the style (will be managed by the given style-service)
	 * @param styleService the style service.
	 * @return the new {@link ManagedStyleProperty}.
	 */
	public static SuiProperty managedStyle(final StyleService styleService, final Style style) {
		return new ManagedStyleProperty(styleService, style);
	}




	/**
	 * @param style        the style managed by the style service.
	 * @param styleService the style service
	 */
	public ManagedStyleProperty(final StyleService styleService, final Style style) {
		super(ManagedStyleProperty.class);
		this.style = style;
		this.styleService = styleService;
	}




	/**
	 * @return whether this property gets the style from a string or a resource.
	 */
	private boolean usesResourceStyle() {
		return style instanceof ResourceStyle;
	}




	/**
	 * @return the {@link Style} as a {@link ResourceStyle} or null.
	 */
	private ResourceStyle getResStyle() {
		return (ResourceStyle) style;
	}




	/**
	 * @return the {@link Style} as a {@link StringStyle} or null.
	 */
	private StringStyle getStrStyle() {
		return (StringStyle) style;
	}




	@Override
	protected boolean isPropertyEqual(final SuiProperty other) {
		final ManagedStyleProperty otherProp = (ManagedStyleProperty) other;
		if (!getStyleService().equals(otherProp.getStyleService())) {
			return false;
		}
		if (usesResourceStyle() != otherProp.usesResourceStyle()) {
			return false;
		}
		if (usesResourceStyle()) {
			return getResStyle().getResource().getPath().equals(otherProp.getResStyle().getResource().getPath())
					&& getResStyle().getResource().isInternal() == otherProp.getResStyle().getResource().isInternal();
		} else {
			final Set<String> styleStringsThis = new HashSet<>(Arrays.asList(getStrStyle().getStyleStrings()));
			final Set<String> styleStringsOther = new HashSet<>(Arrays.asList(otherProp.getStrStyle().getStyleStrings()));
			return styleStringsThis.equals(styleStringsOther);
		}
	}




	@Override
	public String printValue() {
		if (usesResourceStyle()) {
			return getResStyle().getResource().getPath() + (getResStyle().getResource().isInternal() ? " (internal)" : "");
		} else {
			return getStrStyle().getAsSingleString();
		}
	}




	public static class ManagedStyleUpdatingBuilder implements PropFxNodeUpdatingBuilder<ManagedStyleProperty, Node> {


		@Override
		public void build(final SuiNode node, final ManagedStyleProperty property, final Node fxNode) {
			property.getStyleService().registerStyle(property.getStyle(), calcStyleName(property));
			property.getStyleService().applyStyleToExclusive(calcStyleName(property), fxNode);
		}




		@Override
		public MutationResult update(final ManagedStyleProperty property, final SuiNode node, final Node fxNode) {

			node.getPropertyStore().getSafe(ManagedStyleProperty.class).ifPresent(prevStyleProp ->
					prevStyleProp.getStyleService().deregisterStyle(calcStyleName(prevStyleProp)));

			property.getStyleService().registerStyle(property.getStyle(), calcStyleName(property));
			property.getStyleService().applyStyleToExclusive(calcStyleName(property), fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ManagedStyleProperty property, final SuiNode node, final Node fxNode) {
			property.getStyleService().deregisterStyle(calcStyleName(property));
			return MutationResult.MUTATED;
		}




		/**
		 * Calculate the name of the style for the style service from the given property.
		 *
		 * @param property the property
		 * @return the unique name of the style.
		 */
		private String calcStyleName(final ManagedStyleProperty property) {
			return "managedStyleProp-" + Integer.toHexString(property.hashCode());
		}

	}


}
