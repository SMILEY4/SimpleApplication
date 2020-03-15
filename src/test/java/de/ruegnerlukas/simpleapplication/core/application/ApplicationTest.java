package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.callbacks.Callback;
import de.ruegnerlukas.simpleapplication.common.callbacks.EmptyCallback;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.events.EventServiceImpl;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
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
		final List<Publishable> eventsStart = eventService.getEventPackages();
		assertThat(eventsStart.size()).isEqualTo(3);
		assertThat(eventsStart.get(0).getChannel()).isEqualTo(ApplicationConstants.EVENT_PRESENTATION_INITIALIZED);
		assertThat(eventsStart.get(1).getChannel()).isEqualTo(ApplicationConstants.EVENT_COMPONENT_LOADED);
		assertThat(eventsStart.get(2).getChannel()).isEqualTo(ApplicationConstants.EVENT_APPLICATION_STARTED);

		starter.stop();
		final List<Publishable> eventsStop = eventService.getEventPackages();
		assertThat(eventsStop.size()).isEqualTo(2);
		assertThat(eventsStop.get(0).getChannel()).isEqualTo(ApplicationConstants.EVENT_COMPONENT_UNLOADED);
		assertThat(eventsStop.get(1).getChannel()).isEqualTo(ApplicationConstants.EVENT_APPLICATION_STOPPING);

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
	private static class TestEventService extends EventServiceImpl {


		private final List<Publishable> events = new ArrayList<>();




		public TestEventService() {
			this.subscribe(events::add);
		}




		public List<Publishable> getEventPackages() {
			final List<Publishable> resultList = new ArrayList<>(events);
			events.clear();
			return resultList;
		}

	}






	@Getter
	private static class TestPlugin extends Plugin {


		private int loadedCounter = 0;

		private int unloadedCounter = 0;




		public TestPlugin(final String id, final String displayName, final String version) {
			super(new PluginInformation(id, displayName, version, false));
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
