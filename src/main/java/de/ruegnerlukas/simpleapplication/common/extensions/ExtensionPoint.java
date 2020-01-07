package de.ruegnerlukas.simpleapplication.common.extensions;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public abstract class ExtensionPoint {


	/**
	 * The unique id of this extension point
	 */
	private final String id;




	/**
	 * Hands the given data to this extension point
	 *
	 * @param data the data
	 * @return a result
	 */
	public abstract ExtensionPointResult put(ExtensionPointData data);


}
