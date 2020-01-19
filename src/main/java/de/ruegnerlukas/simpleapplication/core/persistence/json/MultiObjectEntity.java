package de.ruegnerlukas.simpleapplication.core.persistence.json;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class MultiObjectEntity {


	/**
	 * The map of all entities in json format and their key/id
	 */
	@Getter
	private Map<String, String> jsonEntities = new HashMap<>();

}