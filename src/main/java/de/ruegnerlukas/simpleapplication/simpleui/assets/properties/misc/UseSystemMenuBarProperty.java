package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.application.Platform;
import javafx.scene.control.MenuBar;
import lombok.Getter;

import java.util.function.BiFunction;

public class UseSystemMenuBarProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<UseSystemMenuBarProperty, UseSystemMenuBarProperty, Boolean> COMPARATOR =
			(a, b) -> a.isUseSystemMenuBar() == b.isUseSystemMenuBar();

	/**
	 * Whether to use the host OS system menu bar.
	 */
	@Getter
	private final boolean useSystemMenuBar;




	/**
	 * @param useSystemMenuBar whether to use the host OS system menu bar.
	 */
	public UseSystemMenuBarProperty(final boolean useSystemMenuBar) {
		super(UseSystemMenuBarProperty.class, COMPARATOR);
		this.useSystemMenuBar = useSystemMenuBar;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @return this builder for chaining
		 */
		default T useSystemMenuBar() {
			return useSystemMenuBar(true);
		}

		/**
		 * @param useSystemMenuBar whether to use the system menu bar (if available)
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T useSystemMenuBar(final boolean useSystemMenuBar) {
			getBuilderProperties().add(new UseSystemMenuBarProperty(useSystemMenuBar));
			return (T) this;
		}

	}






	public static class MenuBarUpdatingBuilder implements PropFxNodeUpdatingBuilder<UseSystemMenuBarProperty, MenuBar> {


		@Override
		public void build(final SuiNode node,
						  final UseSystemMenuBarProperty property,
						  final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(property.isUseSystemMenuBar()));
		}




		@Override
		public MutationResult update(final UseSystemMenuBarProperty property,
									 final SuiNode node,
									 final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(property.isUseSystemMenuBar()));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final UseSystemMenuBarProperty property,
									 final SuiNode node,
									 final MenuBar fxNode) {
			Platform.runLater(() -> fxNode.setUseSystemMenuBar(false));
			return MutationResult.MUTATED;
		}

	}


}
