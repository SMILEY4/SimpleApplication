package de.ruegnerlukas.simpleapplication.common.plugins;

public class TestPluginUtils {


	public static Plugin createPlugin(final String id, final String[] dependencies) {
		return new TestPlugin(id, "version", id, dependencies, false);
	}




	public static Plugin createPlugin(
			final String id,
			final String version,
			final String displayName,
			final String[] dependencies) {
		return new TestPlugin(id, version, displayName, dependencies, false);
	}




	public static Plugin createPlugin(
			final String id,
			final String version,
			final String displayName,
			final String[] dependencies,
			final boolean failLoading) {
		return new TestPlugin(id, version, displayName, dependencies, failLoading);
	}




	static class TestPlugin extends Plugin {


		public boolean failLoading;




		/**
		 * @param id                 the unique identifier of this plugin
		 * @param version            the version of this plugin
		 * @param displayName        a readable name of this plugin
		 * @param pluginDependencies a list of plugin-ids/system-ids this plugin depends on.
		 * @param failLoading        whether the plugin should fail to load
		 */
		public TestPlugin(String id, String version, String displayName, String[] pluginDependencies, boolean failLoading) {
			super(id, version, displayName, pluginDependencies);
			this.failLoading = failLoading;
		}




		@Override
		public boolean onLoad() {
			return !failLoading;
		}




		@Override
		public void onDestroy() {

		}

	}

}
