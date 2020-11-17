package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import lombok.Getter;

import java.util.function.Consumer;

public class DirectFxNodeAccessProperty extends SuiProperty {


	/**
	 * The consumer called when the sui node is created
	 */
	@Getter
	private final Consumer<Node> onBuild;

	/**
	 * The consumer called when the properties of the sui node are mutated or the property was added
	 */
	@Getter
	private final Consumer<Node> onMutate;




	/**
	 * @param onBuild  the consumer called when the sui node is created
	 * @param onMutate the consumer called when the properties of the sui node are mutated or the property was added
	 */
	public DirectFxNodeAccessProperty(final Consumer<Node> onBuild, final Consumer<Node> onMutate) {
		super(DirectFxNodeAccessProperty.class, (a, b) -> false);
		Validations.INPUT.notNull(onBuild).exception("The on-build function may not be null,");
		Validations.INPUT.notNull(onMutate).exception("The on-mutate function may not be null,");
		this.onBuild = onBuild;
		this.onMutate = onMutate;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param onAdd    the consumer called when the property was added to the sui node
		 * @param onMutate the consumer called when the properties of the sui node are mutated
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T directFxNodeAccess(final Consumer<Node> onAdd, final Consumer<Node> onMutate) {
			getBuilderProperties().add(new DirectFxNodeAccessProperty(onAdd, onMutate));
			return (T) this;
		}

		/**
		 * @param nodeType the expected type of the javafx node
		 * @param onAdd    the consumer called when the property was added to the sui node
		 * @param onMutate the consumer called when the properties of the sui node are mutated
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default <N extends Node> T directFxNodeAccess(final Class<N> nodeType, final Consumer<N> onAdd, final Consumer<N> onMutate) {
			getBuilderProperties().add(new DirectFxNodeAccessProperty((Consumer<Node>) onAdd, (Consumer<Node>) onMutate));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<DirectFxNodeAccessProperty, Node> {


		@Override
		public void build(final SuiNode node, final DirectFxNodeAccessProperty property, final Node fxNode) {
			property.getOnBuild().accept(fxNode);
		}




		@Override
		public MutationResult update(final DirectFxNodeAccessProperty property, final SuiNode node, final Node fxNode) {
			property.getOnMutate().accept(fxNode);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final DirectFxNodeAccessProperty property, final SuiNode node, final Node fxNode) {
			return MutationResult.MUTATED;
		}

	}


}



