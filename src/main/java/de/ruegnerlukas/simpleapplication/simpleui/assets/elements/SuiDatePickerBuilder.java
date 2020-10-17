package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedDateEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ChronologyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiDatePickerBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiDatePickerBuilder>,
		RegionBuilderExtension<SuiDatePickerBuilder>,
		CommonEventBuilderExtension<SuiDatePickerBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
		PromptTextProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
		ChronologyProperty.PropertyBuilderExtension<SuiDatePickerBuilder>,
		OnSelectedDateEventProperty.PropertyBuilderExtension<SuiDatePickerBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiDatePicker.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
