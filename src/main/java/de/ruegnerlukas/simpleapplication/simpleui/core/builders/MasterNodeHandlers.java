package de.ruegnerlukas.simpleapplication.simpleui.core.builders;

import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MasterNodeMutator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MasterNodeHandlers {

	/**
	 * The primary fx node builder.
	 */
	private final MasterFxNodeBuilder fxNodeBuilder;

	/**
	 * The primary node mutator.
	 */
	private final MasterNodeMutator mutator;

}
