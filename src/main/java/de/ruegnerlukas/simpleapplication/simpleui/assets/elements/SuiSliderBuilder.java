package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiSliderBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiSliderBuilder>,
		RegionBuilderExtension<SuiSliderBuilder>,
		CommonEventBuilderExtension<SuiSliderBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		MinMaxProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		BlockIncrementProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		TickMarkProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		OrientationProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		OnValueChangedEventProperty.PropertyBuilderExtension<SuiSliderBuilder>,
		LabelFormatterProperty.PropertyBuilderExtension<SuiSliderBuilder> {


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiSlider.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
