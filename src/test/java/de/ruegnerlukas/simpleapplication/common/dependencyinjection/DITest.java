package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.ArrayFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.ListFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.MapFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.SetFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.ArrayProvider;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.ListProvider;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.MapProvider;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.SetProvider;
import de.ruegnerlukas.simpleapplication.common.dependencyinjection.providers.StringProvider;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class DITest {


	private static final String USER_NAME = "Test User";
	private static final int USER_ID = 1234;

	private static final String MY_USER_OBJECT_NAME = "MyUser";
	private static final String MY_USER_NAME = "My User";
	private static final int MY_USER_ID = 4321;

	private static final String ADMIN_NAME = "Test Admin";
	private static final int ADMIN_ID = 5678;
	private static final String ADMIN_CONTACT = "admin@test.mail";




	@Before
	public void setup() {

		ProviderService.registerFactory(new InstanceFactory<>(User.class, ObjectType.NON_SINGLETON) {
			@Override
			public User buildObject() {
				return new UserImpl(USER_NAME, USER_ID);
			}
		});

		ProviderService.registerFactory(new InstanceFactory<>(Administrator.class) {
			@Override
			public Administrator buildObject() {
				return new Administrator(ADMIN_NAME, ADMIN_ID, ADMIN_CONTACT);
			}
		});

		ProviderService.registerFactory(new InstanceFactory<>(MY_USER_OBJECT_NAME) {
			@Override
			public User buildObject() {
				return new UserImpl(MY_USER_NAME, MY_USER_ID);
			}
		});

		ProviderService.registerFactory(new ArrayFactory<>("User Array") {
			@Override
			public UserImpl[] buildObject() {
				return new UserImpl[0];
			}
		});

		ProviderService.registerFactory(new ListFactory<User>("User List") {
			@Override
			public List<User> buildObject() {
				return List.of(new UserImpl(MY_USER_NAME, MY_USER_ID));
			}
		});

		ProviderService.registerFactory(new SetFactory<User>("User Set") {
			@Override
			public Set<User> buildObject() {
				return Set.of(new UserImpl(MY_USER_NAME, MY_USER_ID));
			}
		});

		ProviderService.registerFactory(new MapFactory<String, User>("User Map") {
			@Override
			public Map<String, User> buildObject() {
				return Map.of("Test", new UserImpl(MY_USER_NAME, MY_USER_ID));
			}
		});

		ProviderService.registerFactory(new StringFactory("string", "The Test String"));

	}




	@Test
	public void testNonSingleton() {
		final User userA = new Provider<>(User.class).get();
		final User userB = new Provider<>(User.class).get();

		assertThat(userA).isNotNull();
		assertThat(userA.getName()).isEqualTo(USER_NAME);
		assertThat(userA.getId()).isEqualTo(USER_ID);

		assertThat(userB).isNotNull();
		assertThat(userB.getName()).isEqualTo(USER_NAME);
		assertThat(userB.getId()).isEqualTo(USER_ID);

		assertThat(userA).isNotEqualTo(userB);
	}




	@Test
	public void testSingleton() {
		final Administrator adminA = new Provider<>(Administrator.class).get();
		final Administrator adminB = new Provider<>(Administrator.class).get();

		assertThat(adminA).isNotNull();
		assertThat(adminA.getName()).isEqualTo(ADMIN_NAME);
		assertThat(adminA.getId()).isEqualTo(ADMIN_ID);
		assertThat(adminA.getContact()).isEqualTo(ADMIN_CONTACT);

		assertThat(adminB).isNotNull();

		assertThat(adminA).isEqualTo(adminB);
	}




	@Test (expected = IllegalStateException.class)
	public void testNotExistent() {
		final Object object = new Provider<>(Object.class).get();
		assertThat(object).isNull();
	}




	@Test
	public void testByName() {
		final User user = new Provider<User>("MyUser").get();
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(MY_USER_NAME);
		assertThat(user.getId()).isEqualTo(MY_USER_ID);
	}




	@Test
	public void testArrayProvider() {
		final User[] array = new ArrayProvider<User>("User Array").get();
		assertThat(array).isNotNull();
	}




	@Test
	public void testListProvider() {
		final List<User> list = new ListProvider<User>("User List").get();
		assertThat(list).isNotNull();
	}




	@Test
	public void testSetProvider() {
		final Set<User> set = new SetProvider<User>("User Set").get();
		assertThat(set).isNotNull();
	}




	@Test
	public void testMapProvider() {
		final Map<String, User> map = new MapProvider<String, User>("User Map").get();
		assertThat(map).isNotNull();
	}



	@Test
	public void testStringProvider() {
		final String str = new StringProvider("string").get();
		assertThat(str).isEqualTo("The Test String");
	}



	@AllArgsConstructor
	static class Administrator implements User {


		private String name;
		private int id;
		private String contact;




		@Override
		public String getName() {
			return name;
		}




		@Override
		public int getId() {
			return id;
		}




		public String getContact() {
			return contact;
		}

	}






	@AllArgsConstructor
	static class UserImpl implements User {


		private String name;
		private int id;




		@Override
		public String getName() {
			return name;
		}




		@Override
		public int getId() {
			return id;
		}

	}






	interface User {


		String getName();

		int getId();

	}


}
