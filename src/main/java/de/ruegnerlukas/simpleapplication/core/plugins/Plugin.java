package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Plugin {


	/**
	 * Meta-information about this plugin.
	 */
	private final PluginInformation information;




	/**
	 * @return the id of this plugin obtained from the {@link PluginInformation}.
	 */
	public String getId() {
		return information.getId();
	}




	/**
	 * Called when this plugin is loaded.
	 */
	public abstract void onLoad();

	/**
	 * Called when this plugin is unloaded.
	 */
	public abstract void onUnload();

}
