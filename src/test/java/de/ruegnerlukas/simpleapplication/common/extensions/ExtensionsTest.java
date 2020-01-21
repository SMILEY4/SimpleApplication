package de.ruegnerlukas.simpleapplication.common.extensions;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class ExtensionsTest {


	@Test
	public void test() {

		final String EXT_ID = "test_extension";
		final ExtensionHandler handler = new ExtensionHandler();

		assertThat(handler.getRegisteredExtensionPoints()).isEmpty();
		assertThat(handler.getExtensionPoint(EXT_ID)).isNull();

		handler.register(extension(EXT_ID));
		assertThat(handler.getRegisteredExtensionPoints()).containsExactly(EXT_ID);
		assertThat(handler.getExtensionPoint(EXT_ID).getId()).isEqualTo(EXT_ID);
	}




	/**
	 * @param id the id of the {@link ExtensionPoint}
	 * @return the created {@link ExtensionPoint}
	 */
	private ExtensionPoint extension(final String id) {
		return new ExtensionPoint(id) {
			@Override
			public ExtensionPointResult put(final ExtensionPointData data) {
				return new ExtensionPointResult();
			}
		};
	}


}
