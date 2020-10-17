package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnValueChangedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.BlockIncrementProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.EditableProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelFormatterProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.LabelSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MinMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SpacingProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TickMarkProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public class SuiLabeledSliderBuilder extends BuilderExtensionContainer implements
		BaseBuilderExtension<SuiLabeledSliderBuilder>,
		RegionBuilderExtension<SuiLabeledSliderBuilder>,
		CommonEventBuilderExtension<SuiLabeledSliderBuilder>,
		AlignmentProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		TooltipProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		MinMaxProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		BlockIncrementProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		TickMarkProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		OrientationProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		SpacingProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		EditableProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		LabelFormatterProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		LabelSizeProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>,
		OnValueChangedEventProperty.PropertyBuilderExtension<SuiLabeledSliderBuilder>

{


	@Override
	public SuiNode create(final SuiState state, final Tags tags) {
		return SuiNode.create(
				SuiLabeledSlider.class,
				getFactoryInternalProperties(),
				state,
				tags
		);
	}


}
