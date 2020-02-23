package de.ruegnerlukas.simpleapplication.core.application.plugins;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginServiceTest {


	@BeforeClass
	public static void setup() {
		ProviderService.registerFactory(new InstanceFactory<>(EventService.class) {
			@Override
			public EventService buildObject() {
				return new EventService();
			}
		});
	}




	@Test
	public void testPluginsSimple() {

		final PluginService pluginService = new PluginServiceImpl();
		final Plugin plugin = buildPluginMock("test.plugin", Set.of());

		pluginService.registerPlugin(plugin);
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();

		pluginService.loadPlugin(plugin.getId());
		verify(plugin).onLoad();
		assertThat(pluginService.isLoaded(plugin.getId())).isTrue();

		pluginService.loadPlugin(plugin.getId());
		verify(plugin, times(1)).onLoad();
		assertThat(pluginService.isLoaded(plugin.getId())).isTrue();

		pluginService.unloadPlugin(plugin.getId());
		verify(plugin).onUnload();
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();

		pluginService.unloadPlugin(plugin.getId());
		verify(plugin, times(1)).onUnload();
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();

	}




	@Test
	public void testLoadPluginWithDependencies() {

		/*
		digraph G {
			B -> A;
			C -> A;
			D -> B, C;
			E -> B;
			F;
		}
		 */

		final Plugin pluginA = buildPluginMock("a", Set.of());
		final Plugin pluginB = buildPluginMock("b", Set.of("a"));
		final Plugin pluginC = buildPluginMock("c", Set.of("a"));
		final Plugin pluginD = buildPluginMock("d", Set.of("b", "c"));
		final Plugin pluginE = buildPluginMock("e", Set.of("b"));
		final Plugin pluginF = buildPluginMock("f", Set.of());
		final PluginService pluginService = createPluginService(pluginA, pluginB, pluginC, pluginD, pluginE, pluginF);

		pluginService.loadPluginWithDependencies(pluginD.getId());
		verify(pluginA).onLoad();
		verify(pluginB).onLoad();
		verify(pluginC).onLoad();
		verify(pluginD).onLoad();
		assertThat(pluginService.isLoaded(pluginA.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginB.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginC.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginD.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginE.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginF.getId())).isFalse();
	}




	@Test
	public void testUnloadPluginWithDependencies() {

		/*
		digraph G {
			B -> A;
			C -> A;
			D -> B, C;
			E -> B;
			F;
		}
		 */

		final Plugin pluginA = buildPluginMock("a", Set.of());
		final Plugin pluginB = buildPluginMock("b", Set.of("a"));
		final Plugin pluginC = buildPluginMock("c", Set.of("a"));
		final Plugin pluginD = buildPluginMock("d", Set.of("b", "c"));
		final Plugin pluginE = buildPluginMock("e", Set.of("b"));
		final Plugin pluginF = buildPluginMock("f", Set.of());
		final PluginService pluginService = createPluginService(pluginA, pluginB, pluginC, pluginD, pluginE, pluginF);
		pluginService.loadAllPlugins();

		pluginService.unloadPlugin(pluginB.getId());
		verify(pluginE).onUnload();
		verify(pluginD).onUnload();
		verify(pluginB).onUnload();
		assertThat(pluginService.isLoaded(pluginA.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginB.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginC.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginD.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginE.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginF.getId())).isTrue();
	}




	private Plugin buildPluginMock(final String id, Set<String> dependencies) {
		final Plugin plugin = Mockito.mock(Plugin.class);
		when(plugin.getId()).thenReturn(id);
		when(plugin.getDisplayName()).thenReturn(id);
		when(plugin.getVersion()).thenReturn("testing");
		when(plugin.getDependencyIds()).thenReturn(dependencies);
		return plugin;
	}




	@Test
	public void testComponentsSimple() {

		final String COMPONENT_ID = "test.component";
		final PluginService pluginService = new PluginServiceImpl();

		assertThat(pluginService.isLoaded(COMPONENT_ID)).isFalse();

		pluginService.loadComponent(COMPONENT_ID);
		assertThat(pluginService.isLoaded(COMPONENT_ID)).isTrue();

		pluginService.unloadComponent(COMPONENT_ID);
		assertThat(pluginService.isLoaded(COMPONENT_ID)).isFalse();
	}




	@Test
	public void testLoadUnloadAll() {

		final PluginService pluginService = new PluginServiceImpl();

		final Plugin pluginA = Mockito.mock(Plugin.class);
		when(pluginA.getId()).thenReturn("test.plugin.a");
		when(pluginA.getDisplayName()).thenReturn("Test Plugin A");
		when(pluginA.getVersion()).thenReturn("1.0");

		final Plugin pluginB = Mockito.mock(Plugin.class);
		when(pluginB.getId()).thenReturn("test.plugin.b");
		when(pluginB.getDisplayName()).thenReturn("Test Plugin B");
		when(pluginB.getVersion()).thenReturn("1.0");

		pluginService.registerPlugins(List.of(pluginA, pluginB));
		assertThat(pluginService.isLoaded(pluginA.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginB.getId())).isFalse();

		pluginService.loadAllPlugins();
		verify(pluginA).onLoad();
		verify(pluginB).onLoad();
		assertThat(pluginService.isLoaded(pluginA.getId())).isTrue();
		assertThat(pluginService.isLoaded(pluginB.getId())).isTrue();

		pluginService.unloadAllPlugins();
		verify(pluginA).onUnload();
		verify(pluginB).onUnload();
		assertThat(pluginService.isLoaded(pluginA.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginB.getId())).isFalse();

	}




	/**
	 * Creates a new plugin service and registers the given plugins.
	 *
	 * @param plugins the plugins
	 * @return the creates service
	 */
	private PluginService createPluginService(final Plugin... plugins) {
		final PluginService service = new PluginServiceImpl();
		for (Plugin plugin : plugins) {
			service.registerPlugin(plugin);
		}
		return service;
	}


}
