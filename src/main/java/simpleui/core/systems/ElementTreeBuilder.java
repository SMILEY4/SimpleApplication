package simpleui.core.systems;

import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuilt.SBox;
import simpleui.core.prebuilt.SButton;

public class ElementTreeBuilder {


	public void build(SElement element) {
		if (element instanceof SComponent) {
			final SComponent component = (SComponent) element;
			component.setSubElement(component.render());
			build(component.getSubElement());
		}
		if (element instanceof SButton) {
		}
		if (element instanceof SBox) {
			final SBox box = (SBox) element;
			box.getElements().forEach(this::build);
		}
	}


}
