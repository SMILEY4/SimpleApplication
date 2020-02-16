package de.ruegnerlukas.simpleapplication.core.application.plugins;

import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginServiceTest {


	@Test
	public void testPluginsSimple() {

		final PluginService pluginService = new PluginServiceImpl();

		final Plugin plugin = Mockito.mock(Plugin.class);
		when(plugin.getId()).thenReturn("test.plugin");
		when(plugin.getDisplayName()).thenReturn("Test Plugin");
		when(plugin.getVersion()).thenReturn("1.0");

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


}
