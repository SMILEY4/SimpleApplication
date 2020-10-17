package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedListView;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnItemSelectedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ContentItemsProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MultiselectProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.PromptTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
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




	public static SuiListBuilder create() {
		return new SuiListBuilder();
	}




	/**
	 * Creates a new list
	 *
	 * @param properties the properties
	 * @return the factory for a list
	 */
	public static NodeFactory list(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiList.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiList.class,
				List.of(properties),
				state,
				tags
		);
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
