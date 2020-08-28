package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEvent;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.CheckBox;
import lombok.Getter;

public class OnCheckedEventProperty extends AbstractEventListenerProperty<CheckedEventData> {


	/**
	 * The listener for events with {@link CheckedEventData}.
	 */
	@Getter
	private final SUIEventListener<CheckedEventData> listener;




	/**
	 * @param listener the listener for events with {@link CheckedEventData}.
	 */
	public OnCheckedEventProperty(final SUIEventListener<CheckedEventData> listener) {
		super(OnCheckedEventProperty.class);
		this.listener = listener;
	}




	public static class CheckboxUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnCheckedEventProperty, CheckBox> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final OnCheckedEventProperty property,
						  final CheckBox fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final OnCheckedEventProperty property,
									 final SUINode node, final CheckBox fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final OnCheckedEventProperty property,
									 final SUINode node, final CheckBox fxNode) {
			fxNode.setOnMouseClicked(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final CheckBox fxNode, final OnCheckedEventProperty property) {
			fxNode.selectedProperty().addListener((value, prev, next) -> {
				if (next != null && next) {
					property.getListener().onEvent(new SUIEvent<>(
							"check.checked",
							CheckedEventData.builder()
									.checked(true)
									.build()
					));
				}
			});
		}

	}


}