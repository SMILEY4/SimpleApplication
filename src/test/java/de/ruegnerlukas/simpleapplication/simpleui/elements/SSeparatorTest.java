package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.FxTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.TestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import javafx.geometry.Orientation;
import javafx.scene.control.Separator;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import static org.assertj.core.api.Assertions.assertThat;

public class SSeparatorTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SimpleUIRegistry.initialize();
	}




	@Test
	public void testSNode() {

		final TestState state = new TestState();

		NodeFactory separator = SSeparator.separator(
				Properties.id("mySeparator"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(false),
				Properties.orientation(Orientation.HORIZONTAL)
		);

		SNode node = separator.create(state);
		assertThat(node.getProperties().keySet())
				.containsExactlyInAnyOrderElementsOf(SimpleUIRegistry.get().getEntry(SSeparator.class).getProperties());

		TestUtils.assertNode(node, SSeparator.class);
		PropertyTestUtils.assertIdProperty(node, "mySeparator");
		PropertyTestUtils.assertSizeMinProperty(node, 1.0, 2.0);
		PropertyTestUtils.assertSizePreferredProperty(node, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(node, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(node, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(node, false);
		PropertyTestUtils.assertOrientation(node, Orientation.HORIZONTAL);
	}




	@Test
	public void testMutate() {

		final TestState state = new TestState();

		NodeFactory separator = SSeparator.separator(
				Properties.id("mySeparator"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(false),
				Properties.orientation(Orientation.HORIZONTAL)
		);

		NodeFactory separatorTarget = SSeparator.separator(
				Properties.id("mySeparator"),
				Properties.minSize(123.0, 234.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(true),
				Properties.orientation(Orientation.VERTICAL)
		);

		SceneContext context = new SceneContext(state, separator, null);
		SNode original = context.getRootNode();
		SNode target = separatorTarget.create(state);

		SNode mutatedNode = context.getMutator().mutate(original, target);
		assertThat(mutatedNode).isEqualTo(original);

		TestUtils.assertNode(mutatedNode, SSeparator.class);
		PropertyTestUtils.assertIdProperty(mutatedNode, "mySeparator");
		PropertyTestUtils.assertSizeMinProperty(mutatedNode, 123.0, 234.0);
		PropertyTestUtils.assertSizePreferredProperty(mutatedNode, 3.0, 4.0);
		PropertyTestUtils.assertSizeMaxProperty(mutatedNode, 5.0, 6.0);
		PropertyTestUtils.assertSizeProperty(mutatedNode, 10.0, 11.0, 12.0, 13.0, 14.0, 15.0);
		PropertyTestUtils.assertDisabledProperty(mutatedNode, true);
		PropertyTestUtils.assertOrientation(mutatedNode, Orientation.VERTICAL);
	}




	@Test
	public void testFxNode() {

		final TestState state = new TestState();

		NodeFactory separator = SSeparator.separator(
				Properties.id("mySeparator"),
				Properties.minSize(1.0, 2.0),
				Properties.preferredSize(3.0, 4.0),
				Properties.maxSize(5.0, 6.0),
				Properties.size(10.0, 11.0, 12.0, 13.0, 14.0, 15.0),
				Properties.disabled(false),
				Properties.orientation(Orientation.HORIZONTAL)
		);

		SceneContext context = new SceneContext(state, separator, null);
		SNode node = context.getRootNode();

		FxTestUtils.assertSeparator((Separator) node.getFxNode(), FxTestUtils.SeparatorInfo.builder()
				.minWidth(1.0).minHeight(2.0)
				.prefWidth(3.0).prefHeight(4.0)
				.maxWidth(5.0).maxHeight(6.0)
				.disabled(false)
				.orientation(Orientation.HORIZONTAL)
				.build());

	}


}
