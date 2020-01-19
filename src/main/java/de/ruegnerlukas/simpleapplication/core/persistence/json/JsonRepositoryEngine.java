package de.ruegnerlukas.simpleapplication.core.persistence.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
public class JsonRepositoryEngine {


	/**
	 * The root directory of the repository.
	 */
	@Getter
	private final String directory;


	/**
	 * The object mapper for json.
	 */
	private final ObjectMapper mapper = new ObjectMapper();




	/**
	 * @param directory the root directory of the repository.
	 */
	public JsonRepositoryEngine(final String directory) {
		this.directory = directory;
	}




	/**
	 * @param id the id of the file/entity
	 * @return whether the file with the given id exists
	 */
	public boolean exists(final String id) {
		final String path = convertToFilePath(id);
		return Files.exists(Paths.get(path));
	}




	/**
	 * @param id the valid id of the file/entity.
	 * @return the content of the file with the given id as a (json-) string
	 */
	public String getFileAsString(final String id) {
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
	public <T> T getFileAsObject(final String id, final Class<T> type) {
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
	public void writeToFile(final String id, final Object object) {
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
	public void deleteFile(final String id) {
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
	public String convertToFilePath(final String id) {
		return directory + "/" + id + ".json";
	}




	/**
	 * Converts the given type to an id.
	 *
	 * @param type the type
	 * @return a valid id
	 */
	public String convertToValidId(final Class<?> type) {
		String name = type.getSimpleName();
		return Character.toLowerCase(name.charAt(0)) + name.substring(1);
	}




	/**
	 * Converts the given string to a valid id
	 *
	 * @param str the string
	 * @return a valid id
	 */
	public String convertToValidId(final String str) {
		return str.strip().replace(' ', '_');
	}

}
