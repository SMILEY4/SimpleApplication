package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSelectedTabEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnTabClosedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabClosingPolicyProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TabPaneMenuSideProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiTabPaneBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiTabPaneBuilder>,
		RegionBuilderExtension<SuiTabPaneBuilder>,
		CommonEventBuilderExtension<SuiTabPaneBuilder>,
		ItemProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		ItemListProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		TabPaneMenuSideProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		TabClosingPolicyProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		OnTabClosedEventProperty.PropertyBuilderExtension<SuiTabPaneBuilder>,
		OnSelectedTabEventProperty.PropertyBuilderExtension<SuiTabPaneBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiTabPane.class,
				getFactoryInternalProperties(),
				state,
				tags,
				SuiTabPane.CHILD_NODE_LISTENER,
				null
		);
	}


}
