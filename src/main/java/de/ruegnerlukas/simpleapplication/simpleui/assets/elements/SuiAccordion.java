package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.BaseBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.PropertyGroups;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.CommonEventBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.OnSectionToggleEventProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnimateProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ExpandedSectionProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TooltipProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.AbstractFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.BuilderExtensionContainer;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.RegionBuilderExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry.PropertyEntry;

public final class SuiAccordion {


	/**
	 * Hidden constructor for utility classes
	 */
	private SuiAccordion() {
		// do nothing
	}




	/**
	 * Build a new element
	 *
	 * @return the builder for the element
	 */
	public static SuiAccordionBuilder create() {
		return new SuiAccordionBuilder();
	}




	public static class SuiAccordionBuilder extends BuilderExtensionContainer implements
			BaseBuilderExtension<SuiAccordionBuilder>,
			RegionBuilderExtension<SuiAccordionBuilder>,
			CommonEventBuilderExtension<SuiAccordionBuilder>,
			ItemListProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
			ItemProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
			ExpandedSectionProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
			AnimateProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
			TooltipProperty.PropertyBuilderExtension<SuiAccordionBuilder>,
			OnSectionToggleEventProperty.PropertyBuilderExtension<SuiAccordionBuilder> {


		@Override
		public SuiNode create(final SuiState state, final Tags tags) {
			return create(
					SuiAccordion.class,
					state,
					tags,
					SuiAccordion.CHILD_LISTENER,
					null
			);
		}


	}






	/**
	 * A child listener applicable to {@link ExtendedAccordion}s.
	 */
	protected static final SuiNodeChildListener CHILD_LISTENER = node -> {
		final ExtendedAccordion accordion = (ExtendedAccordion) node.getFxNodeStore().get();
		if (accordion != null) {
			if (node.getChildNodeStore().hasChildren()) {
				accordion.setSections(node.getChildNodeStore().stream());
			} else {
				accordion.clearSections();
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
				PropertyEntry.of(ExpandedSectionProperty.class, new ExpandedSectionProperty.AccordionUpdatingBuilder()),
				PropertyEntry.of(AnimateProperty.class, new AnimateProperty.AccordionUpdatingBuilder()),
				PropertyEntry.of(OnSectionToggleEventProperty.class, new OnSectionToggleEventProperty.AccordionUpdatingBuilder()),
				PropertyEntry.of(TooltipProperty.class, new TooltipProperty.ControlUpdatingBuilder())
		));
	}




	private static class FxNodeBuilder implements AbstractFxNodeBuilder<ExtendedAccordion> {


		@Override
		public ExtendedAccordion build(final SuiNode node) {
			return new ExtendedAccordion();
		}

	}


}
