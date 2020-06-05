package simpleui;

import simpleui.core.factories.SComponent;
import simpleui.core.factories.SNodeFactory;
import simpleui.core.nodes.SNode;
import simpleui.core.properties.Properties;
import simpleui.core.state.State;

import java.util.stream.IntStream;

import static simpleui.core.factories.SNodeFactory.box;
import static simpleui.core.factories.SNodeFactory.button;

public class TestComponentB extends SComponent {


	@Override
	public SNodeFactory render(final State state) {
		TestState testState = (TestState) state;
		return box(
				Properties.items(IntStream.range(0, testState.btnCountB)
						.mapToObj(i -> button(Properties.buttonText(testState.prefixB + i)))
						.map(obj -> (SNode) obj)
				)
		);
	}

}
