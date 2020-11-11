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


public final class WindowRootElement {


	/**
	 * Creates a new window root element
	 *
	 * @return the created instance
	 */
	public static WindowRootElement windowRoot() {
		return new WindowRootElement(null);
	}




	/**
	 * Creates a new window root element using the given stage
	 *
	 * @param stage the javafx stage to use for the window
	 * @return the created instance
	 */
	public static WindowRootElement windowRoot(final Stage stage) {
		return new WindowRootElement(stage);
	}




	/**
	 * The javafx stage
	 */
	@Setter
	@Getter
	private Stage stage;

	/**
	 * The title of the window
	 */
	@Getter
	private String title;

	/**
	 * The width of the window
	 */
	@Getter
	private Number width;

	/**
	 * The height of the window
	 */
	@Getter
	private Number height;

	/**
	 * Whether to wait for the window to close
	 */
	@Getter
	private boolean wait;

	/**
	 * The root node
	 */
	@Getter
	private SuiComponentRenderer<? extends SuiState> content;

	/**
	 * the possible child windows that can be opened
	 */
	@Getter
	private final List<WindowRootElement> modalWindowRootElements = new ArrayList<>();

	/**
	 * the condition for when to show this window
	 */
	@Getter
	private Predicate<SuiState> condition;

	/**
	 * An optional listener called when the window is closed
	 */
	@Getter
	private Consumer<SuiState> onClose;




	/**
	 * @param stage the javafx stage to use for this window or null
	 */
	private WindowRootElement(final Stage stage) {
		this.stage = stage;
	}




	/**
	 * @param title the title of the window
	 * @return this window root element for chaining
	 */
	public WindowRootElement title(final String title) {
		this.title = title;
		return this;
	}




	/**
	 * @param width  the width of the window
	 * @param height the height of the window
	 * @return this window root element for chaining
	 */
	public WindowRootElement size(final Number width, final Number height) {
		this.width = width;
		this.height = height;
		return this;
	}




	/**
	 * @param wait whether to wait for the window to close
	 * @return this window root element for chaining
	 */
	public WindowRootElement wait(final boolean wait) {
		this.wait = wait;
		return this;
	}




	/**
	 * @param action the action executed when the window is closed
	 * @return this window root element for chaining
	 */
	public WindowRootElement onClose(final Runnable action) {
		this.onClose = state -> action.run();
		return this;
	}




	/**
	 * @param stateType the expected type of the state
	 * @param action    the action with the stage to execute when the window is closed
	 * @param <T>       the generic type of the state
	 * @return this window root element for chaining
	 */
	public <T extends SuiState> WindowRootElement onClose(final Class<T> stateType, final Consumer<T> action) {
		//noinspection unchecked
		this.onClose = (Consumer<SuiState>) action;
		return this;
	}




	/**
	 * @param stateType the expected type of the state
	 * @param renderer  the renderer of the root node
	 * @param <T>       the generic type of the state
	 * @return this window root element for chaining
	 */
	public <T extends SuiState> WindowRootElement content(final Class<T> stateType, final SuiComponentRenderer<T> renderer) {
		this.content = renderer;
		return this;
	}




	/**
	 * @param stateType the expected type of the state
	 * @param condition the condition that must be true for the window to show
	 * @param <T>       the generic type of the state
	 * @return this window root element for chaining
	 */
	public <T extends SuiState> WindowRootElement condition(final Class<T> stateType, final Predicate<T> condition) {
		//noinspection unchecked
		this.condition = (Predicate<SuiState>) condition;
		return this;
	}




	/**
	 * Adds the given child window to this window
	 *
	 * @param windowRootElement windows of this window that can be opened
	 * @return this window root element for chaining
	 */
	public WindowRootElement modal(final WindowRootElement windowRootElement) {
		// validate: windowRootElement must be unique
		modalWindowRootElements.add(windowRootElement);
		return this;
	}

}
