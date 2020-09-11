package de.ruegnerlukas.simpleapplication.common.instanceproviders;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.ArrayFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.ListFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.MapFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.SetFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ArrayProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ListProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.MapProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.SetProvider;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.common.validation.ValidateStateException;
import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InstanceProviderTest {


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
		ProviderService.cleanup();

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
	public void test_non_singleton_expect_different_provider_have_different_instance() {
		final User userA = new Provider<>(User.class).get();

		final Provider<User> providerB = new Provider<>(User.class);
		final User userB1 = providerB.get();
		final User userB2 = providerB.get();

		assertThat(userA).isNotNull();
		assertThat(userA.getName()).isEqualTo(USER_NAME);
		assertThat(userA.getId()).isEqualTo(USER_ID);

		assertThat(userB1).isNotNull();
		assertThat(userB1.getName()).isEqualTo(USER_NAME);
		assertThat(userB1.getId()).isEqualTo(USER_ID);

		assertThat(userB2).isNotNull();
		assertThat(userB2.getName()).isEqualTo(USER_NAME);
		assertThat(userB2.getId()).isEqualTo(USER_ID);

		assertThat(userA).isNotEqualTo(userB1);
		assertThat(userA).isNotEqualTo(userB2);
		assertThat(userB1).isEqualTo(userB2);
	}




	@Test
	public void test_singleton_expect_different_provider_have_same_instance() {
		final Administrator adminA = new Provider<>(Administrator.class).get();
		final Administrator adminB = new Provider<>(Administrator.class).get();
		assertThat(adminA).isNotNull();
		assertThat(adminA.getName()).isEqualTo(ADMIN_NAME);
		assertThat(adminA.getId()).isEqualTo(ADMIN_ID);
		assertThat(adminA.getContact()).isEqualTo(ADMIN_CONTACT);
		assertThat(adminB).isNotNull();
		assertThat(adminA).isEqualTo(adminB);
	}




	@Test
	public void test_register_factory_replaces_factory_with_same_identifier() {
		final String TEST_FACTORY = "TestFactory";
		ProviderService.registerFactory(new StringFactory(TEST_FACTORY, "a"));
		assertThat(new Provider<String>(TEST_FACTORY).get()).isEqualTo("a");

		ProviderService.registerFactory(new StringFactory(TEST_FACTORY, "b"));
		assertThat(new Provider<String>(TEST_FACTORY).get()).isEqualTo("b");
	}




	@Test (expected = ValidateStateException.class)
	public void test_request_non_existent_expect_null_and_failed_validation() {
		final Object object = new Provider<>(Object.class).get();
		assertThat(object).isNull();
	}




	@Test
	public void test_request_instance_by_string_id() {
		final User user = new Provider<User>("MyUser").get();
		assertThat(user).isNotNull();
		assertThat(user.getName()).isEqualTo(MY_USER_NAME);
		assertThat(user.getId()).isEqualTo(MY_USER_ID);
	}




	@Test
	public void test_array_provider() {
		final User[] array = new ArrayProvider<User>("User Array").get();
		assertThat(array).isNotNull();
	}




	@Test
	public void test_list_provider() {
		final List<User> list = new ListProvider<User>("User List").get();
		assertThat(list).isNotNull();
	}




	@Test
	public void test_set_provider() {
		final Set<User> set = new SetProvider<User>("User Set").get();
		assertThat(set).isNotNull();
	}




	@Test
	public void test_map_provider() {
		final Map<String, User> map = new MapProvider<String, User>("User Map").get();
		assertThat(map).isNotNull();
	}




	@Test
	public void test_string_provider() {
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
