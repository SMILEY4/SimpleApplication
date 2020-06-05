package simpleui.core.properties;

import lombok.Getter;

public class ActionListenerProperty extends Property {


	@Getter
	private final ActionListener listener;




	public ActionListenerProperty(final ActionListener listener) {
		super(ActionListenerProperty.class);
		this.listener = listener;
	}




	@Override
	public boolean compareProperty(final Property other) {
		return this.listener.equals(((ActionListenerProperty) other).getListener());
	}




	public interface ActionListener {


		void onAction();

	}

}
