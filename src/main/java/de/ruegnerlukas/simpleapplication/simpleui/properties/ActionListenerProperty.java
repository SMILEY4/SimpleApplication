package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult;

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






	public static class ButtonActionListenerUpdatingBuilder implements PropFxNodeUpdatingBuilder<ActionListenerProperty, ButtonBase> {


		@Override
		public void build(final SceneContext context, final SNode node, final ActionListenerProperty property,
						  final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
		}




		@Override
		public MutationResult update(final SceneContext context, final ActionListenerProperty property,
									 final SNode node, final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SceneContext context, final ActionListenerProperty property,
									 final SNode node, final ButtonBase fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}

	}

}
