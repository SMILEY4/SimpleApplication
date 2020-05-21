package simpleui.core.systems;

import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuilt.SBox;

public class ElementTreePrinter {


	public void print(SElement element) {
		print(element, 0);
		System.out.println("");
	}




	private void print(SElement element, int level) {
		System.out.println(" ".repeat(level * 3)
						+ element.getClass().getSimpleName()
						+ " #" + Integer.toHexString(element.hashCode()));

		if (element instanceof SComponent) {
			final SComponent component = (SComponent) element;
			print(component.getSubElement(), level+1);
		}
		if (element instanceof SBox) {
			final SBox box = (SBox) element;
			box.getElements().forEach(e -> print(e, level + 1));
		}
	}

}
