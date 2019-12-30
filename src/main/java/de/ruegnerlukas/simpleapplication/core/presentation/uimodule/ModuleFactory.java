package de.ruegnerlukas.simpleapplication.core.presentation.uimodule;

/**
 * Factory for {@link UIModule}s.
 */
public abstract class ModuleFactory {


	/**
	 * Creates a new {@link UIModule}.
	 *
	 * @return the created module.
	 */
	public abstract UIModule module();

}
