package simpleui;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import simpleui.core.factories.SNodeFactory;
import simpleui.core.state.StateManager;


public class SUITest extends Application {


	public static void main(String[] args) {

//		final SNodeFactory sceneFactory = new TestComponentA();
//
//		final SNode rootA = sceneFactory.create(new State(2, 3, "A-", "B-"));
//		final SNode rootB = sceneFactory.create(new State(4, 3, "A-", "B-"));
//
//		System.out.println();
//		System.out.println("=== A ===");
//		rootA.print(0);
//
//		System.out.println();
//		System.out.println("=== B ===");
//		rootB.print(0);
//
//		System.out.println();
//		System.out.println("=== M ===");
//		rootA.mutate(rootB);
//
//		System.out.println();
//		System.out.println("=== A* ===");
//		rootA.print(0);

		launch(args);
	}




	@Override
	public void start(final Stage stage) {

		final SNodeFactory sceneFactory = new TestComponentA();
		final Scene scene = new Scene(new Pane(), 500, 400);
		StateManager.init(sceneFactory, new TestState(), scene);

		stage.setScene(scene);
		stage.show();
	}


}
