package de.ruegnerlukas.simpleapplication.core.persistence.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Stores and manages object as json-files.
 * A unique id corresponds to one file (and one object).
 */
@Slf4j
public class AdvJsonRepositoryImpl implements JsonRepository {


	enum EntityType {
		/**
		 * one file = one entity
		 */
		SINGLE_ENTITY,

		/**
		 * one file = one {@link MultiObjectEntity} = multiple entities
		 */
		MULTI_OBJECT_ENTITY
	}






	/**
	 * The root directory of this repository.
	 */
	private final String directory;

	/**
	 * All known ids in this repository.
	 */
	private final Set<String> storedIds = new HashSet<>();

	/**
	 * The types of all known ids in this repository.
	 */
	private final Map<String, EntityType> entityTypes = new HashMap<>();

	/**
	 * The object mapper for json.
	 */
	private final ObjectMapper mapper = new ObjectMapper();




	/**
	 * @param directory the root directory of the repository.
	 */
	public AdvJsonRepositoryImpl(final Resource directory) {
		this(directory.getPath());
	}




	/**
	 * @param directory the root directory of the repository.
	 */
	public AdvJsonRepositoryImpl(final String directory) {
		this.directory = directory;
	}




	@Override
	public Optional<String> getAsJsonString(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return getAsJsonString(convertToValidId(type));
	}




	@Override
	public Optional<String> getAsJsonString(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		Validations.INPUT.contains(storedIds, validId, "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(entityTypes.get(validId), EntityType.MULTI_OBJECT_ENTITY,
				"The requested file contains multiple entities and cant be accessed this way");
		String content = null;
		if (exists(validId)) {
			content = getFileAsString(validId);
		}
		return Optional.ofNullable(content);
	}




	@Override
	public <T> Optional<T> getAsObject(final Class<T> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return getAsObject(convertToValidId(type), type);
	}




	@Override
	public <T> Optional<T> getAsObject(final String id, final Class<T> type) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		Validations.INPUT.contains(storedIds, validId, "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(entityTypes.get(validId), EntityType.MULTI_OBJECT_ENTITY,
				"The requested file contains multiple entities and cant be accessed this way");
		T object = null;
		if (exists(validId)) {
			object = getFileAsObject(validId, type);
		}
		return Optional.ofNullable(object);
	}




	@Override
	public List<String> getAllIds() {
		return new ArrayList<>(storedIds);
	}




	@Override
	public boolean exists(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return storedIds.contains(convertToValidId(type));
	}




	@Override
	public boolean exists(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		return storedIds.contains(convertToValidId(id));

	}




	@Override
	public int count() {
		return storedIds.size();
	}




	@Override
	public void insert(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		insert(convertToValidId(object.getClass()), object);
	}




	@Override
	public void insert(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		if (exists(validId)) {
			log.warn("The object with the id {} already exists and will not be inserted again.", id);
		} else {
			writeToFile(validId, object);
			storedIds.add(validId);
		}
	}




	@Override
	public void update(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		update(convertToValidId(object.getClass()), object);
	}




	@Override
	public void update(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be updated.", id);
		} else {
			Validations.INPUT.isEqual(entityTypes.get(validId), EntityType.MULTI_OBJECT_ENTITY,
					"The requested file contains multiple entities and cant be updated this way");
			writeToFile(validId, object);
		}
	}




	@Override
	public void upsert(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		upsert(convertToValidId(object.getClass()), object);
	}




	@Override
	public void upsert(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		if (exists(validId)) {
			update(validId, object);
		} else {
			insert(validId, object);
		}
	}




	@Override
	public void delete(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		delete(convertToValidId(type));
	}




	@Override
	public void delete(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be deleted.", id);
		} else {
			deleteFile(validId);
			storedIds.remove(validId);
		}
	}




	/**
	 * @param id the valid id of the file/entity.
	 * @return the content of the file with the given id as a (json-) string
	 */
	private String getFileAsString(final String id) {
		final String path = convertToFilePath(id);
		String content;
		try {
			content = Files.readString(Paths.get(path));
		} catch (IOException e) {
			log.error("Error reading file with id " + id + " at " + path + ".", e);
			return null;
		}
		try {
			mapper.readTree(content);
		} catch (JsonProcessingException e) {
			log.error("Object with id " + id + " is not a valid json string.", e);
			return null;
		}
		return content;
	}




	/**
	 * @param id   the valid id of the file/entity.
	 * @param type the type of the object
	 * @return the content of the file with the given id converted to an object of the given type.
	 */
	private <T> T getFileAsObject(final String id, final Class<T> type) {
		final String content = getFileAsString(id);
		if (content == null) {
			return null;
		}
		T object = null;
		try {
			object = mapper.readValue(content, type);
		} catch (JsonProcessingException e) {
			log.error("Cannot parse json in file with id " + id, e);
		}
		return object;
	}




	/**
	 * Writes the given object with the given id to a file. Existing files will be overwritten and new files created.
	 *
	 * @param id     the valid id of the object
	 * @param object the object to write to file
	 */
	private void writeToFile(final String id, final Object object) {
		final String path = convertToFilePath(id);
		final File file = new File(path);
		if (!file.exists()) {
			file.getParentFile().mkdirs();
		}
		try {
			mapper.writerWithDefaultPrettyPrinter().writeValue(file, object);
		} catch (IOException e) {
			log.error("Could not write object with id " + id + " to file at " + path + ".", e);
		}
	}




	/**
	 * Deletes the file with the given id.
	 *
	 * @param id the valid id of the file/object
	 */
	private void deleteFile(final String id) {
		final String path = convertToFilePath(id);
		try {
			Files.deleteIfExists(Paths.get(path));
		} catch (IOException e) {
			log.error("Could not delete file with id " + id + " at " + path + ".", e);
		}
	}




	/**
	 * Converts the given valid id to a file path
	 *
	 * @param id the valid id of the file/object
	 * @return the path of the file
	 */
	private String convertToFilePath(final String id) {
		return directory + "/" + id + ".json";
	}




	/**
	 * Converts the given type to an id.
	 *
	 * @param type the type
	 * @return a valid id
	 */
	private String convertToValidId(final Class<?> type) {
		String name = type.getSimpleName();
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}




	/**
	 * Converts the given string to a valid id
	 *
	 * @param str the string
	 * @return a valid id
	 */
	private String convertToValidId(final String str) {
		return str.strip().replace(' ', '_');
	}


}
