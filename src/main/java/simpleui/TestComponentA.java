package simpleui;

import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuild.SButton;
import simpleui.core.prebuild.SContainer;

public class TestComponentA extends SComponent {


	@Override
	public SElement render() {
		return new SContainer(
				new SButton("Button A-1"),
				new TestComponentB(),
				new SButton("Button A-2")
		);
	}

}
