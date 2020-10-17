package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnDividerDraggedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SplitDividerPositionProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiSplitPaneBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiSplitPaneBuilder>,
		RegionBuilderExtension<SuiSplitPaneBuilder>,
		CommonEventBuilderExtension<SuiSplitPaneBuilder>,
		ItemProperty.PropertyBuilderExtension<SuiSplitPaneBuilder>,
		ItemListProperty.PropertyBuilderExtension<SuiSplitPaneBuilder>,
		OrientationProperty.PropertyBuilderExtension<SuiSplitPaneBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiSplitPaneBuilder>,
		SplitDividerPositionProperty.PropertyBuilderExtension<SuiSplitPaneBuilder>,
		OnDividerDraggedEventProperty.PropertyBuilderExtension<SuiSplitPaneBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiSplitPane.class,
				getFactoryInternalProperties(),
				state,
				tags,
				SuiSplitPane.CHILD_LISTENER,
				SuiSplitPane.CHILD_TRANSFORM_LISTENER
		);
	}


}
