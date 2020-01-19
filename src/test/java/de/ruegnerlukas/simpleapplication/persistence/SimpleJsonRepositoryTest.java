package de.ruegnerlukas.simpleapplication.persistence;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.core.persistence.json.DummyObject;
import de.ruegnerlukas.simpleapplication.core.persistence.json.JsonRepository;
import de.ruegnerlukas.simpleapplication.core.persistence.json.JsonRepositoryImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleJsonRepositoryTest {


	private final String DIRECTORY = "test";




	@Before
	@After
	public void deleteDirectory() {
		final Resource directory = Resource.externalRelative(DIRECTORY);
		try {
			Files.deleteIfExists(Paths.get(directory.getPath()));
		} catch (IOException ignore) {
		}
	}




	@Test
	public void testSingle() {

		final Resource directory = Resource.externalRelative(DIRECTORY);

		final JsonRepository repository = new JsonRepositoryImpl(directory);
		final DummyObject object = DummyObject.random();

		// insert
		repository.insert(object);
		assertThat(repository.count()).isEqualTo(1);
		assertThat(repository.exists(DummyObject.class)).isTrue();
		assertThat(repository.getAllIds()).containsExactlyInAnyOrder("dummyObject");

		// get #1
		final DummyObject objectGet1 = repository.getAsObject(DummyObject.class).get();
		assertThat(objectGet1).isNotNull();
		assertThat(objectGet1.getName()).isEqualTo(object.getName());
		assertThat(objectGet1.getTimestamp()).isEqualTo(object.getTimestamp());
		assertThat(objectGet1.getSomeNumber()).isEqualTo(object.getSomeNumber());

		// update
		object.setName("Another name");
		repository.update(object);

		// get #2
		final DummyObject objectGet2 = repository.getAsObject(DummyObject.class).get();
		assertThat(objectGet2).isNotNull();
		assertThat(objectGet2.getName()).isEqualTo(object.getName());
		assertThat(objectGet2.getTimestamp()).isEqualTo(object.getTimestamp());
		assertThat(objectGet2.getSomeNumber()).isEqualTo(object.getSomeNumber());

		// delete
		repository.delete(DummyObject.class);
		assertThat(repository.count()).isEqualTo(0);
		assertThat(repository.exists(DummyObject.class)).isFalse();
		assertThat(repository.getAllIds()).isEmpty();
	}


}
