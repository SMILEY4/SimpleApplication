package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.function.BiFunction;

@Getter
public class SizePreferredProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SizePreferredProperty, SizePreferredProperty, Boolean> COMPARATOR = (a, b) ->
			NumberUtils.isEqual(a.getWidth(), b.getWidth()) && NumberUtils.isEqual(a.getHeight(), b.getHeight());
	/**
	 * The preferred width.
	 */
	private final Number width;


	/**
	 * The preferred height.
	 */
	private final Number height;




	/**
	 * @param width  the preferred width.
	 * @param height the preferred height.
	 */
	public SizePreferredProperty(final Number width, final Number height) {
		super(SizePreferredProperty.class, COMPARATOR);
		Validations.INPUT.notNull(width).exception("The width may not be null.");
		Validations.INPUT.notNull(height).exception("The height may not be null.");
		this.width = width;
		this.height = height;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param width  the preferred width.
		 * @param height the preferred height.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T sizePreferred(final Number width, final Number height) {
			getBuilderProperties().add(new SizePreferredProperty(width, height));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<SizePreferredProperty, Region> {


		@Override
		public void build(final SuiNode node,
						  final SizePreferredProperty property,
						  final Region fxNode) {
			fxNode.setPrefSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
		}




		@Override
		public MutationResult update(final SizePreferredProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			fxNode.setPrefSize(property.getWidth().doubleValue(), property.getHeight().doubleValue());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SizePreferredProperty property,
									 final SuiNode node,
									 final Region fxNode) {
			if (node.getPropertyStore().has(SizeProperty.class)) {
				SizeProperty sizeProp = node.getPropertyStore().get(SizeProperty.class);
				fxNode.setPrefSize(sizeProp.getPreferredWidth().doubleValue(), sizeProp.getPreferredHeight().doubleValue());
				return MutationResult.MUTATED;
			} else {
				return MutationResult.REQUIRES_REBUILD;
			}
		}

	}


}
