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
	 * Whether the plugin should be hidden from the user.
	 */
	private final boolean hidden;




	/**
	 * @param id          the unique identifier of this plugin
	 * @param version     the version of this plugin
	 * @param displayName a readable name of this plugin
	 * @param hidden      whether the plugin should be hidden from the user
	 */
	public Plugin(final String id, final String version, final String displayName, final boolean hidden) {
		this.id = id;
		this.version = version;
		this.displayName = displayName;
		this.hidden = hidden;
	}


}
