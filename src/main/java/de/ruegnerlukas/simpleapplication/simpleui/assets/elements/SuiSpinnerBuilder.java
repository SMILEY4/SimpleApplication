package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpinnerFactoryProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiSpinnerBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiSpinnerBuilder>,
		RegionBuilderExtension<SuiSpinnerBuilder>,
		CommonEventBuilderExtension<SuiSpinnerBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
		OnValueChangedEventProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiSpinnerBuilder>,
		SpinnerFactoryProperty.PropertyBuilderExtension<SuiSpinnerBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiSpinner.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
