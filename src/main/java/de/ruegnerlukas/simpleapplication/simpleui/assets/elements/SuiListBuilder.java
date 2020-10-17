package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnItemSelectedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MultiselectProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiListBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiListBuilder>,
		RegionBuilderExtension<SuiListBuilder>,
		CommonEventBuilderExtension<SuiListBuilder>,
		ContentItemsProperty.PropertyBuilderExtensionWithSelected<SuiListBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiListBuilder>,
		PromptTextProperty.PropertyBuilderExtension<SuiListBuilder>,
		MultiselectProperty.PropertyBuilderExtension<SuiListBuilder>,
		OnItemSelectedEventProperty.PropertyBuilderExtension<SuiListBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiList.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
