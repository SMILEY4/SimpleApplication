package de.ruegnerlukas.simpleapplication.common.dependencyinjection;

import lombok.AllArgsConstructor;
import org.junit.Before;
import org.junit.Test;

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

		ProviderService.registerFactory(new InstanceFactory<>(ObjectType.NON_SINGLETON, User.class) {
			@Override
			public User buildObject() {
				return new UserImpl(USER_NAME, USER_ID);
			}
		});

		ProviderService.registerFactory(new InstanceFactory<>(ObjectType.SINGLETON, Administrator.class) {
			@Override
			public Administrator buildObject() {
				return new Administrator(ADMIN_NAME, ADMIN_ID, ADMIN_CONTACT);
			}
		});

		ProviderService.registerFactory(new InstanceFactory<>(ObjectType.SINGLETON, MY_USER_OBJECT_NAME) {
			@Override
			public User buildObject() {
				return new UserImpl(MY_USER_NAME, MY_USER_ID);
			}
		});

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
