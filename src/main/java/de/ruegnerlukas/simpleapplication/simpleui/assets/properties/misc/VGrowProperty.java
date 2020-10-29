package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.function.BiFunction;

public class VGrowProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<VGrowProperty, VGrowProperty, Boolean> COMPARATOR =
			(a, b) -> a.getPriority() == b.getPriority();


	/**
	 * The priority.
	 */
	@Getter
	private final Priority priority;




	/**
	 * @param priority the priority
	 */
	public VGrowProperty(final Priority priority) {
		super(VGrowProperty.class, COMPARATOR);
		this.priority = priority;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param priority the priority or null
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T vGrow(final Priority priority) {
			getBuilderProperties().add(new VGrowProperty(priority));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<VGrowProperty, Node> {


		@Override
		public void build(final SuiNode node, final VGrowProperty property, final Node fxNode) {
			VBox.setVgrow(fxNode, property.getPriority());
		}




		@Override
		public MutationResult update(final VGrowProperty property, final SuiNode node, final Node fxNode) {
			VBox.setVgrow(fxNode, property.getPriority());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final VGrowProperty property, final SuiNode node, final Node fxNode) {
			VBox.setVgrow(fxNode, null);
			return MutationResult.MUTATED;
		}

	}

}



