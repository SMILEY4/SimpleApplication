package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnAccordionExpandedEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.control.Accordion;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiAccordion {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiAccordion() {
		// do nothing
	}




	/**
	 * Creates a new accordion
	 *
	 * @param properties the properties
	 * @return the factory for a accordion
	 */
	public static NodeFactory accordion(final SuiProperty... properties) {
		Validations.INPUT.notNull(properties).exception("The properties may not be null.");
		Validations.INPUT.containsNoNull(properties).exception("The properties may not contain null-entries");
		Properties.validate(SuiAccordion.class, properties);
		return (state, tags) -> SuiNode.create(
				SuiAccordion.class,
				List.of(properties),
				state,
				tags,
				CHILD_LISTENER,
				null
		);
	}




	/**
	 * A child listener applicable to {@link Accordion}s.
	 */
	private static final SuiNodeChildListener CHILD_LISTENER = node -> {
		final Accordion accordion = (Accordion) node.getFxNodeStore().get();
		if (accordion != null) {
			if (node.getChildNodeStore().hasChildren()) {
				final List<TitledPane> prevTitlePanes = new ArrayList<>(accordion.getPanes());
				final List<TitledPane> titledPanes = node.getChildNodeStore().stream()
						.map(child -> {
							/*
							reuse titled panes to prevent javafx "issue":
							setting new child nodes causes accordion.expanedPane to be set to null for split-second,
							starting an animation that sets the content to invisible,
							event if we set a new expanded pane in the meantime.
							 */
							TitledPane childTitledPane = null;
							for (int i = 0; i < prevTitlePanes.size(); i++) {
								if (prevTitlePanes.get(i).getContent() == child.getFxNodeStore().get()) {
									childTitledPane = prevTitlePanes.remove(i);
									break;
								}
							}
							if (childTitledPane == null) {
								childTitledPane = createTitlePane(child);
							}
							return childTitledPane;
						})
						.collect(Collectors.toList());
				accordion.getPanes().setAll(titledPanes);
			} else {
				accordion.getPanes().clear();
			}
		}
	};




	/**
	 * Register this node type at the given registry.
	 *
	 * @param registry the registry
	 */
	public static void register(final SuiRegistry registry) {
		registry.registerBaseFxNodeBuilder(SuiAccordion.class, new FxNodeBuilder());
		registry.registerProperties(SuiAccordion.class, PropertyGroups.commonProperties());
		registry.registerProperties(SuiAccordion.class, PropertyGroups.commonRegionProperties());
		registry.registerProperties(SuiAccordion.class, PropertyGroups.commonEventProperties());
		registry.registerProperties(SuiAccordion.class, List.of(
				PropertyEntry.of(ItemListProperty.class, new ItemListProperty.AccordionBuilder(), null),
				PropertyEntry.of(ItemProperty.class, new ItemProperty.AccordionBuilder(), null),
				PropertyEntry.of(OnAccordionExpandedEventProperty.class, new OnAccordionExpandedEventProperty.AccordionUpdatingBuilder())
		));
	}




	/**
	 * Creates a new title pane from the given simpleui-node
	 *
	 * @param node the node
	 * @return the created title pane
	 */
	public static TitledPane createTitlePane(final SuiNode node) {
		final String title = node.getPropertyStore().getSafe(TitleProperty.class)
				.map(TitleProperty::getTitle)
				.orElse("");
		return new TitledPane(title, node.getFxNodeStore().get());
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<Accordion> {


		@Override
		public Accordion build(final SuiNode node) {
			return new Accordion();
		}

	}


}
