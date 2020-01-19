package de.ruegnerlukas.simpleapplication.core.persistence.json;

import java.util.List;
import java.util.Optional;

public interface JsonRepository extends SimpleJsonRepository {


	/**
	 * Return the json entity/file with the given type and sub-id.
	 *
	 * @param type  the type of the entities
	 * @param subId the id of the entity
	 * @return an {@link Optional} with the string or null
	 */
	Optional<String> getAsJsonString(Class<?> type, String subId);

	/**
	 * Return the json entity/file with the given id and sub-id.
	 *
	 * @param id    the id of the file
	 * @param subId the id of the entity
	 * @return an {@link Optional} with the string or null
	 */
	Optional<String> getAsJsonString(String id, String subId);

	/**
	 * Return the json entity/file with the given type as an object of the given type and sub-id.
	 *
	 * @param type  the type of the entity/file
	 * @param subId the id of the entity
	 * @return an {@link Optional} with the object or null
	 */
	<T> Optional<T> getAsObject(Class<T> type, String subId);


	/**
	 * Return the json entity/file with the given id and sub-id as an object of the given type.
	 *
	 * @param id    the id of the file
	 * @param subId the id of the entity
	 * @param type  the type of the entity/file
	 * @return an {@link Optional} with the object or null
	 */
	<T> Optional<T> getAsObject(String id, String subId, Class<T> type);


	/**
	 * @return a list of all sub-ids for the given type
	 */
	List<String> getAllSubIds(Class<?> type);


	/**
	 * @return a list of all sub-ids for the given id
	 */
	List<String> getAllSubIds(String id);

	/**
	 * Checks if the object of the given type and with the given sub-id, already exists. The id will be the name of the class.
	 *
	 * @param type  the type of object to check
	 * @param subId the id of the entity
	 * @return whether the object already exists.
	 */
	boolean exists(Class<?> type, String subId);

	/**
	 * Checks if an object with the given id and sub-id already exists.
	 *
	 * @param id    the id of the file
	 * @param subId the id of the entity to check
	 * @return whether an object with the id already exists.
	 */
	boolean exists(String id, String subId);


	/**
	 * @return the count of all objects in the file with the given id.
	 */
	int count(String id);


	/**
	 * Saves the given object. The id will be the name of the class.
	 * If an object with the same id and sub-id already exists, it will not be overridden.
	 *
	 * @param object the object to save
	 * @param subId  the id of the entity
	 */
	void insert(Object object, String subId);


	/**
	 * Saves the given object with the given id and sub-id.
	 * If an object with the same id and sub-id already exists, it will not be overwritten.
	 *
	 * @param id     the id of the file
	 * @param subId  the id of the entity
	 * @param object the object to save
	 */
	void insert(String id, String subId, Object object);


	/**
	 * Updates the given object. The id will be the name of the class.
	 * If the object does not yet exist, it will not be created.
	 *
	 * @param object the object to update
	 * @param subId  the id of the entity
	 */
	void update(Object object, String subId);


	/**
	 * Updates the given object with the given id and sub-id. If the object does not yet exist, it will not be created.
	 *
	 * @param id     the id of the object
	 * @param object the object to update
	 * @param subId  the id of the entity
	 */
	void update(String id, String subId, Object object);


	/**
	 * Updates the given object. The id will be the name of the class.
	 * If the object does not yet exist, it will be inserted.
	 *
	 * @param object the object to update or insert
	 * @param subId  the id of the entity
	 */
	void upsert(Object object, String subId);


	/**
	 * Updates the given object with the given id and sub-id. If the object does not yet exist, it will be inserted.
	 *
	 * @param id     the id of the object
	 * @param subId  the id of the entity
	 * @param object the object to update or insert
	 */
	void upsert(String id, String subId, Object object);


	/**
	 * Deletes the object of the given type. The id will be the name of the class.
	 *
	 * @param type  the type of the object to delete
	 * @param subId the id of the entity
	 */
	void delete(Class<?> type, String subId);


	/**
	 * Deletes the given object with the given id and sub-id.
	 *
	 * @param id    the id of the object to delete
	 * @param subId the id of the entity
	 */
	void delete(String id, String subId);

}
