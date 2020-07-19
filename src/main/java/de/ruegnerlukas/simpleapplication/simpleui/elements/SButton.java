package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
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
import javafx.scene.control.Button;

import java.util.List;

public final class SButton {


	/**
	 * Hidden constructor for utility classes
	 */
	private SButton() {
		// do nothing
	}




	/**
	 * Creates a new button node
	 *
	 * @param properties the properties
	 * @return the factory for a button node
	 */
	public static NodeFactory button(final Property... properties) {
		Properties.checkIllegal(SButton.class, SimpleUIRegistry.get().getEntry(SButton.class).getProperties(), properties);
		return state -> new SNode(SButton.class, List.of(properties), state, null);
	}




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SButton.class, new ButtonNodeBuilder());
		registry.registerProperty(SButton.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SButton.class, SizePreferredProperty.class,
				new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SButton.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SButton.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SButton.class, TextContentProperty.class, new TextContentProperty.TextContentUpdatingBuilder());
		registry.registerProperty(SButton.class, WrapTextProperty.class, new WrapTextProperty.WrapTextUpdatingBuilder());
		registry.registerProperty(SButton.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SButton.class, ActionListenerProperty.class,
				new ActionListenerProperty.ButtonActionListenerUpdatingBuilder());
	}




	private static class ButtonNodeBuilder implements BaseFxNodeBuilder<Button> {


		@Override
		public Button build(final SceneContext context, final SNode node) {
			return new Button();
		}

	}


}
