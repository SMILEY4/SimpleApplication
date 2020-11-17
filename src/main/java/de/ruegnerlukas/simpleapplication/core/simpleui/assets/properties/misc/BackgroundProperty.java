package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class BackgroundProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<BackgroundProperty, BackgroundProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getBackground(), b.getBackground());

	/**
	 * the background.
	 */
	@Getter
	private final Background background;




	/**
	 * @param background the background
	 */
	public BackgroundProperty(final Background background) {
		super(BackgroundProperty.class, COMPARATOR);
		this.background = background;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param color the color of the background
		 * @return this builder for chaining
		 */
		default T backgroundSolid(final Color color) {
			return background(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
		}


		/**
		 * @param background the javafx background
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T background(final Background background) {
			getBuilderProperties().add(new BackgroundProperty(background));
			return (T) this;
		}

	}






	public static class RegionUpdatingBuilder implements PropFxNodeUpdatingBuilder<BackgroundProperty, Region> {


		@Override
		public void build(final SuiNode node, final BackgroundProperty property, final Region fxNode) {
			fxNode.setBackground(property.getBackground());
		}




		@Override
		public MutationResult update(final BackgroundProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setBackground(property.getBackground());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final BackgroundProperty property, final SuiNode node, final Region fxNode) {
			fxNode.setBackground(null);
			return MutationResult.MUTATED;
		}

	}


}



