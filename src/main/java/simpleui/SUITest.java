package simpleui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simpleui.core.SComponentMaster;

public class SUITest extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(final Stage stage) {

		Scene scene = new Scene(new Pane(), 500, 400);

		TestComponentA component = new TestComponentA();

		SComponentMaster.instance.initialize(component, scene);
		SComponentMaster.instance.update();

		stage.setScene(scene);
		stage.show();
	}


}
