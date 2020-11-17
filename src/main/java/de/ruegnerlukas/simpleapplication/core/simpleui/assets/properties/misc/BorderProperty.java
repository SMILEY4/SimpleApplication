package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.layout.Border;
import javafx.scene.layout.Region;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class BorderProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<BorderProperty, BorderProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getBorder(), b.getBorder());

	/**
	 * the border.
	 */
	@Getter
	private final Border border;




	/**
	 * @param border the border
	 */
	public BorderProperty(final Border border) {
		super(BorderProperty.class, COMPARATOR);
		this.border = border;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param border the javafx border
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T border(final Border border) {
			getBuilderProperties().add(new BorderProperty(border));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<BorderProperty, Region> {


		@Override
		public void build(final SuiNode node, final BorderProperty property, final Region fxNode) {
			fxNode.setBorder(property.getBorder());
		}




		@Override
		public MutationResult update(final BorderProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setBorder(property.getBorder());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final BorderProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setBorder(null);
			return MutationResult.MUTATED;
		}

	}


}



