package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class SizeMaxProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SizeMaxProperty, SizeMaxProperty, Boolean> COMPARATOR = (a, b) ->
			NumberUtils.isEqual(a.getWidth(), b.getWidth()) && NumberUtils.isEqual(a.getHeight(), b.getHeight());

	/**
	 * The maximum width.
	 */
	private final Number width;


	/**
	 * The maximum height.
	 */
	private final Number height;




	/**
	 * @param width  the maximum width.
	 * @param height the maximum height.
	 */
	public SizeMaxProperty(final Number width, final Number height) {
		super(SizeMaxProperty.class, COMPARATOR);
		Validations.INPUT.notNull(width).exception("The width may not be null.");
		Validations.INPUT.notNull(height).exception("The height may not be null.");
		this.width = width;
		this.height = height;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param width  the maximum width.
		 * @param height the maximum height.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T sizeMax(final Number width, final Number height) {
			getBuilderProperties().add(new SizeMaxProperty(width, height));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizeMaxProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizeMaxProperty property,
						  final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setMaxSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizeMaxProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setMaxSize(sizeProp.getMaxWidth().doubleValue(), sizeProp.getMaxHeight().doubleValue());
			} else {
				fxNode.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			}
			return MutationResult.MUTATED;
		}

	}


}
