package de.ruegnerlukas.simpleapplication.core.simpleui.core;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.WindowRootElement;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiStateListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiStateUpdate;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.Tags;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SuiSceneController implements SuiStateListener {


	/**
	 * The ui state
	 */
	private final SuiState state;

	/**
	 * The window root element
	 */
	@Getter
	private final WindowRootElement windowRootElement;

	/**
	 * The root node
	 */
	private final SuiComponent<?> rootComponent;

	/**
	 * The current scene tree
	 */
	private SuiSceneTree sceneTree;

	/**
	 * the parent controller (or null)
	 */
	private final SuiSceneController parentController;

	/**
	 * The currently open child controllers
	 */
	private final List<SuiSceneController> childControllers = new ArrayList<>();




	/**
	 * @param windowRootElement the window root element
	 */
	public SuiSceneController(final WindowRootElement windowRootElement) {
		this(new SuiState(), windowRootElement, null);
	}




	/**
	 * @param state             the state to use with this controller
	 * @param windowRootElement the window root element
	 */
	public SuiSceneController(final SuiState state, final WindowRootElement windowRootElement) {
		this(state, windowRootElement, null);
	}




	/**
	 * @param state             the state to use with this controller
	 * @param windowRootElement the window root element
	 * @param parent            the parent controller
	 */
	public SuiSceneController(final SuiState state, final WindowRootElement windowRootElement, final SuiSceneController parent) {
		Validations.INPUT.notNull(state).exception("The state may not be null.");
		Validations.INPUT.notNull(windowRootElement).exception("The window root element may not be null.");
		this.state = state;
		this.state.addStateListener(this);
		this.windowRootElement = windowRootElement;
		this.rootComponent = new SuiComponent<>(windowRootElement.getContent());
		this.parentController = parent;
	}




	/**
	 * @return the simpleui root node of this controller
	 */
	public SuiNode getRootNode() {
		return sceneTree == null ? null : sceneTree.getRoot();
	}




	/**
	 * Opens the window
	 */
	public void show() {

		if (sceneTree == null) {
			sceneTree = SuiSceneTree.build(rootComponent, state, null);
			sceneTree.buildFxNodes();
		}
		final Node fxNode = sceneTree.getRoot().getFxNodeStore().get();
		final Scene scene = new Scene((Parent) fxNode);

		Stage stage = windowRootElement.getStage();
		if (stage == null) {
			stage = new Stage();
			if (parentController != null) {
				stage.initOwner(parentController.getWindowRootElement().getStage());
			}
			stage.initModality(Modality.WINDOW_MODAL);
			windowRootElement.setStage(stage);
		}

		stage.setScene(scene);
		stage.setTitle(windowRootElement.getTitle());
		if (windowRootElement.getSize() != null) {
			stage.setWidth(windowRootElement.getSize().getWidth());
			stage.setHeight(windowRootElement.getSize().getHeight());
		}
		if (windowRootElement.getSizeMin() != null) {
			stage.setMinWidth(windowRootElement.getSizeMin().getWidth());
			stage.setMinHeight(windowRootElement.getSizeMin().getHeight());
		}
		if (windowRootElement.getSizeMax() != null) {
			stage.setMaxWidth(windowRootElement.getSizeMax().getWidth());
			stage.setMaxHeight(windowRootElement.getSizeMax().getHeight());
		}
		setIcon(stage, windowRootElement.getIcon());
		stage.setOnCloseRequest(e -> {
			if (parentController != null) {
				parentController.onChildClosed(this);
			}
			close(true);
		});

		if (windowRootElement.getWindowBaseStyle() != null) {
			SuiRegistry.get().getStyleManager().applyStyle(stage, windowRootElement.getWindowBaseStyle());
		}

		if (windowRootElement.isWait()) {
			stage.showAndWait();
		} else {
			stage.show();
		}

	}




	/**
	 * Sets the icon of the given stage. If the icon is null, the icon will not be set.
	 *
	 * @param stage the stage
	 * @param icon  the new icon
	 */
	private void setIcon(final Stage stage, final Resource icon) {
		if (icon != null) {
			if (icon.isInternal()) {
				InputStream inputStream = icon.asInputStream();
				Validations.STATE.notNull(inputStream)
						.exception("The internal icon resource does not exist '{}'.", icon.getPath())
						.onSuccess(() -> stage.getIcons().setAll(new Image(inputStream)));
			} else {
				Validations.STATE.exists(new File(icon.getPath()))
						.exception("The external icon resource does not exist '{}'.", icon.getPath())
						.onSuccess(() -> stage.getIcons().setAll(new Image("file:" + icon.getPath())));
			}
		}
	}




	@Override
	public void stateUpdated(final SuiState state, final SuiStateUpdate<?> update, final Tags tags) {
		final SuiSceneTree targetTree = SuiSceneTree.build(rootComponent, state, tags);
		SuiProfiler.get().countSceneMutated();
		sceneTree.mutate(targetTree, tags);

		windowRootElement.getModalWindowRootElements().forEach(popupWindowRootElement -> {
			final Optional<SuiSceneController> openController = childControllers.stream()
					.filter(c -> c.getWindowRootElement().equals(popupWindowRootElement))
					.findAny();
			final boolean isCurrentlyOpen = openController.isPresent();
			final boolean shouldBeOpen = popupWindowRootElement.getCondition().test(state);
			if (shouldBeOpen && !isCurrentlyOpen) {
				final SuiSceneController popupController = new SuiSceneController(this.state, popupWindowRootElement, this);
				this.childControllers.add(popupController);
				popupController.show();
			}
			if (!shouldBeOpen && isCurrentlyOpen) {
				openController.get().close();
				childControllers.remove(openController.get());

			}
		});
	}




	/**
	 * Called when a child controller of this controller was closed
	 *
	 * @param childController the closed controller
	 */
	protected void onChildClosed(final SuiSceneController childController) {
		childControllers.remove(childController);
	}




	/**
	 * Closes the window / this controller
	 */
	public void close() {
		close(false);
	}




	/**
	 * Closes the window and/or this controller
	 *
	 * @param alreadyClosed whether the window was already closed (if so, only the controller has to be disposed)
	 */
	private void close(final boolean alreadyClosed) {
		if (!alreadyClosed) {
			windowRootElement.getStage().close();
		}
		dispose();
		if (getWindowRootElement().getOnClose() != null) {
			getWindowRootElement().getOnClose().accept(state);
		}
	}




	/**
	 * Cleans up this controller
	 */
	public void dispose() {
		this.state.removeStateListener(this);
	}

}
