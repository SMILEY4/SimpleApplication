package de.ruegnerlukas.simpleapplication.core.persistence.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Stores and manages object as json-files.
 * A unique id corresponds to one file (but can contain multiple objects referenced by sub-ids).
 */
@Slf4j
public class JsonRepositoryImpl implements JsonRepository {


	enum EntityType {
		/**
		 * one file = one entity
		 */
		SINGLE_OBJECT,

		/**
		 * one file = one {@link MultiObjectEntity} = multiple entities
		 */
		MULTI_OBJECT_ENTITY
	}






	/**
	 * The types of all known ids in this repository.
	 */
	private final Map<String, EntityType> entityTypes = new HashMap<>();

	/**
	 * The file engine for this repository.
	 */
	private final JsonRepositoryEngine engine;

	/**
	 * The object mapper for json.
	 */
	private final ObjectMapper mapper = new ObjectMapper();




	/**
	 * @param directory the root directory of the repository.
	 */
	public JsonRepositoryImpl(final Resource directory) {
		this(directory.getPath());
	}




	/**
	 * @param directory the root directory of the repository.
	 */
	public JsonRepositoryImpl(final String directory) {
		this.engine = new JsonRepositoryEngine(directory);
	}




	@Override
	public Optional<String> getAsJsonString(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return getAsJsonString(engine.convertToValidId(type));
	}




	@Override
	public Optional<String> getAsJsonString(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(engine.exists(validId), "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(getEntityType(validId), EntityType.SINGLE_OBJECT,
				"The requested file contains multiple entities and cant be accessed this way");
		String content = null;
		if (exists(validId)) {
			content = engine.getFileAsString(validId);
		}
		return Optional.ofNullable(content);
	}




