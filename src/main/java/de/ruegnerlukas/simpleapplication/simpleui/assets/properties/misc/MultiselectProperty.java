package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import lombok.Getter;

import java.util.function.BiFunction;

public class MultiselectProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<MultiselectProperty, MultiselectProperty, Boolean> COMPARATOR =
			(a, b) -> a.isAllowMultiselect() == b.isAllowMultiselect();

	/**
	 * Whether multi-select is enabled.
	 */
	@Getter
	private final boolean allowMultiselect;




	/**
	 * @param allowMultiselect whether multi-select is enabled.
	 */
	public MultiselectProperty(final boolean allowMultiselect) {
		super(MultiselectProperty.class, COMPARATOR);
		this.allowMultiselect = allowMultiselect;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T multiselect() {
			return multiselect(true);
		}

		/**
		 * @param multiselect whether multi-select is enabled
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T multiselect(final boolean multiselect) {
			getFactoryInternalProperties().add(new MultiselectProperty(multiselect));
			return (T) this;
		}

	}






	public static class ListViewUpdatingBuilder implements PropFxNodeUpdatingBuilder<MultiselectProperty, ListView<?>> {


		@Override
		public void build(final SuiNode node, final MultiselectProperty property, final ListView<?> fxNode) {
			fxNode.getSelectionModel().setSelectionMode(property.allowMultiselect ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
		}




		@Override
		public MutationResult update(final MultiselectProperty property, final SuiNode node, final ListView<?> fxNode) {
			fxNode.getSelectionModel().setSelectionMode(property.allowMultiselect ? SelectionMode.MULTIPLE : SelectionMode.SINGLE);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MultiselectProperty property, final SuiNode node, final ListView<?> fxNode) {
			fxNode.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
			return MutationResult.MUTATED;
		}

	}


}
