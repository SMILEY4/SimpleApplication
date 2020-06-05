package simpleui.core.nodes;

import javafx.scene.Node;
import javafx.scene.control.Button;
import simpleui.core.properties.ActionListenerProperty;
import simpleui.core.properties.ButtonTextProperty;
import simpleui.core.properties.Property;

import java.util.Optional;

public class SButton extends SNode {


	public SButton(final Property... properties) {
		super(properties);
	}




	@Override
	public Node buildFxNode() {
		Button button = new Button(getText());
		getActionListener().ifPresent(listener -> button.setOnAction(e -> listener.onAction()));
		return button;
	}




	@Override
	protected MutationResult mutateUpdateProperty(final Class<? extends Property> propKey,
												  final Property propThis, final Property propOther) {
		if (propKey == ButtonTextProperty.class) {
			getFXButton().setText(((ButtonTextProperty) propOther).getText());
		}
		if (propKey == ActionListenerProperty.class) {
			getFXButton().setOnAction(event -> ((ActionListenerProperty) propOther).getListener().onAction());
		}
		return MutationResult.MUTATED;
	}




	@Override
	protected MutationResult mutateRemoveProperty(final Class<? extends Property> propKey, final Property propThis) {
		if (propKey == ButtonTextProperty.class) {
			getFXButton().setText("");
		}
		if (propKey == ActionListenerProperty.class) {
			getFXButton().setOnAction(null);
		}
		return MutationResult.MUTATED;
	}




	@Override
	protected MutationResult mutateAddProperty(final Class<? extends Property> propKey, final Property propOther) {
		if (propKey == ButtonTextProperty.class) {
			getFXButton().setText(((ButtonTextProperty) propOther).getText());
		}
		if (propKey == ActionListenerProperty.class) {
			getFXButton().setOnAction(event -> ((ActionListenerProperty) propOther).getListener().onAction());
		}
		return MutationResult.MUTATED;
	}




	private Button getFXButton() {
		return (Button) this.getLinkedFxNode();
	}




	private ButtonTextProperty getTextProperty() {
		return this.getProperty(ButtonTextProperty.class);
	}




	private String getText() {
		return Optional.ofNullable(getTextProperty().getText()).orElse("");
	}




	private ActionListenerProperty getActionListenerProperty() {
		return this.getProperty(ActionListenerProperty.class);
	}




	private Optional<ActionListenerProperty.ActionListener> getActionListener() {
		final ActionListenerProperty prop = getActionListenerProperty();
		if (prop != null) {
			return Optional.ofNullable(prop.getListener());
		} else {
			return Optional.empty();
		}
	}




	@Override
	public void print(final int level) {
		System.out.println(" ".repeat(level * 3)
				+ getClass().getSimpleName()
				+ " #" + Integer.toHexString(hashCode())
				+ " {"
				+ "text:" + getText() + ","
				+ "hasListener: " + getActionListener().isPresent()
				+ "}"
				+ (getLinkedFxNode() != null ? " (fx)" : "")
		);
	}

}
