package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ImageProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ImageSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PreserveRatioProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiImageBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiImageBuilder>,
		RegionBuilderExtension<SuiImageBuilder>,
		CommonEventBuilderExtension<SuiImageBuilder>,
		PreserveRatioProperty.PropertyBuilderExtension<SuiImageBuilder>,
		ImageSizeProperty.PropertyBuilderExtension<SuiImageBuilder>,
		ImageProperty.PropertyBuilderExtension<SuiImageBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiImage.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
