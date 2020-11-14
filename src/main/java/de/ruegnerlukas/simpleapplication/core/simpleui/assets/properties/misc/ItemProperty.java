package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiAccordion;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedTabPane;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Stream;

public class ItemProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ItemProperty, ItemProperty, Boolean> COMPARATOR = (a, b) -> false;

	/**
	 * The factory for creating the item/node.
	 */
	@Getter
	private final NodeFactory factory;




	/**
	 * Creates an item property without a factory.
	 */
	protected ItemProperty() {
		super(ItemProperty.class, COMPARATOR);
		this.factory = null;
	}




	/**
	 * @param item the factory for creating the item/node.
	 */
	public ItemProperty(final NodeFactory item) {
		super(ItemProperty.class, COMPARATOR);
		this.factory = item;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension>
			extends FactoryExtension, InjectableItemProperty.PropertyBuilderExtension<T> {


		/**
		 * @param item the factory for creating the item/node.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T item(final NodeFactory item) {
			getBuilderProperties().add(new ItemProperty(item));
			return (T) this;
		}


	}






	public static class PaneBuilder implements PropFxNodeBuilder<ItemProperty, Pane> {


		@Override
		public void build(final SuiNode node,
						  final ItemProperty property,
						  final Pane fxNode) {
			if (node.getChildNodeStore().hasChildren()) {
				fxNode.getChildren().setAll(node.getChildNodeStore().getOne().getFxNodeStore().get());
			} else {
				fxNode.getChildren().clear();
			}
		}

	}






	public static class ScrollPaneBuilder implements PropFxNodeBuilder<ItemProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemProperty property,
						  final ScrollPane fxNode) {
			Node fxChildNode = null;
			if (node.getChildNodeStore().hasChildren()) {
				fxChildNode = node.getChildNodeStore().getOne().getFxNodeStore().get();
			}
			fxNode.setContent(fxChildNode);
		}

	}






	public static class TabPaneBuilder implements PropFxNodeBuilder<ItemListProperty, ExtendedTabPane> {


		@Override
		public void build(final SuiNode node, final ItemListProperty property, final ExtendedTabPane fxNode) {
			fxNode.setTabs(SuiTabPane.createTabs(List.of(node.getChildNodeStore().getOne())));
		}

	}






	public static class SplitPaneBuilder implements PropFxNodeBuilder<ItemListProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final ItemListProperty property,
						  final SplitPane fxNode) {
			fxNode.getItems().setAll(node.getChildNodeStore().getOne().getFxNodeStore().get());
		}

	}






	public static class AccordionBuilder implements PropFxNodeBuilder<ItemListProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final ItemListProperty property, final ExtendedAccordion fxNode) {
			if (node.getChildNodeStore().hasChildren()) {
				fxNode.setSections(Stream.of(node.getChildNodeStore().getOne()));
			} else {
				fxNode.clearSections();
			}
			callOtherPropBuilder(SuiAccordion.class, ExpandedSectionProperty.class, node, fxNode);
		}

	}


}
