package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends Property {


	@Getter
	private final List<NodeFactory> factories;




	public ItemListProperty(final NodeFactory... items) {
		super(ItemListProperty.class);
		this.factories = List.of(items);
	}




	public ItemListProperty(final Collection<NodeFactory> items) {
		super(ItemListProperty.class);
		this.factories = List.copyOf(items);
	}




	public ItemListProperty(final Stream<NodeFactory> items) {
		super(ItemListProperty.class);
		this.factories = List.copyOf(items.collect(Collectors.toList()));
	}




	public ItemListProperty(final ItemListProperty.ItemListFactory factory) {
		super(ItemListProperty.class);
		this.factories = factory.build();
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		final ItemListProperty otherList = (ItemListProperty) other;
		return false; // TODO
	}




	@Override
	public String printValue() {
		return "n=" + getFactories().size();
	}




	public static class ItemListBuilder implements PropFxNodeBuilder<ItemListProperty, Pane> {


		@Override
		public void build(final SceneContext context, final SNode node, final ItemListProperty property, final Pane fxNode) {
			List<Node> childFxNodes = node.getChildren().stream()
					.map(child -> context.getFxNodeBuilder().build(child))
					.collect(Collectors.toList());
			fxNode.getChildren().setAll(childFxNodes);
		}

	}






	public interface ItemListFactory {


		List<NodeFactory> build();

	}

}
