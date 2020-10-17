package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiTextAreaBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiTextAreaBuilder>,
		RegionBuilderExtension<SuiTextAreaBuilder>,
		CommonEventBuilderExtension<SuiTextAreaBuilder>,
		TextContentProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		PromptTextProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		WrapTextProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		OnTextChangedEventProperty.PropertyBuilderExtension<SuiTextAreaBuilder>,
		OnTextEnteredEventProperty.PropertyBuilderExtension<SuiTextAreaBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiTextArea.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
