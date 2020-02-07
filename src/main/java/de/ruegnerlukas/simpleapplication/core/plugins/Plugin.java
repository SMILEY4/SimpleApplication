package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Plugin {


	/**
	 * The unique id of this plugin.
	 */
	private final String id;

	/**
	 * A readable name of this plugin.
	 */
	private final String displayName;

	/**
	 * The version of this plugin.
	 */
	private final String version;




	/**
	 * Called when this plugin is loaded.
	 */
	public abstract void onLoad();

	/**
	 * Called when this plugin is unloaded.
	 */
	public abstract void onUnload();

}
