package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.utils.FxTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SUIScrollPaneTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	@Test
	public void testScrollPaneSNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(),
				Properties.fitToHeight(),
				Properties.style("-fx-background-color: red"),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				Properties.defaultMutationBehaviour(),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Scroll Content")
						)
				)
		);

		final SUINode node = scrollPane.create(state);

		TestUtils.assertNode(node, SUIScrollPane.class);
		PropertyTestUtils.assertIdProperty(node, "myScrollPane");
		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(node, true);
		PropertyTestUtils.assertFitToWidthProperty(node, true);
		PropertyTestUtils.assertFitToHeightProperty(node, true);
		PropertyTestUtils.assertShowScrollBarProperty(node, ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS);
		PropertyTestUtils.assertStyle(node, "-fx-background-color: red");

		final List<SUINode> children = node.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn");
		PropertyTestUtils.assertTextContentProperty(childButton, "Scroll Content");
	}




	@Test
	public void testMutate() {

		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(),
				Properties.fitToHeight(),
				Properties.style("-fx-background-color: red"),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Scroll Content")
						)
				)
		);

		NodeFactory scrollPaneTarget = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(500.0, 600.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(false),
				Properties.fitToHeight(false),
				Properties.style("-fx-background-color: blue"),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("New Scroll Content")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, scrollPane);
		SUINode original = context.getRootNode();
		SUINode target = scrollPaneTarget.create(state);

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SUIScrollPane.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myScrollPane");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 500.0, 600.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
		PropertyTestUtils.assertFitToWidthProperty(mutatedNode, false);
		PropertyTestUtils.assertFitToHeightProperty(mutatedNode, false);
		PropertyTestUtils.assertShowScrollBarProperty(mutatedNode, ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED);
		PropertyTestUtils.assertStyle(mutatedNode, "-fx-background-color: blue");

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn");
		PropertyTestUtils.assertTextContentProperty(childButton, "New Scroll Content");

	}




	@Test
	public void testMutateRemoveContent() {

		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(),
				Properties.fitToHeight(),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Scroll Content")
						)
				)
		);

		NodeFactory scrollPaneTarget = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(500.0, 600.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(false),
				Properties.fitToHeight(false),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED)
		);

		SUISceneContext context = new SUISceneContext(state, scrollPane);
		SUINode original = context.getRootNode();
		SUINode target = scrollPaneTarget.create(state);

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SUIScrollPane.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myScrollPane");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 500.0, 600.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
		PropertyTestUtils.assertFitToWidthProperty(mutatedNode, false);
		PropertyTestUtils.assertFitToHeightProperty(mutatedNode, false);
		PropertyTestUtils.assertShowScrollBarProperty(mutatedNode, ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(0);

	}



	@Test
	public void testMutateAddContent() {

		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(),
				Properties.fitToHeight(),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS)
		);

		NodeFactory scrollPaneTarget = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(500.0, 600.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(false),
				Properties.fitToHeight(false),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Scroll Content")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, scrollPane);
		SUINode original = context.getRootNode();
		SUINode target = scrollPaneTarget.create(state);

		SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SUIScrollPane.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "myScrollPane");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 500.0, 600.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
		PropertyTestUtils.assertFitToWidthProperty(mutatedNode, false);
		PropertyTestUtils.assertFitToHeightProperty(mutatedNode, false);
		PropertyTestUtils.assertShowScrollBarProperty(mutatedNode, ScrollPane.ScrollBarPolicy.ALWAYS, ScrollPane.ScrollBarPolicy.AS_NEEDED);

		final List<SUINode> children = mutatedNode.getChildrenUnmodifiable();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "btn");
		PropertyTestUtils.assertTextContentProperty(childButton, "Scroll Content");
	}


	@Test
	public void testFxNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory scrollPane = SUIScrollPane.scrollPane(
				Properties.id("myScrollPane"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.fitToWidth(),
				Properties.fitToHeight(),
				Properties.style("-fx-background-color: red"),
				Properties.showScrollbars(ScrollPane.ScrollBarPolicy.NEVER, ScrollPane.ScrollBarPolicy.ALWAYS),
				Properties.item(
						SUIButton.button(
								Properties.id("btn"),
								Properties.textContent("Scroll Content")
						)
				)
		);

		SUISceneContext context = new SUISceneContext(state, scrollPane);
		SUINode node = context.getRootNode();

		FxTestUtils.assertScrollPane((ScrollPane) node.getFxNode(), FxTestUtils.ScrollPaneInfo.builder()
				.minWidth(1.0).minHeight(2.0)
				.prefWidth(3.0).prefHeight(4.0)
				.maxWidth(5.0).maxHeight(6.0)
				.disabled(true)
				.fitToWidth(true)
				.fitToHeight(true)
				.horzBar(ScrollPane.ScrollBarPolicy.NEVER)
				.vertBar(ScrollPane.ScrollBarPolicy.ALWAYS)
				.contentButtonText("Scroll Content")
				.style("-fx-background-color: red")
				.build());
	}

}
