package de.ruegnerlukas.simpleapplication.core.persistence.json;

import java.util.List;
import java.util.Optional;

public interface JsonRepository {


	/**
	 * Return the json entity/file with the given type.
	 *
	 * @param type the type of the entity/file
	 * @return an {@link Optional} with the string or null
	 */
	Optional<String> getAsJsonString(Class<?> type);

	/**
	 * Return the json entity/file with the given id as a string.
	 *
	 * @param id the id of the entity/file
	 * @return an {@link Optional} with the string or null
	 */
	Optional<String> getAsJsonString(String id);

	/**
	 * Return the json entity/file with the given type as an object of the given type.
	 *
	 * @param type the type of the entity/file
	 * @return an {@link Optional} with the object or null
	 */
	<T> Optional<T> getAsObject(Class<T> type);


	/**
	 * Return the json entity/file with the given id as an object of the given type.
	 *
	 * @param id   the id of the entity/file
	 * @param type the type of the entity/file
	 * @return an {@link Optional} with the object or null
	 */
	<T> Optional<T> getAsObject(String id, Class<T> type);


	/**
	 * @return a list of all stored ids
	 */
	List<String> getAllIds();


	/**
	 * Checks if the object of the given type already exists. The id will be the name of the class.
	 *
	 * @param type the type of object to check
	 * @return whether the object already exists.
	 */
	boolean exists(Class<?> type);

	/**
	 * Checks if an object with the given id already exists.
	 *
	 * @param id the id to check
	 * @return whether an object with the id already exists.
	 */
	boolean exists(String id);


	/**
	 * @return the count of all objects.
	 */
	int count();


	/**
	 * Saves the given object. The id will be the name of the class.
	 * If an object with the same id already exists, it will not be overridden.
	 *
	 * @param object the object to save
	 */
	void insert(Object object);


	/**
	 * Saves the given object with the given id.
	 * If an object with the same id already exists, it will not be overwritten.
	 *
	 * @param id     the id of the object
	 * @param object the object to save
	 */
	void insert(String id, Object object);


	/**
	 * Updates the given object. The id will be the name of the class.
	 * If the object does not yet exist, it will not be created.
	 *
	 * @param object the object to update
	 */
	void update(Object object);


	/**
	 * Updates the given object with the given id. If the object does not yet exist, it will not be created.
	 *
	 * @param id     the id of the object
	 * @param object the object to update
	 */
	void update(String id, Object object);


	/**
	 * Updates the given object. The id will be the name of the class.
	 * If the object does not yet exist, it will be inserted.
	 *
	 * @param object the object to update or insert
	 */
	void upsert(Object object);


	/**
	 * Updates the given object with the given id. If the object does not yet exist, it will be inserted.
	 *
	 * @param id     the id of the object
	 * @param object the object to update or insert
	 */
	void upsert(String id, Object object);


	/**
	 * Deletes the object of the given type. The id will be the name of the class.
	 *
	 * @param type the type of the object to delete
	 */
	void delete(Class<?> type);


	/**
	 * Deletes the given object with the given id.
	 *
	 * @param id the id of the object to delete
	 */
	void delete(String id);

}
