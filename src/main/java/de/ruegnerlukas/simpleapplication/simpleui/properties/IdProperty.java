package de.ruegnerlukas.simpleapplication.simpleui.properties;

import lombok.Getter;

public class IdProperty extends Property {


	@Getter
	private final String id;




	public IdProperty(String id) {
		super(IdProperty.class);
		this.id = id;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return id.equals(other.getAs(IdProperty.class).getId());
	}




	@Override
	public String printValue() {
		return id;
	}


}
