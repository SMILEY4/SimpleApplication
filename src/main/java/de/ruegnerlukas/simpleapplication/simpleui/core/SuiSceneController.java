package de.ruegnerlukas.simpleapplication.simpleui.core;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.WindowRootElement;
import de.ruegnerlukas.simpleapplication.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateUpdate;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;

public class SuiSceneController implements SuiStateListener {


    private final SuiState state;

    @Getter
    private final WindowRootElement windowRootElement;

    private final SuiComponent<?> rootComponent;

    private SuiSceneTree sceneTree;

    private final SuiSceneController parentController;

    private final List<SuiSceneController> childControllers = new ArrayList<>();


    public SuiSceneController(final WindowRootElement windowRootElement) {
        this(new SuiState(), windowRootElement, null);
    }


    public SuiSceneController(final SuiState state, final WindowRootElement windowRootElement) {
        this(state, windowRootElement, null);
    }


    public SuiSceneController(final SuiState state, final WindowRootElement windowRootElement, final SuiSceneController parent) {
        // todo: validate window root element
        this.state = state;
        this.state.addStateListener(this);
        this.windowRootElement = windowRootElement;
        this.rootComponent = new SuiComponent<>(windowRootElement.getNodeFactoryBuilder());
        this.parentController = parent;
    }


    public SuiNode getRootNode() {
        return sceneTree == null ? null : sceneTree.getRoot();
    }


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
        stage.setWidth(windowRootElement.getWidth().doubleValue());
        stage.setHeight(windowRootElement.getHeight().doubleValue());
        stage.setOnCloseRequest(e -> {
            if (parentController != null) {
                parentController.onChildClosed(this);
            }
            close(true);
        });

        if (windowRootElement.isWait()) {
            stage.showAndWait();
        } else {
            stage.show();
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


    protected void onChildClosed(final SuiSceneController childController) {
        childControllers.remove(childController);
    }


    public void close() {
        close(false);
    }


    private void close(final boolean alreadyClosed) {
        if (!alreadyClosed) {
            windowRootElement.getStage().close();
        }
        dispose();
        if (getWindowRootElement().getOnClose() != null) {
            getWindowRootElement().getOnClose().accept(state);
        }
    }


    public void dispose() {
        this.state.removeStateListener(this);
    }

}
