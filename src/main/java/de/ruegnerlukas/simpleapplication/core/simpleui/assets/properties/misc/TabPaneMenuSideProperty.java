package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.geometry.Side;
import javafx.scene.control.TabPane;
import lombok.Getter;

import java.util.function.BiFunction;

public class TabPaneMenuSideProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TabPaneMenuSideProperty, TabPaneMenuSideProperty, Boolean> COMPARATOR =
			(a, b) -> a.getSide() == b.getSide();


	/**
	 * The side.
	 */
	@Getter
	private final Side side;




	/**
	 * @param side the side
	 */
	public TabPaneMenuSideProperty(final Side side) {
		super(TabPaneMenuSideProperty.class, COMPARATOR);
		Validations.INPUT.notNull(side).exception("The tab menu side may not be null.");
		this.side = side;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param side the side of the tabs
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T tabMenuSide(final Side side) {
			getBuilderProperties().add(new TabPaneMenuSideProperty(side));
			return (T) this;
		}

	}






	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<TabPaneMenuSideProperty, TabPane> {


		@Override
		public void build(final SuiNode node, final TabPaneMenuSideProperty property, final TabPane fxNode) {
			fxNode.setSide(property.getSide());
		}




		@Override
		public MutationResult update(final TabPaneMenuSideProperty property, final SuiNode node, final TabPane fxNode) {
			fxNode.setSide(property.getSide());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TabPaneMenuSideProperty property, final SuiNode node, final TabPane fxNode) {
			fxNode.setSide(Side.TOP);
			return MutationResult.MUTATED;
		}

	}


}



