package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiLabelBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiLabelBuilder>,
		RegionBuilderExtension<SuiLabelBuilder>,
		CommonEventBuilderExtension<SuiLabelBuilder>,
		TextContentProperty.PropertyBuilderExtension<SuiLabelBuilder>,
		WrapTextProperty.PropertyBuilderExtension<SuiLabelBuilder>,
		AlignmentProperty.PropertyBuilderExtension<SuiLabelBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiLabelBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiLabel.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
