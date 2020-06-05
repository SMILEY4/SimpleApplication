package simpleui.core.nodes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import simpleui.core.properties.ItemListProperty;
import simpleui.core.properties.Property;

import java.util.List;

public class SBox extends SNode {


	public SBox(final Property... properties) {
		super(properties);
	}




	@Override
	protected Node buildFxNode() {
		VBox vBox = new VBox();
		vBox.setPadding(new Insets(0, 0, 0, 20));
		for (SNode item : getItems()) {
			vBox.getChildren().add(item.createLinkedFxNode());
		}
		return vBox;
	}




	@Override
	protected MutationResult mutateUpdateProperty(final Class<? extends Property> propKey, final Property propThis, final Property propOther) {
		if (propKey == ItemListProperty.class) {
			/*
			TODO:
			SBOX
			- finish mutation methods
			GENERAL
			- replace old props with new ones after mutation
			- handle updating props, e.g. items:
				- dont replace whole prop when only one item changed
			- handle: items should be SNodeFactories not SNodes ! + handle state
				- before: items were created from factories in SNodeFactory, e.g.:

				static SNodeFactory box(final List<SNodeFactory> items) {
					return state -> new SBox(items.stream().map(f -> f.create(state)).collect(Collectors.toList()));
			 */

		}
		return MutationResult.MUTATED;
	}




	@Override
	protected MutationResult mutateRemoveProperty(final Class<? extends Property> propKey, final Property propThis) {
		if (propKey == ItemListProperty.class) {
			getFxBox().getChildren().clear();
		}
		return MutationResult.MUTATED;
	}




	@Override
	protected MutationResult mutateAddProperty(final Class<? extends Property> propKey, final Property propOther) {
		if (propKey == ItemListProperty.class) {
			final ItemListProperty listProperty = (ItemListProperty) propOther;
			listProperty.getItems().forEach(item -> getFxBox().getChildren().add(item.createLinkedFxNode()));
		}
		return MutationResult.MUTATED;
	}




	//	@Override
//	public boolean mutate(final SNode other) {
//		if (other instanceof SBox) {
//			final SBox boxOther = (SBox) other;
//
//			List<SNode> toRemove = new ArrayList<>();
//
//			for (int i = 0; i < Math.max(getItems().size(), boxOther.getItems().size()); i++) {
//				SNode childThis = getItems().size() <= i ? null : getItems().get(i);
//				SNode childOther = boxOther.getItems().size() <= i ? null : boxOther.getItems().get(i);
//
//				if (childThis != null && childOther != null) {
//					boolean rebuildChild = childThis.mutate(childOther);
//					if (rebuildChild) {
//						System.out.println("mutate box - replace child");
//						patchReplaceChild(i, childOther);
//					}
//				}
//
//				if (childThis == null && childOther != null) {
//					System.out.println("mutate box - add child");
//					patchNewChild(childOther);
//				}
//
//				if (childThis != null && childOther == null) {
//					toRemove.add(childThis);
//				}
//
//			}
//
//			toRemove.forEach(child -> {
//				System.out.println("mutate box - remove child");
//				patchRemoveChild(child);
//			});
//
//			return false;
//
//		} else {
//			System.out.println("mutate box - rebuild");
//			return true;
//		}
//	}
//
	private void patchReplaceChild(final int index, final SNode newChild) {
		final Node newFxNode = newChild.createLinkedFxNode();
		getFxBox().getChildren().set(index, newFxNode);
		getItems().set(index, newChild);
	}




	private void patchNewChild(final SNode newChild) {
		getFxBox().getChildren().add(newChild.createLinkedFxNode());
	}




	private void patchRemoveChild(final SNode child) {
		getFxBox().getChildren().remove(child.getLinkedFxNode());
		this.getItems().remove(child);
	}




	private ItemListProperty getItemListProperty() {
		return this.getProperty(ItemListProperty.class);
	}




	private List<SNode> getItems() {
		final ItemListProperty property = getItemListProperty();
		if (property != null) {
			return property.getItems();
		} else {
			return List.of();
		}
	}




	private VBox getFxBox() {
		return (VBox) getLinkedFxNode();
	}




	@Override
	public void print(final int level) {
		System.out.println(" ".repeat(level * 3)
				+ getClass().getSimpleName()
				+ " #" + Integer.toHexString(hashCode())
				+ (getLinkedFxNode() != null ? " (fx)" : "")
		);
		getItems().forEach(item -> item.print(level + 1));
	}


}
