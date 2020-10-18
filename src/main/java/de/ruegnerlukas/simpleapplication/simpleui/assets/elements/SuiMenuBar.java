package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MenuContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.UseSystemMenuBarProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.RegionBuilderExtension;
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




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
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
			return create(
					SuiMenuBar.class,
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
