package de.ruegnerlukas.simpleapplication.simpleui.properties;

import lombok.Getter;

public class IdProperty extends Property {


	/**
	 * The id of the element. Unique among the siblings.
	 */
	@Getter
	private final String id;




	/**
	 * @param id the id of the element. Unique among the siblings.
	 */
	public IdProperty(final String id) {
		super(IdProperty.class);
		this.id = id;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return id.equals(((IdProperty) other).getId());
	}




	@Override
	public String printValue() {
		return id;
	}


}
