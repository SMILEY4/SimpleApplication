package simpleui;

import simpleui.core.SComponent;
import simpleui.core.SComponentMaster;
import simpleui.core.SElement;
import simpleui.core.prebuilt.SBox;
import simpleui.core.prebuilt.SButton;

import java.util.ArrayList;
import java.util.List;

public class TestComponentB extends SComponent {


	private int counter = 2;




	public TestComponentB() {
		System.out.println("construct component b");
	}




	@Override
	public SElement render() {
		final List<SElement> buttons = new ArrayList<>();
		for (int i = 0; i < counter; i++) {
			buttons.add(new SButton("B-" + (i + 1)));
		}
		return new SBox(
				new SButton("Add (" + counter + ")", this::onAdd),
				new SBox(buttons)
		);
	}




	private void onAdd() {
		counter++;
		SComponentMaster.instance.reRender(this);
	}

}
