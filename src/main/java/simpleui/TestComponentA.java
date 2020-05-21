package simpleui;

import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuilt.SBox;
import simpleui.core.prebuilt.SButton;

public class TestComponentA extends SComponent {


	@Override
	public SElement render() {
		return new SBox(

				new SButton("A-1"),

				new SBox(
						new SButton("A-2.1"),
						new SButton("A-2.2")
				),

				new SButton("A-3"),

				new TestComponentB(),

				new SButton("A-4")
		);
	}

}
