package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponentRenderer;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;


public class WindowRootElement {


	public static WindowRootElement windowRoot() {
		return new WindowRootElement(null);
	}




	public static WindowRootElement windowRoot(final Stage stage) {
		return new WindowRootElement(stage);
	}




	@Setter
	@Getter
	private Stage stage;

	@Getter
	private String title;

	@Getter
	private Number width;

	@Getter
	private Number height;

	@Getter
	private boolean wait;

	@Getter
	private SuiComponentRenderer<? extends SuiState> nodeFactoryBuilder;

	@Getter
	private final List<WindowRootElement> modalWindowRootElements = new ArrayList<>();

	@Getter
	private Predicate<SuiState> condition;

	@Getter
	private Consumer<SuiState> onClose;




	public WindowRootElement(final Stage stage) {
		this.stage = stage;
	}




	public WindowRootElement title(final String title) {
		this.title = title;
		return this;
	}




	public WindowRootElement size(final Number width, final Number height) {
		this.width = width;
		this.height = height;
		return this;
	}




	public WindowRootElement wait(final boolean wait) {
		this.wait = wait;
		return this;
	}




	public WindowRootElement onClose(final Runnable action) {
		this.onClose = state -> action.run();
		return this;
	}




	public <T extends SuiState> WindowRootElement onClose(final Class<T> stateType, final Consumer<T> action) {
		//noinspection unchecked
		this.onClose = (Consumer<SuiState>) action;
		return this;
	}




	public <T extends SuiState> WindowRootElement content(final Class<T> stateType, final SuiComponentRenderer<T> nodeFactoryBuilder) {
		this.nodeFactoryBuilder = nodeFactoryBuilder;
		return this;
	}




	public <T extends SuiState> WindowRootElement condition(final Class<T> stateType, final Predicate<T> condition) {
		//noinspection unchecked
		this.condition = (Predicate<SuiState>) condition;
		return this;
	}




	public WindowRootElement modal(final WindowRootElement windowRootElement) {
		// validate: windowRootElement must be unique
		modalWindowRootElements.add(windowRootElement);
		return this;
	}


}
