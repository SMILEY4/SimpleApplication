package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.TestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ComponentTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SimpleUIRegistry.initialize();
	}




	private static class TestComponent extends Component<TestState> {


		@Override
		public NodeFactory render(final TestState state) {
			return SButton.button(
					Properties.id("myButton"),
					Properties.textContent("My Button")
			);
		}

	}




	@Test
	public void testComponentSNode() {

		final TestState state = new TestState();

		NodeFactory vbox = SVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						new TestComponent()
				)
		);

		final SNode node = vbox.create(state);

		TestUtils.assertNode(node, SVBox.class);
		PropertyTestUtils.assertIdProperty(node, "myVBox");

		final List<SNode> children = node.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SNode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "myButton");
		PropertyTestUtils.assertTextContentProperty(childButton, "My Button");

	}


}
