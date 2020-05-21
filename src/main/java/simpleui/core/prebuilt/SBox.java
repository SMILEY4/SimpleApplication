package simpleui.core.prebuilt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import simpleui.core.SElement;

import java.util.List;


@Getter
@AllArgsConstructor
public class SBox extends SElement {


	private final List<SElement> elements;




	public SBox(SElement... element) {
		this(List.of(element));
	}


}
