package de.ruegnerlukas.simpleapplication.core.extensions;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;
import de.ruegnerlukas.simpleapplication.common.validation.ValidateInputException;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

public class ExtensionPointsTest {


	@Test
	public void testRegisterExtensionPoints() {

		final String EP_ID = "test.ep";
		final ExtensionPointService service = new ExtensionPointServiceImpl();

		final ExtensionPoint extensionPoint = new ExtensionPoint(EP_ID);

		assertThat(service.find(EP_ID)).isNotPresent();
		assertThat(service.findOrDummy(EP_ID)).isNotNull();
		assertThat(service.findOrDummy(EP_ID).getId()).isEqualTo(DummyExtensionPoint.ID);

		service.register(extensionPoint);
		assertThat(service.find(EP_ID)).isPresent();
		assertThat(service.find(EP_ID).get()).isEqualTo(extensionPoint);
		assertThat(service.findOrDummy(EP_ID)).isEqualTo(extensionPoint);

		service.deregister(EP_ID);
		assertThat(service.find(EP_ID)).isNotPresent();
		assertThat(service.findOrDummy(EP_ID)).isNotNull();
		assertThat(service.findOrDummy(EP_ID)).isNotEqualTo(extensionPoint);
		assertThat(service.findOrDummy(EP_ID).getId()).isEqualTo(DummyExtensionPoint.ID);
	}




	@Test
	public void testProvideByService() {
		final ExtensionPointService service = new ExtensionPointServiceImpl();
		final EventListener<String> listener = (EventListener<String>) Mockito.mock(EventListener.class);
		final ExtensionPoint extensionPoint = new ExtensionPoint("test.ep");
		extensionPoint.addSupportedType(String.class, listener);
		service.register(extensionPoint);
		service.provide(extensionPoint.getId(), String.class, "Test String");
		verify(listener).onEvent(eq("Test String"));
	}




	@Test
	public void testDummyExtensionPoint() {

		final ExtensionPointService service = new ExtensionPointServiceImpl();
		final ExtensionPoint dummy = service.findOrDummy("some.id");

		assertThat(dummy.getId()).isEqualTo(DummyExtensionPoint.ID);
		assertThat(dummy.isSupported(String.class)).isTrue();
		assertThat(dummy.isSupported(Integer.class)).isTrue();
		assertThat(dummy.isSupported(Object.class)).isTrue();
		assertThat(dummy.isSupported(null)).isTrue();

		final EventListener<String> listener = (EventListener<String>) Mockito.mock(EventListener.class);
		dummy.addSupportedType(String.class, listener);
		dummy.provide(String.class, "Test Data");
		verify(listener, never()).onEvent(any());
	}




	@Test
	public void testExtensionPointSupportedType() {

		final ExtensionPoint extensionPoint = new ExtensionPoint("test.ep");
		final EventListener<String> listener = (EventListener<String>) Mockito.mock(EventListener.class);

		extensionPoint.addSupportedType(String.class, listener);
		assertThat(extensionPoint.isSupported(String.class)).isTrue();
		assertThat(extensionPoint.getSupportedTypes()).containsExactlyInAnyOrder(String.class);

		final String VALUE = "Test Data";
		extensionPoint.provide(String.class, VALUE);
		verify(listener).onEvent(eq(VALUE));
	}




	@Test
	public void testExtensionPointUnsupportedType() {

		final ExtensionPoint extensionPoint = new ExtensionPoint("test.ep");
		final EventListener<String> listener = (EventListener<String>) Mockito.mock(EventListener.class);

		extensionPoint.addSupportedType(String.class, listener);
		assertThat(extensionPoint.isSupported(Integer.class)).isFalse();

		final Integer VALUE = 42;
		extensionPoint.provide(Integer.class, VALUE);
		verify(listener, never()).onEvent(any());
	}




	@Test (expected = ValidateInputException.class)
	public void testExtensionPointInvalidType() {
		final ExtensionPoint extensionPoint = new ExtensionPoint("test.ep");
		extensionPoint.provide(String.class, 42);
	}




	@Test (expected = ValidateInputException.class)
	public void testExtensionPointNullType() {
		final ExtensionPoint extensionPoint = new ExtensionPoint("test.ep");
		extensionPoint.provide(null, "Test");
	}

}
