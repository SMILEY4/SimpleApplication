package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.common.events.EventPackage;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApplicationTest {


	@Test
	public void testSimple() {

		final TestEventService eventService = new TestEventService();
		final TestJfxStarter starter = new TestJfxStarter();

		final Application application = new Application(new ApplicationConfiguration());
		application.setJFXApplicationStarter(starter);
		application.setCoreProviderConfiguration(new TestCoreProviderConfig(eventService));

		application.run();
		final List<EventPackage<?>> eventsStart = eventService.getEventPackages();
		assertThat(eventsStart.size()).isEqualTo(2);
		assertThat(eventsStart.get(0).getChannels()).containsExactlyInAnyOrder(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED);
		assertThat(eventsStart.get(0).getEvent() instanceof EmptyEvent).isTrue();
		assertThat(eventsStart.get(1).getChannels()).containsExactlyInAnyOrder(ApplicationConstants.EVENT_APPLICATION_STARTED);
		assertThat(eventsStart.get(1).getEvent() instanceof EmptyEvent).isTrue();

		starter.stop();
		final List<EventPackage<?>> eventsStop = eventService.getEventPackages();
		assertThat(eventsStop.size()).isEqualTo(1);
		assertThat(eventsStop.get(0).getChannels()).containsExactlyInAnyOrder(ApplicationConstants.EVENT_APPLICATION_STOPPING);
		assertThat(eventsStop.get(0).getEvent() instanceof EmptyEvent).isTrue();

	}




	@Test
	public void testWithProvider() {

		final String PROVIDER_NAME = "test_provider";
		final String PROVIDER_VALUE = "Test Provider";

		final TestEventService eventService = new TestEventService();
		final TestJfxStarter starter = new TestJfxStarter();

		final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
		applicationConfiguration.getProviderFactories().add(new StringFactory(PROVIDER_NAME, PROVIDER_VALUE));

		final Application application = new Application(applicationConfiguration);
		application.setJFXApplicationStarter(starter);
		application.setCoreProviderConfiguration(new TestCoreProviderConfig(eventService));

		application.run();

		final StringProvider provider = new StringProvider(PROVIDER_NAME);
		assertThat(provider.get()).isNotNull();
		assertThat(provider.get()).isEqualTo(PROVIDER_VALUE);

	}





	@Test
	public void testWithPlugin() {

		final TestPlugin plugin = new TestPlugin("test", "Test", "1.0");


		final TestJfxStarter starter = new TestJfxStarter();

		final ApplicationConfiguration applicationConfiguration = new ApplicationConfiguration();
		applicationConfiguration.getPlugins().add(plugin);

		final Application application = new Application(applicationConfiguration);
		application.setJFXApplicationStarter(starter);
		application.setCoreProviderConfiguration(new TestCoreProviderConfig(new TestEventService()));

		application.run();
		assertThat(plugin.getLoadedCounter()).isEqualTo(1);
		assertThat(plugin.getUnloadedCounter()).isEqualTo(0);

		starter.stop();
		assertThat(plugin.getLoadedCounter()).isEqualTo(1);
		assertThat(plugin.getUnloadedCounter()).isEqualTo(1);
	}




	private static class TestJfxStarter extends JFXApplication.JFXStarter {


		private EmptyCallback stopCallback = () -> {
		};




		@Override
		public void start(final Callback<Stage> startCallback, final EmptyCallback stopCallback) {
			startCallback.execute(null);
			this.stopCallback = stopCallback;
		}




		public void stop() {
			stopCallback.execute();
		}

	}






	private static class TestCoreProviderConfig extends CoreProviderConfiguration {


		private final TestEventService eventService;




		private TestCoreProviderConfig(final TestEventService eventService) {
			this.eventService = eventService;
		}




		@Override
		public void configure() {
			add(new InstanceFactory<>(PluginService.class) {
				@Override
				public PluginService buildObject() {
					return new PluginServiceImpl();
				}
			});
			add(new InstanceFactory<>(ViewService.class) {
				@Override
				public ViewService buildObject() {
					return Mockito.mock(ViewService.class);
				}
			});
			add(new InstanceFactory<>(EventService.class) {
				@Override
				public EventService buildObject() {
					return eventService;
				}
			});
		}

	}






	@Slf4j
	private static class TestEventService extends EventService {


		private final List<EventPackage<?>> eventPackages = new ArrayList<>();




		public TestEventService() {
			this.subscribe(e -> {
				log.debug("Captured event for test: ({}) - {}.", String.join(", ", e.getChannels()), e.getEvent());
				eventPackages.add(e);
			});
		}




		public List<EventPackage<?>> getEventPackages() {
			final List<EventPackage<?>> resultList = new ArrayList<>(eventPackages);
			eventPackages.clear();
			return resultList;
		}

	}






	@Getter
	private static class TestPlugin extends Plugin {


		private int loadedCounter = 0;

		private int unloadedCounter = 0;




		public TestPlugin(final String id, final String displayName, final String version) {
			super(id, displayName, version);
		}




		@Override
		public void onLoad() {
			loadedCounter++;
		}




		@Override
		public void onUnload() {
			unloadedCounter++;
		}


	}

}
