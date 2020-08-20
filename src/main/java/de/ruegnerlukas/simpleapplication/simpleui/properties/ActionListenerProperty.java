package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;

public class ActionListenerProperty extends Property {


	/**
	 * The listener
	 */
	@Getter
	private final ActionListener listener;




	/**
	 * @param listener the listener to use
	 */
	public ActionListenerProperty(final ActionListener listener) {
		super(ActionListenerProperty.class);
		this.listener = listener;
	}




	@Override
	public boolean isPropertyEqual(final Property other) {
		return this.listener.equals(((ActionListenerProperty) other).getListener());
	}




	@Override
	public String printValue() {
		return getListener() != null ? getListener().toString() : "null";
	}




	public interface ActionListener {


		/**
		 * Called on an action.
		 */
		void onAction();

	}






	public static class ButtonListenerUpdatingBuilder implements PropFxNodeUpdatingBuilder<ActionListenerProperty, ButtonBase> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SUINode node, final ActionListenerProperty property,
						  final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final ActionListenerProperty property,
									 final SUINode node, final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final ActionListenerProperty property,
									 final SUINode node, final ButtonBase fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}

	}

}
