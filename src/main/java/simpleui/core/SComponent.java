package simpleui.core;

import lombok.Getter;
import lombok.Setter;

public abstract class SComponent extends SElement {


	@Getter
	@Setter
	private SElement subElement;




	public abstract SElement render();


}
