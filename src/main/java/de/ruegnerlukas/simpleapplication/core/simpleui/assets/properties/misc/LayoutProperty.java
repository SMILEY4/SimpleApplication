package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.function.BiFunction;

public class LayoutProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<LayoutProperty, LayoutProperty, Boolean> COMPARATOR =
			(a, b) -> a.getLayoutFunction().equals(b.getLayoutFunction());


	/**
	 * The function used to calculate the layout of the child nodes
	 */
	@Getter
	private final ExtendedPane.LayoutFunction layoutFunction;




	/**
	 * @param propertyId     see {@link SuiProperty#getPropertyId()}
	 * @param layoutFunction the function used to calculate the layout of the child nodes
	 */
	public LayoutProperty(final String propertyId, final ExtendedPane.LayoutFunction layoutFunction) {
		super(LayoutProperty.class, COMPARATOR, propertyId);
		Validations.INPUT.notNull(layoutFunction).exception("The layout function may not be null.");
		this.layoutFunction = layoutFunction;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId     see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param layoutFunction the layout function
		 * @return
		 */
		@SuppressWarnings ("unchecked")
		default T layout(final String propertyId, final ExtendedPane.LayoutFunction layoutFunction) {
			getBuilderProperties().add(new LayoutProperty(propertyId, layoutFunction));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<LayoutProperty, ExtendedPane> {


		@Override
		public void build(final SuiNode node, final LayoutProperty property, final ExtendedPane fxNode) {
			fxNode.setLayoutFunction(property.getLayoutFunction());
		}




		@Override
		public MutationResult update(final LayoutProperty property, final SuiNode node, final ExtendedPane fxNode) {
			fxNode.setLayoutFunction(property.getLayoutFunction());
			fxNode.layout();
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final LayoutProperty property, final SuiNode node, final ExtendedPane fxNode) {
			fxNode.setLayoutFunction(null);
			fxNode.layout();
			return MutationResult.REQUIRES_REBUILD;
		}

	}


}



