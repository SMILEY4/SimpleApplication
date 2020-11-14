package de.ruegnerlukas.simpleapplication.core.application;

import de.ruegnerlukas.simpleapplication.common.eventbus.EventBus;
import de.ruegnerlukas.simpleapplication.common.eventbus.EventBusImpl;
import de.ruegnerlukas.simpleapplication.common.eventbus.SubscriptionData;
import de.ruegnerlukas.simpleapplication.common.events.specializedevents.EmptyEvent;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
import de.ruegnerlukas.simpleapplication.core.application.jfx.JFXStarter;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class ApplicationTest {


	@Test
	public void testSimple() {

		final TestEventBus eventService = new TestEventBus();
		final TestJfxStarter starter = new TestJfxStarter();

		final Application application = new Application(new ApplicationConfiguration());
		application.setJFXApplicationStarter(starter);
		application.setCoreProviderConfiguration(new TestCoreProviderConfig(eventService));

		application.run();
		final List<Object> eventsStart = eventService.getEventPackages();
		assertThat(eventsStart.size()).isEqualTo(1);
		assertThat(eventsStart.get(0).getClass()).isEqualTo(EmptyEvent.class);

		starter.stop();
		final List<Object> eventsStop = eventService.getEventPackages();
		assertThat(eventsStop.size()).isEqualTo(1);
		assertThat(eventsStop.get(0).getClass()).isEqualTo(EmptyEvent.class);
	}




	@Test
	public void testWithProvider() {

		final String PROVIDER_NAME = "test_provider";
		final String PROVIDER_VALUE = "Test Provider";

		final TestEventBus eventService = new TestEventBus();
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
		application.setCoreProviderConfiguration(new TestCoreProviderConfig(new TestEventBus()));

		application.run();
		assertThat(plugin.getLoadedCounter()).isEqualTo(1);
		assertThat(plugin.getUnloadedCounter()).isEqualTo(0);

		starter.stop();
		assertThat(plugin.getLoadedCounter()).isEqualTo(1);
		assertThat(plugin.getUnloadedCounter()).isEqualTo(1);
	}




	private static class TestJfxStarter extends JFXStarter {


		private Runnable stopAction = () -> {
		};




		@Override
		public void start(final Consumer<Stage> startAction, final Runnable stopAction) {
			startAction.accept(null);
			this.stopAction = stopAction;
		}




		public void stop() {
			stopAction.run();
		}

	}






	private static class TestCoreProviderConfig extends CoreProviderConfiguration {


		private final TestEventBus eventService;




		private TestCoreProviderConfig(final TestEventBus eventService) {
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
			add(new InstanceFactory<>(EventBus.class) {
				@Override
				public EventBus buildObject() {
					return eventService;
				}
			});
		}

	}






	@Slf4j
	private static class TestEventBus extends EventBusImpl {


		private final List<Object> events = new ArrayList<>();




		public TestEventBus() {
			this.subscribe(SubscriptionData.anyType(), events::add);
		}




		public List<Object> getEventPackages() {
			final List<Object> resultList = new ArrayList<>(events);
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
