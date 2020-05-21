package simpleui.core;

import javafx.scene.Node;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public abstract class SElement {


	@Getter
	@Setter (AccessLevel.PROTECTED)
	private SElement parent;

	@Getter
	private List<SElement> children;




	public SElement(SElement... elements) {
		this(List.of(elements));
	}




	public SElement(List<SElement> elements) {
		this.children = new ArrayList<>(elements);
		this.children.forEach(element -> element.setParent(this));
	}




	public SElement render() {
		children.forEach(SElement::render);
		return this;
	}




	public abstract Node getFxNode();


	public abstract void onChildRerenderRequest();




	public void print(int level) {
		System.out.println(" ".repeat(level * 3) + this.toString());
		children.forEach(child -> child.print(level + 1));
	}


}
