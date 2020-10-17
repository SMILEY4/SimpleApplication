package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemConverterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SearchableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiComboBoxBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiComboBoxBuilder>,
		RegionBuilderExtension<SuiComboBoxBuilder>,
		CommonEventBuilderExtension<SuiComboBoxBuilder>,
		ContentItemsProperty.PropertyBuilderExtensionWithSelected<SuiComboBoxBuilder>,
		ContentItemConverterProperty.PropertyBuilderExtension<SuiComboBoxBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiComboBoxBuilder>,
		OnValueChangedEventProperty.PropertyBuilderExtension<SuiComboBoxBuilder>,
		PromptTextProperty.PropertyBuilderExtension<SuiComboBoxBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiComboBoxBuilder>,
		SearchableProperty.PropertyBuilderExtension<SuiComboBoxBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiComboBox.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
