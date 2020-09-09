package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.core.presentation.style.ResourceStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StringStyle;
import de.ruegnerlukas.simpleapplication.core.presentation.style.Style;
import de.ruegnerlukas.simpleapplication.core.presentation.style.StyleService;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import lombok.Getter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.function.BiFunction;

public class ManagedStyleProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ManagedStyleProperty, ManagedStyleProperty, Boolean> COMPARATOR = (a, b) -> {
		if (a.usesResourceStyle() != b.usesResourceStyle()) {
			return false;
		}
		if (!a.getStyleService().equals(b.getStyleService())) {
			return false;
		}
		if (a.usesResourceStyle()) {
			final String aPath = a.getResStyle().getResource().getPath();
			final String bPath = b.getResStyle().getResource().getPath();
			final boolean aInternal = a.getResStyle().getResource().isInternal();
			final boolean bInternal = b.getResStyle().getResource().isInternal();
			return aInternal == bInternal && aPath.equals(bPath);
		} else {
			final Set<String> styleStringsThis = new HashSet<>(Arrays.asList(a.getStrStyle().getStyleStrings()));
			final Set<String> styleStringsOther = new HashSet<>(Arrays.asList(b.getStrStyle().getStyleStrings()));
			return styleStringsThis.equals(styleStringsOther);
		}
	};

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
		super(ManagedStyleProperty.class, COMPARATOR);
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
