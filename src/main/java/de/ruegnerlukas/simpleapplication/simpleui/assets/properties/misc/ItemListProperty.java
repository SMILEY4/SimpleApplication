package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ItemListProperty, ItemListProperty, Boolean> COMPARATOR = (a, b) -> false;


	/**
	 * The factories for creating the items/nodes.
	 */
	@Getter
	private final List<NodeFactory> factories;




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final NodeFactory... items) {
		super(ItemListProperty.class, COMPARATOR);
		Validations.INPUT.notNull(items).exception("The items may not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items may not contain null-elements.");
		this.factories = List.of(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Collection<NodeFactory> items) {
		super(ItemListProperty.class, COMPARATOR);
		Validations.INPUT.notNull(items).exception("The items may not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items may not contain null-elements.");
		this.factories = List.copyOf(items);
	}




	/**
	 * @param items the factories for creating the items/nodes.
	 */
	public ItemListProperty(final Stream<NodeFactory> items) {
		super(ItemListProperty.class, COMPARATOR);
		Validations.INPUT.notNull(items).exception("The items may not be null.");
		this.factories = List.copyOf(items.collect(Collectors.toList()));
	}




	/**
	 * @param supplier the supplier for creating factories for creating the items/nodes.
	 */
	public ItemListProperty(final Supplier<List<NodeFactory>> supplier) {
		super(ItemListProperty.class, COMPARATOR);
		Validations.INPUT.notNull(supplier).exception("The item-supplier may not be null.");
		this.factories = supplier.get();
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param items the factories for creating the items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T items(final NodeFactory... items) {
			getBuilderProperties().add(new ItemListProperty(items));
			return (T) this;
		}

		/**
		 * @param items the factories for creating the items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T items(final Collection<NodeFactory> items) {
			getBuilderProperties().add(new ItemListProperty(items));
			return (T) this;
		}

		/**
		 * @param items the factories for creating the items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T items(final Stream<NodeFactory> items) {
			getBuilderProperties().add(new ItemListProperty(items));
			return (T) this;
		}

		/**
		 * @param supplier the supplier for creating factories for creating the items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T items(final Supplier<List<NodeFactory>> supplier) {
			getBuilderProperties().add(new ItemListProperty(supplier));
			return (T) this;
		}

	}






	public static class PaneBuilder implements PropFxNodeBuilder<ItemListProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final Pane fxNode) {
			fxNode.getChildren().setAll(node.getChildNodeStore().stream()
					.map(child -> child.getFxNodeStore().get())
					.collect(Collectors.toList())
			);
		}

	}






	public static class TabPaneBuilder implements PropFxNodeBuilder<ItemListProperty, ExtendedTabPane> {


		@Override
		public void build(final SuiNode node, final ItemListProperty property, final ExtendedTabPane fxNode) {
			fxNode.setTabs(SuiTabPane.createTabs(node.getChildNodeStore().getUnmodifiable()));
		}

	}






	public static class SplitPaneBuilder implements PropFxNodeBuilder<ItemListProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final SplitPane fxNode) {
			fxNode.getItems().setAll(
					node.getChildNodeStore().stream()
							.map(child -> child.getFxNodeStore().get())
							.collect(Collectors.toList())
			);

		}

	}






	public static class AccordionBuilder implements PropFxNodeBuilder<ItemListProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final ItemListProperty property, final ExtendedAccordion fxNode) {
			if (node.getChildNodeStore().hasChildren()) {
				fxNode.setSections(node.getChildNodeStore().stream());
			} else {
				fxNode.clearSections();
			}
			callOtherPropBuilder(SuiAccordion.class, ExpandedSectionProperty.class, node, fxNode);
		}

	}


}
