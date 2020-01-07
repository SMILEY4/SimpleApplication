package de.ruegnerlukas.simpleapplication.common.plugins;

import org.junit.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PluginTest {


	@Test
	public void testSinglePlugin() {
		final PluginManager manager = new PluginManager();
		final String PLUGIN_ID = "test_plugin";

		final Plugin plugin = Mockito.mock(TestPluginUtils.TestPlugin.class);
		when(plugin.onLoad()).thenReturn(true);
		when(plugin.getId()).thenReturn(PLUGIN_ID);
		when(plugin.getPluginDependencies()).thenReturn(new String[]{});

		manager.register(plugin);
		verify(plugin, never()).onLoad();
		verify(plugin, never()).onUnload();
		assertThat(manager.getRegisteredPlugins()).containsExactly(PLUGIN_ID);
		assertThat(manager.getLoadedPlugins()).isEmpty();
		assertThat(manager.getUnloadedPlugins()).containsExactly(PLUGIN_ID);


		try {
			manager.unload(PLUGIN_ID);
		} catch (IllegalStateException ignored) {
		}
		verify(plugin, never()).onLoad();
		verify(plugin, never()).onUnload();
		assertThat(manager.getRegisteredPlugins()).containsExactly(PLUGIN_ID);
		assertThat(manager.getLoadedPlugins()).isEmpty();
		assertThat(manager.getUnloadedPlugins()).containsExactly(PLUGIN_ID);

		manager.load(PLUGIN_ID);
		verify(plugin).onLoad();
		verify(plugin, never()).onUnload();
		assertThat(manager.getRegisteredPlugins()).containsExactly(PLUGIN_ID);
		assertThat(manager.getLoadedPlugins()).containsExactly(PLUGIN_ID);
		assertThat(manager.getUnloadedPlugins()).isEmpty();

		manager.unload(PLUGIN_ID);
		verify(plugin).onUnload();
		assertThat(manager.getRegisteredPlugins()).containsExactly(PLUGIN_ID);
		assertThat(manager.getLoadedPlugins()).isEmpty();
		assertThat(manager.getUnloadedPlugins()).containsExactly(PLUGIN_ID);
	}




	@Test
	public void testLoadWithSimpleDependency() {
		final PluginManager manager = new PluginManager();
		final String PLUGIN_ID = "test_plugin";
		final String SYSTEM_ID = "test_system";

		final Plugin plugin = Mockito.mock(TestPluginUtils.TestPlugin.class);
		when(plugin.onLoad()).thenReturn(true);
		when(plugin.getId()).thenReturn(PLUGIN_ID);
		when(plugin.getPluginDependencies()).thenReturn(new String[]{SYSTEM_ID});

		manager.registerAndLoad(plugin);
		verify(plugin, never()).onLoad();
		verify(plugin, never()).onUnload();

		manager.load(SYSTEM_ID);
		verify(plugin).onLoad();
	}




	@Test
	public void testLoadWithComplexDependencies() {

		final PluginManager manager = new PluginManager();

		manager.register(TestPluginUtils.createPlugin("2", new String[]{"0", "1"}));
		manager.register(TestPluginUtils.createPlugin("3", new String[]{"0", "1"}));
		manager.register(TestPluginUtils.createPlugin("4", new String[]{"0"}));
		manager.register(TestPluginUtils.createPlugin("5", new String[]{"0"}));
		manager.register(TestPluginUtils.createPlugin("6", new String[]{"2", "7"}));
		manager.register(TestPluginUtils.createPlugin("7", new String[]{"3"}));
		manager.register(TestPluginUtils.createPlugin("8", new String[]{"3"}));
		manager.register(TestPluginUtils.createPlugin("9", new String[]{"6"}));

		assertThat(manager.getLoadedPlugins()).isEmpty();

		manager.load("0");
		assertThat(manager.getLoadedPlugins()).containsExactlyInAnyOrder("0", "4", "5");

		manager.load("1");
		assertThat(manager.getLoadedPlugins()).containsExactlyInAnyOrder("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		manager.registerAndLoad(TestPluginUtils.createPlugin("10", new String[]{"5"}));
		assertThat(manager.getLoadedPlugins()).containsExactlyInAnyOrder("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10");

	}




	@Test
	public void testUnloadWithSimpleDependency() {
		final PluginManager manager = new PluginManager();
		final String PLUGIN_ID = "test_plugin";
		final String SYSTEM_ID = "test_system";

		final Plugin plugin = Mockito.mock(TestPluginUtils.TestPlugin.class);
		when(plugin.onLoad()).thenReturn(true);
		when(plugin.getId()).thenReturn(PLUGIN_ID);
		when(plugin.getPluginDependencies()).thenReturn(new String[]{SYSTEM_ID});

		manager.load(SYSTEM_ID);
		manager.registerAndLoad(plugin);
		verify(plugin).onLoad();

		manager.unload(SYSTEM_ID);
		verify(plugin).onUnload();
	}




	@Test
	public void testUnloadWithComplexDependencies() {

		final PluginManager manager = new PluginManager();

		manager.register(TestPluginUtils.createPlugin("2", new String[]{"0", "1"}));
		manager.register(TestPluginUtils.createPlugin("3", new String[]{"0", "1"}));
		manager.register(TestPluginUtils.createPlugin("4", new String[]{"0"}));
		manager.register(TestPluginUtils.createPlugin("5", new String[]{"0"}));
		manager.register(TestPluginUtils.createPlugin("6", new String[]{"2", "7"}));
		manager.register(TestPluginUtils.createPlugin("7", new String[]{"3"}));
		manager.register(TestPluginUtils.createPlugin("8", new String[]{"3"}));
		manager.register(TestPluginUtils.createPlugin("9", new String[]{"6"}));

		manager.load("0");
		manager.load("1");
		assertThat(manager.getLoadedPlugins()).containsExactlyInAnyOrder("0", "1", "2", "3", "4", "5", "6", "7", "8", "9");

		manager.unload("3");
		assertThat(manager.getLoadedPlugins()).containsExactlyInAnyOrder("0", "1", "2", "4", "5");

	}




	@Test
	public void testRegisterPluginTwice() {
		final PluginManager manager = new PluginManager();
		final String PLUGIN_ID = "test_plugin";

		final Plugin pluginA = Mockito.mock(TestPluginUtils.TestPlugin.class);
		when(pluginA.onLoad()).thenReturn(true);
		when(pluginA.getId()).thenReturn(PLUGIN_ID);
		when(pluginA.getPluginDependencies()).thenReturn(new String[]{});

		final Plugin pluginB = Mockito.mock(TestPluginUtils.TestPlugin.class);
		when(pluginB.onLoad()).thenReturn(true);
		when(pluginB.getId()).thenReturn(PLUGIN_ID);
		when(pluginB.getPluginDependencies()).thenReturn(new String[]{});

		manager.registerAndLoad(pluginA);
		verify(pluginA).onLoad();

		try {
			manager.registerAndLoad(pluginB);
		} catch (IllegalStateException ignored) {
		}
		verify(pluginB, never()).onLoad();
	}


}
