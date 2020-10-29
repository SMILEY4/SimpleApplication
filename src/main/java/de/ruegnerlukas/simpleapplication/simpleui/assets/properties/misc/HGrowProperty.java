package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import lombok.Getter;

import java.util.function.BiFunction;

public class HGrowProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<HGrowProperty, HGrowProperty, Boolean> COMPARATOR =
			(a, b) -> a.getPriority() == b.getPriority();


	/**
	 * The priority.
	 */
	@Getter
	private final Priority priority;




	/**
	 * @param priority the priority
	 */
	public HGrowProperty(final Priority priority) {
		super(HGrowProperty.class, COMPARATOR);
		this.priority = priority;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param priority the priority or null
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T hGrow(final Priority priority) {
			getBuilderProperties().add(new HGrowProperty(priority));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<HGrowProperty, Node> {


		@Override
		public void build(final SuiNode node, final HGrowProperty property, final Node fxNode) {
			HBox.setHgrow(fxNode, property.getPriority());
		}




		@Override
		public MutationResult update(final HGrowProperty property, final SuiNode node, final Node fxNode) {
			HBox.setHgrow(fxNode, property.getPriority());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final HGrowProperty property, final SuiNode node, final Node fxNode) {
			HBox.setHgrow(fxNode, null);
			return MutationResult.MUTATED;
		}

	}

}



