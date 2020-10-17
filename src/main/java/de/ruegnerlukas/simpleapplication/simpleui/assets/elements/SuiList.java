package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedListView;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnItemSelectedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MultiselectProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.ListView;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiList {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiList() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiListBuilder create() {
		return new SuiListBuilder();
	}




	public static class SuiListBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiListBuilder>,
			RegionBuilderExtension<SuiListBuilder>,
			CommonEventBuilderExtension<SuiListBuilder>,
			ContentItemsProperty.PropertyBuilderExtensionWithSelected<SuiListBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiListBuilder>,
			PromptTextProperty.PropertyBuilderExtension<SuiListBuilder>,
			MultiselectProperty.PropertyBuilderExtension<SuiListBuilder>,
			OnItemSelectedEventProperty.PropertyBuilderExtension<SuiListBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiList.class,
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
		registry.registerBaseFxNodeBuilder(SuiList.class, new FxNodeBuilder());
		registry.registerProperties(SuiList.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiList.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiList.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiList.class, List.of(
				PropertyEntry.of(ContentItemsProperty.class, new ContentItemsProperty.ListViewUpdatingBuilder<>()),
				PropertyEntry.of(OnItemSelectedEventProperty.class, new OnItemSelectedEventProperty.ListViewUpdatingBuilder<>()),
				PropertyEntry.of(MultiselectProperty.class, new MultiselectProperty.ListViewUpdatingBuilder()),
				PropertyEntry.of(PromptTextProperty.class, new PromptTextProperty.ListViewUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ListView<String>> {


		@Override
		public ListView<String> build(final SuiNode node) {
			return new ExtendedListView<>();
		}

	}


}
