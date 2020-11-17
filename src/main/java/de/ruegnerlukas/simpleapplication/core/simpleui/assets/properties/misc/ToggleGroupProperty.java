package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedRadioButton;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class ToggleGroupProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ToggleGroupProperty, ToggleGroupProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getGroupId(), b.getGroupId());


	/**
	 * The group id.
	 */
	@Getter
	private final String groupId;




	/**
	 * @param groupId group id.
	 */
	public ToggleGroupProperty(final String groupId) {
		super(ToggleGroupProperty.class, COMPARATOR);
		Validations.INPUT.notEmpty(groupId).exception("The groupId may not be null or empty");
		this.groupId = groupId;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param groupId group id
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T toggleGroup(final String groupId) {
			getBuilderProperties().add(new ToggleGroupProperty(groupId));
			return (T) this;
		}

	}






	public static class RadioButtonUpdatingBuilder implements PropFxNodeUpdatingBuilder<ToggleGroupProperty, ExtendedRadioButton> {


		/**
		 * The provider for the sui registry
		 */
		private final Provider<SuiRegistry> suiRegistryProvider = new Provider<>(SuiRegistry.class);




		@Override
		public void build(final SuiNode node, final ToggleGroupProperty property, final ExtendedRadioButton fxNode) {
			suiRegistryProvider.get().getToggleGroup(property.getGroupId()).ifPresent(fxNode::setToggleGroup);
		}




		@Override
		public MutationResult update(final ToggleGroupProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			suiRegistryProvider.get().getToggleGroup(property.getGroupId()).ifPresent(fxNode::setToggleGroup);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ToggleGroupProperty property, final SuiNode node, final ExtendedRadioButton fxNode) {
			fxNode.setToggleGroup(null);
			return MutationResult.MUTATED;
		}

	}


}



