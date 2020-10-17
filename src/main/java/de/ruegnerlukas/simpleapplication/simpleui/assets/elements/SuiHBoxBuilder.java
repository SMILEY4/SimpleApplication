package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.FitToHeightProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiHBoxBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiHBoxBuilder>,
		RegionBuilderExtension<SuiHBoxBuilder>,
		CommonEventBuilderExtension<SuiHBoxBuilder>,
		ItemListProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
		ItemProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
		AlignmentProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
		SpacingProperty.PropertyBuilderExtension<SuiHBoxBuilder>,
		FitToHeightProperty.PropertyBuilderExtension<SuiHBoxBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiHBox.class,
				getFactoryInternalProperties(),
				state,
				tags,
				SuiNodeChildListener.DEFAULT,
				SuiNodeChildTransformListener.DEFAULT
		);
	}


}
