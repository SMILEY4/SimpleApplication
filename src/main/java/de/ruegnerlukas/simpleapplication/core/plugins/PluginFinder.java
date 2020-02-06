package de.ruegnerlukas.simpleapplication.core.plugins;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class PluginFinder {


	private List<Plugin> plugins = new ArrayList<>();




	protected void add(final Plugin plugin) {
		plugins.add(plugin);
	}




	public abstract void findPlugins();




	public List<Plugin> getPlugins() {
		return Collections.unmodifiableList(plugins);
	}

}
