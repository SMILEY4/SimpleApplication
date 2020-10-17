package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.TabPane;
import lombok.Getter;

import java.util.function.BiFunction;

public class TabClosingPolicyProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TabClosingPolicyProperty, TabClosingPolicyProperty, Boolean> COMPARATOR =
			(a, b) -> a.getTabClosingPolicy() == b.getTabClosingPolicy();


	/**
	 * The tab closing policy
	 */
	@Getter
	private final TabPane.TabClosingPolicy tabClosingPolicy;




	/**
	 * @param tabClosingPolicy the tab closing policy
	 */
	public TabClosingPolicyProperty(final TabPane.TabClosingPolicy tabClosingPolicy) {
		super(TabClosingPolicyProperty.class, COMPARATOR);
		this.tabClosingPolicy = tabClosingPolicy;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param tabClosingPolicy the policy for closing tabs
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T tabClosingPolicy(final TabPane.TabClosingPolicy tabClosingPolicy) {
			getBuilderProperties().add(new TabClosingPolicyProperty(tabClosingPolicy));
			return (T) this;
		}

	}






	public static class TabPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<TabClosingPolicyProperty, TabPane> {


		@Override
		public void build(final SuiNode node, final TabClosingPolicyProperty property, final TabPane fxNode) {
			fxNode.setTabClosingPolicy(property.getTabClosingPolicy());
		}




		@Override
		public MutationResult update(final TabClosingPolicyProperty property, final SuiNode node, final TabPane fxNode) {
			fxNode.setTabClosingPolicy(property.getTabClosingPolicy());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TabClosingPolicyProperty property, final SuiNode node, final TabPane fxNode) {
			fxNode.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
			return MutationResult.MUTATED;
		}


	}


}



