package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.Label;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry.get;

public final class SUILabel {


	/**
	 * Hidden constructor for utility classes
	 */
	private SUILabel() {
		// do nothing
	}


	/**
	 * Creates a new label node
	 *
	 * @param properties the properties
	 * @return the factory for a label node
	 */
	public static NodeFactory label(final Property... properties) {
		Properties.validate(SUILabel.class, get().getEntry(SUILabel.class).getProperties(), properties);
		return state -> new SUINode(SUILabel.class, List.of(properties), state, null);
	}

	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SUIRegistry registry) {
		registry.registerBaseFxNodeBuilder(SUILabel.class, new SUILabel.LabelNodeBuilder());
		registry.registerProperties(SUILabel.class, PropertyGroups.commonProperties());
		registry.registerProperties(SUILabel.class, PropertyGroups.commonNodeProperties());
		registry.registerProperties(SUILabel.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SUILabel.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SUILabel.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.TextContentUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.WrapTextUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledAlignmentUpdatingBuilder())
		));
	}


	private static class LabelNodeBuilder implements BaseFxNodeBuilder<Label> {


		@Override
		public Label build(final MasterNodeHandlers nodeHandlers, final SUINode node) {
			return new Label();
		}

	}



}
