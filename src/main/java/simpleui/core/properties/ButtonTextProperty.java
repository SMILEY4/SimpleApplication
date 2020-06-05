package simpleui.core.properties;

import lombok.Getter;

public class ButtonTextProperty extends Property {


	@Getter
	private final String text;




	public ButtonTextProperty(final String text) {
		super(ButtonTextProperty.class);
		this.text = text;
	}




	@Override
	public boolean compareProperty(final Property other) {
		return getText().equals(((ButtonTextProperty) other).getText());
	}

}
