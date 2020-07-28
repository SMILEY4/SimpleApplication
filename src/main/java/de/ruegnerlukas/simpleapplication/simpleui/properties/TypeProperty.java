package de.ruegnerlukas.simpleapplication.simpleui.properties;


import lombok.Getter;

public class TypeProperty extends Property {


	/**
	 * Whether the element is disabled.
	 */
	@Getter
	private final Class<?> type;




	/**
	 * @param type the type of an object
	 */
	public TypeProperty(final Class<?> type) {
		super(TypeProperty.class);
		this.type = type;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return type == ((TypeProperty) other).getType();
	}




	@Override
	public String printValue() {
		return getType().getName();
	}




}



