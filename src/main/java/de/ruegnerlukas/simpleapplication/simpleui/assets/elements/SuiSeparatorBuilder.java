package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiSeparatorBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiSeparatorBuilder>,
		RegionBuilderExtension<SuiSeparatorBuilder>,
		CommonEventBuilderExtension<SuiSeparatorBuilder>,
		OrientationProperty.PropertyBuilderExtension<SuiSeparatorBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiSeparatorBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiSeparator.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
