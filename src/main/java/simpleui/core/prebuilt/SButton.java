package simpleui.core.prebuilt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import simpleui.core.SElement;

@Getter
@AllArgsConstructor
public class SButton extends SElement {


	private final String text;

	private final Listener listener;




	public SButton(String text) {
		this(text, () -> {
		});
	}




	public interface Listener {


		void onAction();

	}

}
