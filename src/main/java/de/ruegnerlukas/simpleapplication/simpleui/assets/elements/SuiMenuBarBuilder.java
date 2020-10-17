package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MenuContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.UseSystemMenuBarProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiMenuBarBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiMenuBarBuilder>,
		RegionBuilderExtension<SuiMenuBarBuilder>,
		CommonEventBuilderExtension<SuiMenuBarBuilder>,
		UseSystemMenuBarProperty.PropertyBuilderExtension<SuiMenuBarBuilder>,
		MenuContentProperty.PropertyBuilderExtension<SuiMenuBarBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiMenuBar.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
