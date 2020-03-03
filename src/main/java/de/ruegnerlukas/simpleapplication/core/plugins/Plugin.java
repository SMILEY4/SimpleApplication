package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

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
	 * All ids of other plugins or components this plugin depends on.
	 * This plugin can not be loaded unless all dependencies are loaded and will be unloaded once one dependency was unloaded.
	 */
	private final Set<String> dependencyIds = new HashSet<>();

	/**
	 * Whether this plugin is marked as autoload. Autoload-plugins get automatically loaded when all their dependencies are loaded.
	 */
	private final boolean autoload;




	/**
	 * Called when this plugin is loaded.
	 */
	public abstract void onLoad();

	/**
	 * Called when this plugin is unloaded.
	 */
	public abstract void onUnload();

}
