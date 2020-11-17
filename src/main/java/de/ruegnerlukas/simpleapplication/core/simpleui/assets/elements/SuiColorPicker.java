package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events.OnSelectedColorEventProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import javafx.scene.control.ColorPicker;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiColorPicker {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiColorPicker() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiColorPickerBuilder create() {
		return new SuiColorPickerBuilder();
	}




	public static class SuiColorPickerBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiColorPickerBuilder>,
			RegionBuilderExtension<SuiColorPickerBuilder>,
			CommonEventBuilderExtension<SuiColorPickerBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiColorPickerBuilder>,
			PromptTextProperty.PropertyBuilderExtension<SuiColorPickerBuilder>,
			OnSelectedColorEventProperty.PropertyBuilderExtension<SuiColorPickerBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiColorPicker.class,
					state,
					tags
			);
		}

	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiColorPicker.class, new FxNodeBuilder());
		registry.registerProperties(SuiColorPicker.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiColorPicker.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiColorPicker.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiColorPicker.class, List.of(
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.ComboBoxBaseUpdatingBuilder<>()),
				PropertyEntry.of(OnSelectedColorEventProperty.class, new OnSelectedColorEventProperty.ColorPickerUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ColorPicker> {


		@Override
		public ColorPicker build(final SuiNode node) {
			return new ColorPicker();
		}

	}


}
