package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateListener;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiStateUpdate;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;

@SuppressWarnings ("CheckStyle")
@Slf4j
public class SUITestApplicationWiki extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(final Stage stage) {

		SuiRegistry.initialize();

		MyState uiState = new MyState();

//		SuiSceneController suiSceneController = new SuiSceneController(uiState, MyState.class, state ->
//				SuiVBox.vbox(
//						Properties.spacing(5),
//						Properties.alignment(Pos.CENTER),
//						Properties.items(
//								new MyComponent("Button 1"),
//								new MyComponent("Button 2"),
//								new MyComponent("Button 3")
//						)
//				)
//		);


		SuiSceneController suiSceneController = new SuiSceneController(uiState, MyState.class, state ->
				SuiVBox.vbox(
						Properties.spacing(5),
						Properties.alignment(Pos.CENTER),
						Properties.items(
								myComponent(state, "Button 1"),
								myComponent(state, "Button 2"),
								myComponent(state, "Button 3")
						)
				)
		);

		stage.setScene(new Scene((Parent) suiSceneController.getRootFxNode(), 200, 100));
		stage.show();
	}




	private NodeFactory myComponent(MyState state, String text) {

		state.addStateListener(new SuiStateListener() {
			@Override
			public void beforeUpdate(final SuiState state, final SuiStateUpdate<?> update) {
			}
			@Override
			public void stateUpdated(final SuiState state, final SuiStateUpdate<?> update, final Tags tags) {
			}
		});

		return SuiButton.button(
				Properties.textContent(text + " (" + state.value + ")"),
				EventProperties.eventAction(e -> state.update(MyState.class, s -> s.value++))
		);
	}




	static class MyComponent extends SuiComponent<MyState> {


		public MyComponent(String text) {
			super(state -> SuiButton.button(
					Properties.textContent(text + " (" + state.value + ")"),
					EventProperties.eventAction(e -> state.update(MyState.class, s -> s.value++))
			));
		}

	}






	static class MyState extends SuiState {


		public int value = 0;


	}


}
