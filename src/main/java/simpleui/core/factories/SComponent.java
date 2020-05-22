package simpleui.core.factories;

import simpleui.core.nodes.SNode;
import simpleui.core.state.State;

public abstract class SComponent implements SNodeFactory {


	@Override
	public SNode create(final State state) {
		return render(state).create(state);
	}



	public abstract SNodeFactory render(State state);





}
