package simpleui;


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simpleui.core.SElement;

public class SUITest extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(final Stage stage) {

		SElement element = new TestComponentA();
		element.print(0);

		Node rootNode = element.getFxNode();
		Scene scene = new Scene((Parent) rootNode, 500, 400);

		stage.setScene(scene);
		stage.show();
	}


}
