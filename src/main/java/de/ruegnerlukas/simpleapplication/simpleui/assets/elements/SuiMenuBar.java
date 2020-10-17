package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MenuContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.UseSystemMenuBarProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.scene.control.MenuBar;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiMenuBar {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiMenuBar() {
		// do nothing
	}




	public static SuiMenuBarBuilder create() {
		return new SuiMenuBarBuilder();
	}




	public static class SuiMenuBarBuilder extends BuilderExtensionContainer implements
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




	/**
	 * Creates a new menu bar
	 *
	 * @param properties the properties
	 * @return the factory for a menu bar
	 */
	public static NodeFactory menuBar(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiMenuBar.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiMenuBar.class,
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
		registry.registerBaseFxNodeBuilder(SuiMenuBar.class, new FxNodeBuilder());
		registry.registerProperties(SuiMenuBar.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiMenuBar.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiMenuBar.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiMenuBar.class, List.of(
				PropertyEntry.of(MenuContentProperty.class, new MenuContentProperty.MenuBarUpdatingBuilder()),
				PropertyEntry.of(UseSystemMenuBarProperty.class, new UseSystemMenuBarProperty.MenuBarUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<MenuBar> {


		@Override
		public MenuBar build(final SuiNode node) {
			return new MenuBar();
		}

	}


}
