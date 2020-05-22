package simpleui;

import simpleui.core.state.State;

public class TestState implements State {


	public int btnCountA = 0;
	public int btnCountB = 0;
	public String prefixA = "A-";
	public String prefixB = "B";




	@Override
	public TestState copyState() {
		TestState state = new TestState();
		state.btnCountA = this.btnCountA;
		state.btnCountB = this.btnCountB;
		state.prefixA = this.prefixA;
		state.prefixB = this.prefixB;
		return state;
	}


}
