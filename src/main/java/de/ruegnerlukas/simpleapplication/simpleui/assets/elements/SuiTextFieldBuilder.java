package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTextEnteredEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiTextFieldBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiTextFieldBuilder>,
		RegionBuilderExtension<SuiTextFieldBuilder>,
		CommonEventBuilderExtension<SuiTextFieldBuilder>,
		TextContentProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		AlignmentProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		PromptTextProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		OnTextChangedEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder>,
		OnTextEnteredEventProperty.PropertyBuilderExtension<SuiTextFieldBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiTextField.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
