package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;

import java.util.List;

@SuppressWarnings ("unchecked")
public interface BaseBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


	default T properties(final SuiProperty... properties) {
		for (SuiProperty property : properties) {
			getFactoryInternalProperties().add(property);
		}
		return (T) this;
	}

	default T properties(final List<SuiProperty> properties) {
		getFactoryInternalProperties().addAll(properties);
		return (T) this;
	}

	default T id(final String id) {
		getFactoryInternalProperties().add(Properties.id(id));
		return (T) this;
	}

	default T mutationBehaviour(final MutationBehaviourProperty.MutationBehaviour behaviour) {
		return mutationBehaviour(behaviour, null);
	}

	default T mutationBehaviour(final MutationBehaviourProperty.MutationBehaviour behaviour, final TagConditionExpression condition) {
		getFactoryInternalProperties().add(Properties.mutationBehaviour(behaviour, condition));
		return (T) this;
	}

	default T disabled() {
		return disabled(true);
	}

	default T disabled(final boolean disabled) {
		getFactoryInternalProperties().add(Properties.disabled(disabled));
		return (T) this;
	}

	default T anchorsFitParent() {
		getFactoryInternalProperties().add(Properties.anchorFitParent());
		return (T) this;
	}

	default T anchors(final Number top, final Number bottom, final Number left, final Number right) {
		getFactoryInternalProperties().add(Properties.anchor(top, bottom, left, right));
		return (T) this;
	}

	default T title(final String title) {
		getFactoryInternalProperties().add(Properties.title(title));
		return (T) this;
	}

	default T style(final String style) {
		getFactoryInternalProperties().add(Properties.style(style));
		return (T) this;
	}

	default T style(final Resource style) {
		getFactoryInternalProperties().add(Properties.style(style));
		return (T) this;
	}


}
