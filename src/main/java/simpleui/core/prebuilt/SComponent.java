package simpleui.core.prebuilt;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import simpleui.core.SElement;

public abstract class SComponent extends SElement {


	@Getter
	@Setter
	private SElement subElement;




	public abstract SElement render();




	@Override
	public void buildTree() {
		setSubElement(render());
		getSubElement().buildTree();
	}




	@Override
	public Node buildFXNode() {
		return getSubElement().buildFXNode();
	}




	@Override
	public void print(final int level) {
		final StringBuilder builder = new StringBuilder();
		builder.append(" ".repeat(level * 3));
		builder.append(getClass().getSimpleName()).append(" ");
		builder.append(" ").append(Integer.toHexString(hashCode()));
		if (getSubElement() != null) {
			builder.append("  parent");
		}

		System.out.println(builder.toString());

		if (getSubElement() != null) {
			getSubElement().print(level + 1);
		}
	}

}
