package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import lombok.Getter;

import java.util.function.BiFunction;

public class SystemMenuBarProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SystemMenuBarProperty, SystemMenuBarProperty, Boolean> COMPARATOR =
			(a, b) -> a.isUseSystemMenuBar() == b.isUseSystemMenuBar();

	/**
	 * Whether to use the host OS system menu bar.
	 */
	@Getter
	private final boolean useSystemMenuBar;




	/**
	 * @param useSystemMenuBar whether to use the host OS system menu bar.
	 */
	public SystemMenuBarProperty(final boolean useSystemMenuBar) {
		super(SystemMenuBarProperty.class, COMPARATOR);
		this.useSystemMenuBar = useSystemMenuBar;
	}




	public static class MenuBarUpdatingBuilder implements PropFxNodeUpdatingBuilder<SystemMenuBarProperty, MenuBar> {


		@Override
		public void build(final SuiNode node,
						  final SystemMenuBarProperty property,
						  final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(property.isUseSystemMenuBar()));
		}




		@Override
		public MutationResult update(final SystemMenuBarProperty property,
									 final SuiNode node,
									 final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(property.isUseSystemMenuBar()));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SystemMenuBarProperty property,
									 final SuiNode node,
									 final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(false));
			return MutationResult.MUTATED;
		}

	}


}
