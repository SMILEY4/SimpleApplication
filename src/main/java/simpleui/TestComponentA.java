package simpleui;

import simpleui.core.factories.SComponent;
import simpleui.core.factories.SNodeFactory;
import simpleui.core.state.State;
import simpleui.core.state.StateManager;

import java.util.ArrayList;
import java.util.List;

public class TestComponentA extends SComponent {


	@Override
	public SNodeFactory render(final State state) {
		TestState testState = (TestState) state;

		List<SNodeFactory> nodes = new ArrayList<>();
		nodes.add(SNodeFactory.button("Add", this::onAdd));
		for (int i = 0; i < testState.btnCountA; i++) {
			nodes.add(SNodeFactory.button(testState.prefixA + i));
		}
		nodes.add(new TestComponentB());

		return SNodeFactory.box(
				nodes
		);
	}




	private void onAdd() {
		StateManager.modifyState(state -> {
			TestState testState = (TestState) state;
			testState.btnCountA = testState.btnCountA + 1;
			testState.btnCountB = testState.btnCountB + 2;
			testState.prefixB = "B-na" + (testState.btnCountA + 1) + "-";
		});
	}

}
