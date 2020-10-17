package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ActionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

public class OnActionEventProperty extends AbstractEventListenerProperty<ActionEventData> {


	/**
	 * The listener for events with {@link ActionEventData}.
	 */
	@Getter
	private final SuiEventListener<ActionEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ActionEventData}.
	 */
	public OnActionEventProperty(final String propertyId, final SuiEventListener<ActionEventData> listener) {
		super(OnActionEventProperty.class, propertyId);
		this.listener = listener;
	}




	@SuppressWarnings ("unchecked")
	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		default T eventAction(final SuiEventListener<ActionEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventAction(listener));
			return (T) this;
		}
		default T eventAction(final String propertyId, final SuiEventListener<ActionEventData> listener) {
			getFactoryInternalProperties().add(EventProperties.eventAction(propertyId, listener));
			return (T) this;
		}


	}






	public static class ButtonBaseUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnActionEventProperty, ButtonBase> {


		@Override
		public void build(final SuiNode node,
						  final OnActionEventProperty property,
						  final ButtonBase fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnActionEventProperty property,
									 final SuiNode node,
									 final ButtonBase fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnActionEventProperty property,
									 final SuiNode node,
									 final ButtonBase fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final ButtonBase fxNode, final OnActionEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new ActionEventData(e)));
		}

	}


}
