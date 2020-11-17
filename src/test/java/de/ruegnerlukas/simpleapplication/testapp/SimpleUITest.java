//package de.ruegnerlukas.simpleapplication.testapp;
//
//import de.ruegnerlukas.simpleapplication.core.simpleui.utils.SNodeTreePrinter;
//import de.ruegnerlukas.simpleapplication.core.simpleui.SceneContext;
//import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SimpleUIRegistry;
//import de.ruegnerlukas.simpleapplication.core.simpleui.State;
//import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
//import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.Component;
//import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.injection.InjectionContext;
//import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.InjectionPoint;
//import javafx.application.Application;
//import javafx.geometry.Orientation;
//import javafx.geometry.Pos;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.ScrollPane;
//import javafx.stage.Stage;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SButton.button;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SScrollPane.scrollPane;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SSeparator.separator;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.SVBox.vbox;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.alignment;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.buttonListener;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.fitToWidth;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.item;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.items;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.maxSize;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.showScrollbars;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.spacing;
//import static de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.Properties.textContent;
//
//
//public class SimpleUITest extends Application {
//
//
//	public static void main(String[] args) {
//		launch(args);
//	}
//
//
//
//
//	/*
//	TODO: IDEA: Component-linked-state
//	-> register a section for the state linked to a component
//	-> if that section changes, only the subtree with that component as root has to be updated
//	 */
//
//
//	public static Scene scene;
//
//
//
//
//	@Override
//	public void start(final Stage stage) {
//
//		SimpleUIRegistry.initialize();
//
//		InjectionContext.inject("vboxItems", button(textContent("injected 1")));
//		InjectionContext.inject("vboxItems", button(textContent("injected 2")));
//		InjectionContext.inject("vboxItems", button(textContent("injected 3")));
//
//		NodeFactory rootFactory = new TestComponentA();
//
//		SceneContext context = new SceneContext(new TestState(), rootFactory, fxNode -> scene.setRoot((Parent) fxNode));
//		SNodeTreePrinter.print(context.getRootNode());
//
//		scene = new Scene((Parent) context.getRootNode().getFxNode(), 500, 400);
//		stage.setScene(scene);
//		stage.show();
//	}
//
//
//
//
//	static class TestComponentA extends Component<TestState> {
//
//
//		@Override
//		public NodeFactory render(final TestState state) {
//
//			return scrollPane(
//					fitToWidth(),
//					showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
//					item(new TestComponentB())
//			);
//		}
//
//	}
//
//
//
//
//
//
//	static class TestComponentB extends Component<TestState> {
//
//
//		@Override
//		public NodeFactory render(final TestState state) {
//			return vbox(
//					spacing(10),
//					alignment(Pos.TOP_CENTER),
//					items(
//							() -> {
//								List<NodeFactory> list = new ArrayList<>();
//								list.add(button(
//										textContent("-= ADD =-"),
//										maxSize(100000.0, 100000.0),
//										buttonListener(() -> onAdd(state))));
//								for (int i = 0; i < state.counter; i++) {
//									list.add(button(
//											textContent("btn " + i),
//											maxSize(100000.0, 100000.0)
//									));
//								}
//								list.add(separator(Orientation.HORIZONTAL));
//								list.add(
//										vbox(
//												spacing(10),
//												alignment(Pos.TOP_CENTER),
//												new InjectionPoint("vboxItems")
//										)
//								);
//								return list;
//							}
//					)
//			);
//
//		}
//
//
//
//
//		void onAdd(final TestState state) {
//			state.update(s -> {
//				state.counter++;
//			});
//		}
//
//
//	}
//
//
//
//
//
//
//	static class TestState extends State {
//
//
//		public int counter = 2;
//
//	}
//
//
//}
