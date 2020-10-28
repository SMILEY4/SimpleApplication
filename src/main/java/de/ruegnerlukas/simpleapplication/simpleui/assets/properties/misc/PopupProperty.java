package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowConfig;
import javafx.application.Platform;
import javafx.scene.Node;
import lombok.Getter;

import java.util.function.BiFunction;

public class PopupProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<PopupProperty, PopupProperty, Boolean> COMPARATOR =
			(a, b) -> a.isShow() == b.isShow();


	/**
	 * Whether to show the window
	 */
	@Getter
	private final boolean show;

	/**
	 * The configuration of the window
	 */
	@Getter
	private final WindowConfig windowConfig;




	/**
	 * @param show         whether to show the window
	 * @param windowConfig the configuration of the window
	 */
	public PopupProperty(final boolean show, final WindowConfig windowConfig) {
		super(PopupProperty.class, COMPARATOR);
		Validations.INPUT.notNull(windowConfig).exception("The window config may not be null,");
		this.show = show;
		this.windowConfig = windowConfig;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param show         whether to show the window
		 * @param windowConfig the configuration of the window
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T popup(final boolean show, final WindowConfig windowConfig) {
			getBuilderProperties().add(new PopupProperty(show, windowConfig));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<PopupProperty, Node> {


		@Override
		public void build(final SuiNode node, final PopupProperty property, final Node fxNode) {
			if (property.isShow()) {
				open(property);
			}
		}




		@Override
		public MutationResult update(final PopupProperty property, final SuiNode node, final Node fxNode) {
			if (property.isShow()) {
				open(property);
			} else {
				close(property);
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final PopupProperty property, final SuiNode node, final Node fxNode) {
			if (property.isShow()) {
				close(property);
			}
			return MutationResult.MUTATED;
		}




		/**
		 * Opens the window of the given property
		 *
		 * @param property the popup property
		 */
		private void open(final PopupProperty property) {
			Platform.runLater(() -> SuiRegistry.get().getWindows().openWindow(property.getWindowConfig()));
		}




		/**
		 * Opens the window of the given property
		 *
		 * @param property the popup property
		 */
		private void close(final PopupProperty property) {
			Platform.runLater(() -> SuiRegistry.get().getWindows().closeWindow(property.getWindowConfig().getWindowId()));
		}

	}


}



