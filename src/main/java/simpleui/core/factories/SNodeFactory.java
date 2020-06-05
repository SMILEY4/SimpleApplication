package simpleui.core.factories;

import simpleui.core.nodes.SBox;
import simpleui.core.nodes.SButton;
import simpleui.core.nodes.SNode;
import simpleui.core.properties.Property;
import simpleui.core.state.State;

public interface SNodeFactory {


	SNode create(State state);


	static SNodeFactory box(final Property... properties) {
		return state -> new SBox(properties);
	}


	static SNodeFactory button(final Property... properties) {
		return state -> new SButton(properties);
	}

}
