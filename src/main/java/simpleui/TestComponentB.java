package simpleui;

import simpleui.core.factories.SComponent;
import simpleui.core.factories.SNodeFactory;
import simpleui.core.state.State;

import java.util.stream.IntStream;

public class TestComponentB extends SComponent {


	@Override
	public SNodeFactory render(final State state) {
		TestState testState = (TestState) state;
		return SNodeFactory.box(
				IntStream.range(0, testState.btnCountB)
						.mapToObj(i -> SNodeFactory.button(testState.prefixB+i))
		);
	}

}
