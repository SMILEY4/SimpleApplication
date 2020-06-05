package simpleui.core.properties;

import lombok.Getter;
import simpleui.core.nodes.SNode;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ItemListProperty extends Property {


	@Getter
	private final List<SNode> items;




	public ItemListProperty(final SNode... items) {
		super(ItemListProperty.class);
		this.items = List.of(items);
	}




	public ItemListProperty(final Collection<SNode> items) {
		super(ItemListProperty.class);
		this.items = List.copyOf(items);
	}




	public ItemListProperty(final Stream<SNode> items) {
		super(ItemListProperty.class);
		this.items = List.copyOf(items.collect(Collectors.toList()));
	}




	public ItemListProperty(final ItemListFactory factory) {
		super(ItemListProperty.class);
		this.items = List.copyOf(factory.build());
	}




	@Override
	protected boolean compareProperty(final Property other) {
		final ItemListProperty otherList = (ItemListProperty) other;
		return false; // TODO
	}




	interface ItemListFactory {


		List<SNode> build();

	}

}
