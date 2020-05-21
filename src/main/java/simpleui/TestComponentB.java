package simpleui;

import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuild.SButton;
import simpleui.core.prebuild.SContainer;

import java.util.ArrayList;
import java.util.List;

public class TestComponentB extends SComponent {


	private int buttonCount = 2;




	@Override
	public SElement render() {
		return new SContainer(buildButtons());
	}




	private List<SElement> buildButtons() {
		List<SElement> buttons = new ArrayList<>();
		for (int i = 0; i < this.buttonCount; i++) {
			buttons.add(new SButton("Button B-" + i));
		}
		buttons.add(new SButton("Add B", () -> {
			System.out.println("on button B");
			this.buttonCount++;
			triggerReRender();
		}));
		return buttons;
	}

}