package de.ruegnerlukas.simpleapplication.core.plugins;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public abstract class Plugin {


	private final String id;

	private final String displayName;

	private final String version;




	public abstract void onLoad();

	public abstract void onUnload();

}
