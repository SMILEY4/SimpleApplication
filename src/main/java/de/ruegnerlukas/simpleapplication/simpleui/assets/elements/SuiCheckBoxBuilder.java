package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnCheckedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.CheckedProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IconProperty;
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

public class SuiCheckBoxBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiCheckBoxBuilder>,
		RegionBuilderExtension<SuiCheckBoxBuilder>,
		CommonEventBuilderExtension<SuiCheckBoxBuilder>,
		TextContentProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		IconProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		WrapTextProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		AlignmentProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		CheckedProperty.PropertyBuilderExtension<SuiCheckBoxBuilder>,
		OnCheckedEventProperty.PropertyBuilderExtension<SuiCheckBoxBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiCheckbox.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
