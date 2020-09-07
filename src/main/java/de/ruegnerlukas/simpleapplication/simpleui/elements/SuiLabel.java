package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.builders.BaseFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.AlignmentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.WrapTextProperty;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.control.Label;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.PropertyEntry;
import static de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry.get;

public final class SuiLabel {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiLabel() {
		// do nothing
	}


	/**
	 * Creates a new label node
	 *
	 * @param properties the properties
	 * @return the factory for a label node
	 */
	public static NodeFactory label(final Property... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiLabel.class, get().getEntry(SuiLabel.class).getProperties(), properties);
		return state -> SuiBaseNode.create(
				SuiLabel.class,
				List.of(properties),
				state
		);
	}

	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiLabel.class, new FxNodeBuilder());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiLabel.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiLabel.class, List.of(
				PropertyEntry.of(TextContentProperty.class, new TextContentProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(WrapTextProperty.class, new WrapTextProperty.LabeledUpdatingBuilder()),
				PropertyEntry.of(AlignmentProperty.class, new AlignmentProperty.LabeledUpdatingBuilder())
		));
	}


	private static class FxNodeBuilder implements BaseFxNodeBuilder<Label> {


		@Override
		public Label build(final MasterNodeHandlers nodeHandlers, final SuiNode node) {
			return new Label();
		}

	}



}
