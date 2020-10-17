package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
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
		this.layoutFunction = layoutFunction;
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T layout(final String propertyId, final ExtendedPane.LayoutFunction layoutFunction) {
			getFactoryInternalProperties().add(Properties.layout(propertyId, layoutFunction));
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



