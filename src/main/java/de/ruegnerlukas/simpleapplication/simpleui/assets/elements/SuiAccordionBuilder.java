package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSectionToggleEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnimateProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ExpandedSectionProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiAccordionBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiAccordionBuilder>,
		RegionBuilderExtension<SuiAccordionBuilder>,
		CommonEventBuilderExtension<SuiAccordionBuilder>,
		ItemListProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
		ItemProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
		ExpandedSectionProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
		AnimateProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
		OnSectionToggleEventProperty.PropertyBuilderExtension<SuiAccordionBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiAccordion.class,
				getFactoryInternalProperties(),
				state,
				tags,
				SuiAccordion.CHILD_LISTENER,
				null
		);
	}


}
