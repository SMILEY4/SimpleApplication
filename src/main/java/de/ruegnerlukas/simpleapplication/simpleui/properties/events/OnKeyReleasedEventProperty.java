package de.ruegnerlukas.simpleapplication.simpleui.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.events.KeyEventData;
import de.ruegnerlukas.simpleapplication.simpleui.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import lombok.Getter;

public class OnKeyReleasedEventProperty extends AbstractEventListenerProperty<KeyEventData> {


	/**
	 * The listener for events with {@link KeyEventData}.
	 */
	@Getter
	private final SUIEventListener<KeyEventData> listener;




	/**
	 * @param listener the listener for events with {@link KeyEventData}.
	 */
	public OnKeyReleasedEventProperty(final SUIEventListener<KeyEventData> listener) {
		super(OnKeyReleasedEventProperty.class);
		this.listener = listener;
	}




	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<OnKeyReleasedEventProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final OnKeyReleasedEventProperty property,
						  final Node fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final OnKeyReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final OnKeyReleasedEventProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			fxNode.setOnKeyPressed(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final Node fxNode, final OnKeyReleasedEventProperty property) {
			fxNode.setOnKeyReleased(e -> property.getListener().onEvent(
					KeyEventData.builder()
							.keyCode(e.getCode())
							.character(e.getCharacter())
							.altDown(e.isAltDown())
							.ctrlDown(e.isControlDown())
							.metaDown(e.isMetaDown())
							.shiftDown(e.isShiftDown())
							.shortcutDown(e.isShortcutDown())
							.source(e)
							.build()
			));
		}

	}


}
