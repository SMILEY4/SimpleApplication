package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChoicesConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiChoiceBoxBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiChoiceBoxBuilder>,
		RegionBuilderExtension<SuiChoiceBoxBuilder>,
		CommonEventBuilderExtension<SuiChoiceBoxBuilder>,
		ContentItemsProperty.PropertyBuilderExtensionWithSelected<SuiChoiceBoxBuilder>,
		ChoicesConverterProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder>,
		OnValueChangedEventProperty.PropertyBuilderExtension<SuiChoiceBoxBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiChoiceBox.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
