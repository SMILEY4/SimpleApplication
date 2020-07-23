package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ActionListenerProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.Button;

import java.util.List;

public final class SUIButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUIButton() {
		// do nothing
	}




	/**
	 * Creates a new button node
	 *
	 * @param properties the properties
	 * @return the factory for a button node
	 */
	public static NodeFactory button(final Property... properties) {
		Properties.checkIllegal(SUIButton.class, SUIRegistry.get().getEntry(SUIButton.class).getProperties(), properties);
		return state -> new SUINode(SUIButton.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUIButton.class, new ButtonNodeBuilder());
		registry.registerProperty(SUIButton.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SUIButton.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SUIButton.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SUIButton.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SUIButton.class, TextContentProperty.class, new TextContentProperty.TextContentUpdatingBuilder());
		registry.registerProperty(SUIButton.class, WrapTextProperty.class, new WrapTextProperty.WrapTextUpdatingBuilder());
		registry.registerProperty(SUIButton.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SUIButton.class, ActionListenerProperty.class,
				new ActionListenerProperty.ButtonActionListenerUpdatingBuilder());
	}




	private static class ButtonNodeBuilder implements BaseFxNodeBuilder<Button> {


		@Override
		public Button build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new Button();
		}

	}


}