	@Override
	public Optional<String> getAsJsonString(final Class<?> type, final String subId) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		return getAsJsonString(engine.convertToValidId(type), subId);
	}




	@Override
	public Optional<String> getAsJsonString(final String id, final String subId) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(exists(validId), "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(getEntityType(validId), EntityType.MULTI_OBJECT_ENTITY,
				"The requested file is not a multi-object-file and cant be accessed this way");
		String content = null;
		if (exists(validId, subId)) {
			final MultiObjectEntity moe = getMultiObjectEntity(validId);
			content = moe.getJsonEntities().get(subId);
		}
		return Optional.ofNullable(content);
	}




	@Override
	public <T> Optional<T> getAsObject(final Class<T> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return getAsObject(engine.convertToValidId(type), type);
	}




	@Override
	public <T> Optional<T> getAsObject(final String id, final Class<T> type) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(exists(validId), "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(getEntityType(validId), EntityType.SINGLE_OBJECT,
				"The requested file contains multiple entities and cant be accessed this way");
		T object = null;
		if (exists(validId)) {
			object = engine.getFileAsObject(validId, type);
		}
		return Optional.ofNullable(object);
	}




	@Override
	public <T> Optional<T> getAsObject(final Class<T> type, final String subId) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		return getAsObject(engine.convertToValidId(type), subId, type);
	}




	@Override
	public <T> Optional<T> getAsObject(final String id, final String subId, final Class<T> type) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		Validations.INPUT.isTrue(exists(validId), "The id {} ({}) is not known to this repository.", validId, id);
		Validations.INPUT.isEqual(getEntityType(validId), EntityType.MULTI_OBJECT_ENTITY,
				"The requested file is not a multi-object-file and cant be accessed this way");
		T object = null;
		if (exists(validId)) {
			final MultiObjectEntity moe = getMultiObjectEntity(validId);
			object = getSubObject(moe, subId, type);
		}
		return Optional.ofNullable(object);
	}




	@Override
	public List<String> getAllIds() {
		return engine.getIds();
	}




	@Override
	public List<String> getAllSubIds(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return getAllSubIds(engine.convertToValidId(type));
	}




	@Override
	public List<String> getAllSubIds(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final MultiObjectEntity moe = getMultiObjectEntity(engine.convertToValidId(id));
		if (moe != null) {
			return new ArrayList<>(moe.getJsonEntities().keySet());
		} else {
			return List.of();
		}
	}




	@Override
	public boolean exists(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		return engine.exists(engine.convertToValidId(type));
	}




	@Override
	public boolean exists(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		return engine.exists(engine.convertToValidId(id));
	}




	@Override
	public boolean exists(final Class<?> type, final String subId) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		if (!exists(type)) {
			return false;
		} else {
			final MultiObjectEntity moe = getMultiObjectEntity(engine.convertToValidId(type));
			return moe.getJsonEntities().containsKey(subId);
		}
	}




	@Override
	public boolean exists(final String id, final String subId) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		if (!exists(id)) {
			return false;
		} else {
			final MultiObjectEntity moe = getMultiObjectEntity(engine.convertToValidId(id));
			return moe.getJsonEntities().containsKey(subId);
		}
	}




	@Override
	public int count() {
		return getAllIds().size();
	}




	@Override
	public int count(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final MultiObjectEntity moe = getMultiObjectEntity(engine.convertToValidId(id));
		if (moe != null) {
			return moe.getJsonEntities().size();
		} else {
			return 0;
		}
	}




	@Override
	public void insert(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		insert(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void insert(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId)) {
			log.warn("The object with the id {} already exists and will not be inserted again.", id);
		} else {
			engine.writeToFile(validId, object);
			entityTypes.put(validId, EntityType.SINGLE_OBJECT);
		}
	}




	@Override
	public void insert(final Object object, final String subId) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		insert(engine.convertToValidId(object.getClass()), subId, object);
	}




	@Override
	public void insert(final String id, final String subId, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId, subId)) {
			log.warn("The object with the id {} and sub-id {} already exists and will not be inserted again.", id, subId);
		} else {
			final MultiObjectEntity moe = getMultiObjectEntity(validId);
			final String jsonEntity = toMultiObjectEntityString(object);
			if (jsonEntity != null) {
				moe.getJsonEntities().put(subId, jsonEntity);
				engine.writeToFile(validId, moe);
				entityTypes.put(validId, EntityType.MULTI_OBJECT_ENTITY);
			}
		}
	}




	@Override
	public void update(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		update(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void update(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be updated.", id);
		} else {
			Validations.INPUT.isEqual(getEntityType(validId), EntityType.SINGLE_OBJECT,
					"The requested file contains multiple entities and cant be updated this way");
			engine.writeToFile(validId, object);
		}
	}




	@Override
	public void update(final Object object, final String subId) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		update(engine.convertToValidId(object.getClass()), subId, object);
	}




	@Override
	public void update(final String id, final String subId, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId, subId)) {
			log.warn("The object with the id {} and sub-id {} does not exist and will not be updated.", id, subId);
		} else {
			Validations.INPUT.isEqual(getEntityType(validId), EntityType.MULTI_OBJECT_ENTITY,
					"The requested file is not a multi-object-file and cant be updated this way");
			final MultiObjectEntity moe = getMultiObjectEntity(validId);
			final String jsonEntity = toMultiObjectEntityString(object);
			if (moe != null && jsonEntity != null) {
				moe.getJsonEntities().put(subId, jsonEntity);
				engine.writeToFile(validId, moe);
			}
		}
	}




	@Override
	public void upsert(final Object object) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		upsert(engine.convertToValidId(object.getClass()), object);
	}




	@Override
	public void upsert(final String id, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId)) {
			update(validId, object);
		} else {
			insert(validId, object);
		}
	}




	@Override
	public void upsert(final Object object, final String subId) {
		Validations.INPUT.notNull(object, "The object cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		upsert(engine.convertToValidId(object.getClass()), subId, object);
	}




	@Override
	public void upsert(final String id, final String subId, final Object object) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (exists(validId, subId)) {
			update(validId, subId, object);
		} else {
			insert(validId, subId, object);
		}
	}




	@Override
	public void delete(final Class<?> type) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		delete(engine.convertToValidId(type));
	}




	@Override
	public void delete(final String id) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId)) {
			log.warn("The object with the id {} does not exist and will not be deleted.", id);
		} else {
			engine.deleteFile(validId);
			entityTypes.remove(validId);
		}
	}




	@Override
	public void delete(final Class<?> type, final String subId) {
		Validations.INPUT.notNull(type, "The type cannot be null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		delete(engine.convertToValidId(type), subId);
	}




	@Override
	public void delete(final String id, final String subId) {
		Validations.INPUT.notBlank(id, "The id cannot be empty or null.");
		Validations.INPUT.notBlank(subId, "The sub-id cannot be empty or null.");
		final String validId = engine.convertToValidId(id);
		if (!exists(validId, subId)) {
			log.warn("The object with the id {} and sub-id {} does not exist and will not be deleted.", id, subId);
		} else {
			final MultiObjectEntity moe = getMultiObjectEntity(validId);
			if (moe != null) {
				final boolean removed = moe.getJsonEntities().remove(subId) != null;
				if (removed) {
					if (moe.getJsonEntities().isEmpty()) {
						engine.deleteFile(validId);
						entityTypes.remove(validId);
					} else {
						engine.writeToFile(validId, moe);
					}
				}
			}
		}
	}




	/**
	 * Finds and parses the object from the given {@link MultiObjectEntity} with the given sub-id
	 *
	 * @param entity the {@link MultiObjectEntity}
	 * @param subId  the sub-id of the entity
	 * @param type   the type of the entity
	 * @param <T>    Generic type
	 * @return the parsed object or null
	 */
	private <T> T getSubObject(final MultiObjectEntity entity, final String subId, final Class<T> type) {
		try {
			return mapper.readValue(entity.getJsonEntities().get(subId), type);
		} catch (JsonProcessingException e) {
			log.error("Error parsing sub-entity with sub-id " + subId + ".", e);
			return null;
		}
	}




	/**
	 * @param id the valid id
	 * @return the type of the file/entity with the given id
	 */
	private EntityType getType(final String id) {
		if (exists(id)) {
			try {
				mapper.readValue(engine.getFileAsString(id), MultiObjectEntity.class);
				return EntityType.MULTI_OBJECT_ENTITY;
			} catch (JsonProcessingException ignore) {
				return EntityType.SINGLE_OBJECT;
			}
		} else {
			return null;
		}
	}




	/**
	 * @param id the id of the file
	 * @return the {@link MultiObjectEntity}
	 */
	private MultiObjectEntity getMultiObjectEntity(final String id) {
		MultiObjectEntity multiObjectEntity = new MultiObjectEntity();
		if (engine.exists(id)) {
			multiObjectEntity = engine.getFileAsObject(id, MultiObjectEntity.class);
		}
		return multiObjectEntity;
	}




	/**
	 * Converts the given object to a json-string
	 *
	 * @param object the object to convert
	 * @return the json-string
	 */
	private String toMultiObjectEntityString(final Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			log.error("Could not convert object to json.", e);
			return null;
		}
	}




	/**
	 * @param id the id of the file/entity
	 * @return the type of the file/entity
	 */
	private EntityType getEntityType(final String id) {
		return entityTypes.get(id);
	}


}
