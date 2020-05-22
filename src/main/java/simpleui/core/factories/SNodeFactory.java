package simpleui.core.factories;

import simpleui.core.nodes.SBox;
import simpleui.core.nodes.SButton;
import simpleui.core.nodes.SNode;
import simpleui.core.state.State;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public interface SNodeFactory {


	SNode create(State state);


	static SNodeFactory box(final SNodeFactory... items) {
		return box(List.of(items));
	}

	static SNodeFactory box(final Stream<SNodeFactory> items) {
		return box(items.collect(Collectors.toList()));
	}

	static SNodeFactory box(final List<SNodeFactory> items) {
		return state -> new SBox(items.stream().map(f -> f.create(state)).collect(Collectors.toList()));
	}


	static SNodeFactory button(final String text) {
		return state -> new SButton(text);
	}

	static SNodeFactory button(final String text, final SButton.ButtonListener listener) {
		return state -> new SButton(text, listener);
	}

}
