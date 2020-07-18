package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.OrientationProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.SizeProperty;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;

import java.util.ArrayList;
import java.util.List;

public class SSeparator {


	public static NodeFactory separator(final Orientation orientation, final Property... properties) {
		final List<Property> propList = new ArrayList<>(List.of(properties));
		propList.add(new OrientationProperty(orientation));
		Properties.checkIllegal(SSeparator.class, SimpleUIRegistry.get().getEntry(SSeparator.class).getProperties(), propList);
		return state -> new SNode(SSeparator.class, propList, state, null);
	}




	public static NodeFactory separator(final Property... properties) {
		Properties.checkIllegal(SSeparator.class, SimpleUIRegistry.get().getEntry(SSeparator.class).getProperties(), properties);
		return state -> new SNode(SSeparator.class, List.of(properties), state, null);
	}




	public static void register(final SimpleUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SSeparator.class, new SSeparator.SeperatorNodeBuilder());
		registry.registerProperty(SSeparator.class, SizeMinProperty.class, new SizeMinProperty.SizeMinUpdatingBuilder());
		registry.registerProperty(SSeparator.class, SizePreferredProperty.class, new SizePreferredProperty.SizePreferredUpdatingBuilder());
		registry.registerProperty(SSeparator.class, SizeMaxProperty.class, new SizeMaxProperty.SizeMaxUpdatingBuilder());
		registry.registerProperty(SSeparator.class, SizeProperty.class, new SizeProperty.SizeUpdatingBuilder());
		registry.registerProperty(SSeparator.class, DisabledProperty.class, new DisabledProperty.DisabledUpdatingBuilder());
		registry.registerProperty(SSeparator.class, OrientationProperty.class, new OrientationProperty.SeparatorOrientationUpdatingBuilder());
	}




	private static class SeperatorNodeBuilder implements BaseFxNodeBuilder<Separator> {


		@Override
		public Separator build(final SceneContext context, final SNode node) {
			return new Separator();
		}

	}

}
