package simpleui;


import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import simpleui.core.SComponent;
import simpleui.core.SElement;
import simpleui.core.prebuild.SButton;
import simpleui.core.prebuild.SContainer;

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




	static class TestComponentA extends SComponent {


		@Override
		public SElement render() {
			return new SContainer(
					new SButton("Button A-1"),
					new TestComponentB(),
					new SButton("Button A-2")
			);
		}

	}






	static class TestComponentB extends SComponent {


		@Override
		public SElement render() {
			return new SContainer(
					new SButton("Button B-1"),
					new SButton("Button B-2")
			);
		}

	}

}
