package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.InstanceFactory;
import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.ProviderService;
import de.ruegnerlukas.simpleapplication.core.events.EventService;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginServiceTest {


	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();




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
	public void testLoadPluginMissingDependencies() {

		final Plugin pluginB = buildPluginMock("b", Set.of("a"));
		final Plugin pluginC = buildPluginMock("c", Set.of("b"));
		final PluginService pluginService = createPluginService(pluginB, pluginC);

		assertThat(pluginService.findById("a")).isNotPresent();
		assertThat(pluginService.findById(pluginB.getId())).isPresent();
		assertThat(pluginService.findById(pluginC.getId())).isPresent();
		assertThat(pluginService.canLoadDirectly(pluginB.getId())).isFalse();
		assertThat(pluginService.canLoadDirectly(pluginC.getId())).isFalse();

		pluginService.loadPluginWithDependencies(pluginC.getId());
		assertThat(pluginService.isLoaded(pluginB.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginC.getId())).isFalse();
	}




	@Test
	public void testLoadAllPluginsMissingPlugin() {

		final Plugin pluginB = buildPluginMock("b", Set.of("a"));
		final Plugin pluginC = buildPluginMock("c", Set.of("b"));
		final PluginService pluginService = createPluginService(pluginB, pluginC);

		assertThat(pluginService.findById("a")).isNotPresent();
		assertThat(pluginService.findById(pluginB.getId())).isPresent();
		assertThat(pluginService.findById(pluginC.getId())).isPresent();
		assertThat(pluginService.canLoadDirectly(pluginB.getId())).isFalse();
		assertThat(pluginService.canLoadDirectly(pluginC.getId())).isFalse();

		pluginService.loadAllPlugins();
		assertThat(pluginService.isLoaded(pluginB.getId())).isFalse();
		assertThat(pluginService.isLoaded(pluginC.getId())).isFalse();
	}




	@Test
	public void testLoadPluginWithMissingComponent() {

		final String ID_COMPONENT = "test.component";
		final Plugin plugin = buildPluginMock("test.plugin", Set.of(ID_COMPONENT));
		final PluginService pluginService = createPluginService(plugin);

		assertThat(pluginService.findById(plugin.getId())).isPresent();
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();
		assertThat(pluginService.isLoaded(ID_COMPONENT)).isFalse();

		pluginService.loadPluginWithDependencies(plugin.getId());
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();
		assertThat(pluginService.isLoaded(ID_COMPONENT)).isFalse();

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

		final Plugin pluginA = buildPluginMock("test.plugin.a", Set.of());
		final Plugin pluginB = buildPluginMock("test.plugin.b", Set.of());

		final PluginService pluginService = createPluginService(pluginA, pluginB);
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




	@Test
	public void testAutoloadPlugin() {

		final String ID_COMPONENT = "test.component";

		final Plugin plugin = buildPluginMock("test.plugin", Set.of(ID_COMPONENT), true);

		final PluginService pluginService = createPluginService(plugin);
		assertThat(pluginService.isLoaded(plugin.getId())).isFalse();
		assertThat(pluginService.canLoadDirectly(plugin.getId())).isFalse();

		pluginService.loadComponent(ID_COMPONENT);
		verify(plugin, times(1)).onLoad();
		assertThat(pluginService.isLoaded(plugin.getId())).isTrue();

	}




	private Plugin buildPluginMock(final String id, Set<String> dependencies) {
		return buildPluginMock(id, dependencies, false);
	}




	private Plugin buildPluginMock(final String id, Set<String> dependencies, boolean autoload) {
		final Plugin plugin = Mockito.mock(Plugin.class);
		when(plugin.getInformation()).thenReturn(new PluginInformation(id, id, "testing", dependencies, autoload));
		when(plugin.getId()).thenReturn(id);
		return plugin;
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
