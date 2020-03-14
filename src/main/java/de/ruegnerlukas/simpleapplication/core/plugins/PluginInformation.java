package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginInformation {


	/**
	 * The unique id of the plugin.
	 */
	private String id;

	/**
	 * A readable name of the plugin.
	 */
	private String displayName;

	/**
	 * The version of the plugin.
	 */
	private String version;

	/**
	 * All ids of other plugins or components the plugin depends on.
	 * The plugin can not be loaded unless all dependencies are loaded and will be unloaded once one dependency was unloaded.
	 */
	private Set<String> dependencyIds = new HashSet<>();

	/**
	 * Whether the plugin is marked as autoload. Autoload-plugins get automatically loaded when all their dependencies are loaded.
	 */
	private boolean autoload;




	/**
	 * @param id          the id of the plugin
	 * @param displayName the name of the plugin
	 * @param version     the version of the plugin
	 * @param autoload    whether the plugin is marked as autoload
	 */
	public PluginInformation(final String id, final String displayName, final String version, final boolean autoload) {
		this.id = id;
		this.displayName = displayName;
		this.version = version;
		this.autoload = autoload;
	}


}
