package de.ruegnerlukas.simpleapplication.core.application.plugins;

import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginService;
import de.ruegnerlukas.simpleapplication.core.plugins.PluginServiceImpl;
import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginServiceTest {

	@Test
	public void testSimple() {

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


}
