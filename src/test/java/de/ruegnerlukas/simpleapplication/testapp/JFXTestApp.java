package de.ruegnerlukas.simpleapplication.testapp;


import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SUIState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.events.EventProperties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton.button;
import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIContainer.container;
import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIHBox.hbox;
import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIScrollPane.scrollPane;
import static de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox.vbox;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.fitToWidth;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.id;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.item;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.items;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.layout;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.maxSize;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.preferredSize;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.showScrollbars;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.spacing;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.staticNode;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.Properties.textContent;

@SuppressWarnings ("ALL")
public class JFXTestApp extends Application {


	public static void main(String[] args) {
		launch(args);
	}




	@Override
	public void start(final Stage stage) throws Exception {

		SUIRegistry.initialize();

		TestState testUIState = new TestState();

		SUISceneContext context = new SUISceneContext(testUIState, TestState.class, state -> customLayout());

		Scene scene = new Scene((Parent) context.getRootFxNode(), 500, 600);
		stage.setScene(scene);
		stage.show();
	}




	private NodeFactory customLayout() {
		return scrollPane(
				id("scrollPane"),
				fitToWidth(),
				showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				staticNode(),
				item(
						vbox(
								id("vbox"),
								spacing(5.0),
								items(() -> {
									final List<NodeFactory> factories = new ArrayList<>();
									for (int i = 0; i < 100; i++) {
										factories.add(
												container(
														id("item" + i),
														maxSize(100000.0, 100.0),
														EventProperties.eventMouseEntered(e -> System.out.println("mouse enter")),
														layout("layout", ((parent, nodes) -> {
															final double width = parent.getWidth();
															final double height = parent.getHeight();
															nodes.get(0).resizeRelocate(0, 0, width / 2, height);
															nodes.get(1).resizeRelocate(width / 2, 0, width / 2, height / 2);
															nodes.get(2).resizeRelocate(width / 2, height / 2, width / 2, height / 2);
														})),
														items(
																button(
																		id("btnLeft"),
																		textContent("Left"),
																		EventProperties.eventAction(e -> System.out.println("left pressed"))
																		),
																button(
																		id("btnTopRight"),
																		textContent("Top Right")
																),
																button(
																		id("btnBtmRight"),
																		textContent("Bottom Right")
																)
														)
												)
										);
									}
									return factories;
								})
						)
				)
		);
	}




	private NodeFactory vanilla() {
		return scrollPane(
				id("scrollPane"),
				fitToWidth(),
				showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				staticNode(),
				item(
						vbox(
								id("vbox"),
								spacing(5.0),
								items(() -> {
									final List<NodeFactory> factories = new ArrayList<>();
									for (int i = 0; i < 10_000; i++) {
										factories.add(
												hbox(
														id("item" + i),
														maxSize(100000.0, 100.0),
														items(
																button(
																		id("btnLeft"),
																		textContent("Left"),
																		preferredSize(100000.0, 200.0)
																),
																vbox(
																		id("vboxRight"),
																		preferredSize(100000.0, 200.0),
																		items(
																				button(
																						id("btnTopRight"),
																						textContent("Top Right"),
																						preferredSize(100000.0, 100000.0)
																				),
																				button(
																						id("btnBtmRight"),
																						textContent("Bottom Right"),
																						preferredSize(100000.0, 100000.0)
																				)
																		)
																)

														)
												));
									}
									return factories;
								})
						)
				)
		);
	}




	static class TestState extends SUIState {


		public int counter = 2;

	}






	private long lastTime = 0;




	private void measureFPS() {
		AnimationTimer frameRateMeter = new AnimationTimer() {

			@Override
			public void handle(long now) {
				System.out.println(((now - lastTime) / 1000000.0));
				lastTime = now;
			}
		};

		frameRateMeter.start();
	}


}
