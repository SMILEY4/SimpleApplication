package de.ruegnerlukas.simpleapplication.core.persistence.json;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

/**
 * Stores and manages object as json-files.
 * A unique id corresponds to one file (and one object).
 */
@Slf4j
public class SimpleJsonRepositoryImpl implements SimpleJsonRepository {


//	/**
//	 * All known ids in this repository.
//	 */
//	private final Set<String> storedIds = new HashSet<>();


	/**
	 * The file engine for this repository.
	 */
	private final JsonRepositoryEngine engine;




	/**
	 * @param directory the root directory of the repository.
	 */
	public SimpleJsonRepositoryImpl(final Resource directory) {
		this(directory.getPath());
	}




	/**
	 * @param directory the root directory of the repository.
	 */
	public SimpleJsonRepositoryImpl(final String directory) {
		this.engine = new JsonRepositoryEngine(directory);
	}




	@Override
	public Optional<String> getAsJsonString(final Class<?> type) {
		Validations.INPUT.notNull(type).exception("The type cannot be null.");
		return getAsJsonString(engine.convertToValidId(type));
	}




	@Override
	public Optional<String> getAsJsonString(final String id) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(exists(validId)).exception("The id {} ({}) is not known to this repository.", validId, id);
		String content = null;
		if (exists(validId)) {
			content = engine.getFileAsString(validId);
		}
		return Optional.ofNullable(content);
	}




	@Override
	public <T> Optional<T> getAsObject(final Class<T> type) {
		Validations.INPUT.notNull(type).exception("The type cannot be null.");
		return getAsObject(engine.convertToValidId(type), type);
	}




	@Override
	public <T> Optional<T> getAsObject(final String id, final Class<T> type) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(exists(validId)).exception("The id {} ({}) is not known to this repository.", validId, id);
		T object = null;
		if (exists(validId)) {
			object = engine.getFileAsObject(validId, type);
		}
		return Optional.ofNullable(object);
	}




	@Override
	public List<String> getAllIds() {
		return engine.getIds();
	}




	@Override
	public boolean exists(final Class<?> type) {
		Validations.INPUT.notNull(type).exception("The type cannot be null.");
		return engine.exists(engine.convertToValidId(type));
	}




	@Override
	public boolean exists(final String id) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		return engine.exists(engine.convertToValidId(id));
	}




	@Override
	public int count() {
		return getAllIds().size();
	}




	@Override
	public void insert(final Object object) {
		Validations.INPUT.notNull(object).exception("The object cannot be null.");
		insert(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void insert(final String id, final Object object) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId)) {
			log.warn("The object with the id {} already exists and will not be inserted again.", id);
		} else {
			engine.writeToFile(validId, object);
		}
	}




	@Override
	public void update(final Object object) {
		Validations.INPUT.notNull(object).exception("The object cannot be null.");
		update(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void update(final String id, final Object object) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be updated.", id);
		} else {
			engine.writeToFile(validId, object);
		}
	}




	@Override
	public void upsert(final Object object) {
		Validations.INPUT.notNull(object).exception("The object cannot be null.");
		upsert(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void upsert(final String id, final Object object) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId)) {
			update(validId, object);
		} else {
			insert(validId, object);
		}
	}




	@Override
	public void delete(final Class<?> type) {
		Validations.INPUT.notNull(type).exception("The type cannot be null.");
		delete(engine.convertToValidId(type));
	}




	@Override
	public void delete(final String id) {
		Validations.INPUT.notBlank(id).exception("The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be deleted.", id);
		} else {
			engine.deleteFile(validId);
		}
	}


}
