package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.ButtonBase;
import lombok.Getter;

import java.util.Optional;

public class ActionListenerProperty extends Property {


	@Getter
	private final ActionListener listener;




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




	public static Optional<ActionListener> getActionListener(SNode node) {
		return node.getPropertySafe(ActionListenerProperty.class)
				.map(ActionListenerProperty::getListener);
	}




	public interface ActionListener {


		void onAction();

	}






	public static class ButtonActionListenerUpdatingBuilder implements PropFxNodeUpdatingBuilder<ActionListenerProperty, ButtonBase> {


		@Override
		public void build(final SceneContext context, final SNode node, final ActionListenerProperty property, final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final ActionListenerProperty property, final SNode node, final ButtonBase fxNode) {
			fxNode.setOnAction(event -> property.getListener().onAction());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final ActionListenerProperty property, final SNode node, final ButtonBase fxNode) {
			fxNode.setOnAction(null);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}

}
