package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

public class IdProperty extends SuiProperty {


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
	protected boolean isPropertyEqual(final SuiProperty other) {
		return id.equals(((IdProperty) other).getId());
	}


}
