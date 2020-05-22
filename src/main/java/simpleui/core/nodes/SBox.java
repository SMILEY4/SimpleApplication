package simpleui.core.nodes;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class SBox extends SNode {


	@Getter
	private final List<SNode> items;




	public SBox(SNode... items) {
		this(List.of(items));
	}




	public SBox(List<SNode> items) {
		this.items = new ArrayList<>(items);
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
	public boolean mutate(final SNode other) {
		if (other instanceof SBox) {
			final SBox boxOther = (SBox) other;

			List<SNode> toRemove = new ArrayList<>();

			for (int i = 0; i < Math.max(getItems().size(), boxOther.getItems().size()); i++) {
				SNode childThis = getItems().size() <= i ? null : getItems().get(i);
				SNode childOther = boxOther.getItems().size() <= i ? null : boxOther.getItems().get(i);

				if (childThis != null && childOther != null) {
					boolean rebuildChild = childThis.mutate(childOther);
					if (rebuildChild) {
						System.out.println("mutate box - replace child");
						patchReplaceChild(i, childOther);
					}
				}

				if (childThis == null && childOther != null) {
					System.out.println("mutate box - add child");
					patchNewChild(childOther);
				}

				if (childThis != null && childOther == null) {
					toRemove.add(childThis);
				}

			}

			toRemove.forEach(child -> {
				System.out.println("mutate box - remove child");
				patchRemoveChild(child);
			});

			return false;

		} else {
			System.out.println("mutate box - rebuild");
			return true;
		}
	}




	private void patchReplaceChild(int index, SNode newChild) {
		if (getLinkedFxNode() != null) {
			final Node newFxNode = newChild.createLinkedFxNode();
			((VBox) getLinkedFxNode()).getChildren().set(index, newFxNode);
		}
		getItems().set(index, newChild);
	}




	private void patchNewChild(SNode newChild) {
		if (getLinkedFxNode() != null) {
			((VBox) getLinkedFxNode()).getChildren().add(newChild.createLinkedFxNode());
		}
		getItems().add(newChild);
	}




	private void patchRemoveChild(SNode child) {
		if (getLinkedFxNode() != null) {
			((VBox) getLinkedFxNode()).getChildren().remove(child.getLinkedFxNode());
		}
		this.getItems().remove(child);
	}




	@Override
	public void print(final int level) {
		System.out.println(" ".repeat(level * 3)
				+ getClass().getSimpleName()
				+ " #" + Integer.toHexString(hashCode())
				+ (getLinkedFxNode() != null ? " (fx)" : "")
		);
		items.forEach(item -> item.print(level + 1));
	}


}
