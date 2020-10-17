package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollHorizontalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnScrollVerticalEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToWidthProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ShowScrollbarsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiScrollPaneBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiScrollPaneBuilder>,
		RegionBuilderExtension<SuiScrollPaneBuilder>,
		CommonEventBuilderExtension<SuiScrollPaneBuilder>,
		ItemProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		FitToHeightProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		FitToWidthProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		ShowScrollbarsProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		OnScrollVerticalEventProperty.PropertyBuilderExtension<SuiScrollPaneBuilder>,
		OnScrollHorizontalEventProperty.PropertyBuilderExtension<SuiScrollPaneBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiScrollPane.class,
				getFactoryInternalProperties(),
				state,
				tags,
				SuiNodeChildListener.DEFAULT,
				SuiNodeChildTransformListener.DEFAULT
		);
	}


}
