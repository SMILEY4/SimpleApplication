package de.ruegnerlukas.simpleapplication.common.plugins;

import lombok.Getter;

@Getter
public abstract class Plugin {


	/**
	 * The unique identifier of this plugin.
	 */
	private final String id;

	/**
	 * The version of this plugin.
	 */
	private final String version;

	/**
	 * A readable name of this plugin.
	 */
	private final String displayName;

	/**
	 * A list of plugin-ids/system-ids this plugin depends on.
	 */
	private final String[] pluginDependencies;




	/**
	 * @param id                 the unique identifier of this plugin
	 * @param version            the version of this plugin
	 * @param displayName        a readable name of this plugin
	 * @param pluginDependencies a list of plugin-ids/system-ids this plugin depends on.
	 *                           All plugins/systems must be loaded before this plugin is loaded
	 */
	public Plugin(final String id, final String version, final String displayName, final String[] pluginDependencies) {
		this.id = id;
		this.version = version;
		this.displayName = displayName;
		this.pluginDependencies = pluginDependencies;
	}




	/**
	 * Called when the plugin is loaded.
	 * @return true, when the plugin was successfully loaded
	 */
	public abstract boolean onLoad();

	/**
	 * Called when the plugin is unloaded
	 */
	public abstract void onUnload();


}
