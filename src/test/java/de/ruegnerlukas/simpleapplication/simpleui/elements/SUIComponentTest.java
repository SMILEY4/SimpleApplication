package de.ruegnerlukas.simpleapplication.simpleui.elements;

import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
import javafx.stage.Stage;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SUIComponentTest extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	private static class TestSUIComponent extends SUIComponent<ElementTestState> {


		public TestSUIComponent() {
			super(state -> SUIButton.button(
					Properties.id("myButton"),
					Properties.textContent("My Button")
			));
		}


	}




	@Test
	public void testComponentSNode() {

		final ElementTestState state = new ElementTestState();

		NodeFactory vbox = SUIVBox.vbox(
				Properties.id("myVBox"),
				Properties.items(
						new TestSUIComponent()
				)
		);

		final SUINode node = vbox.create(state);

		TestUtils.assertNode(node, SUIVBox.class);
		PropertyTestUtils.assertIdProperty(node, "myVBox");

		final List<SUINode> children = node.getChildren();
		assertThat(children).isNotNull();
		assertThat(children).hasSize(1);
		assertThat(children).doesNotContainNull();

		final SUINode childButton = children.get(0);
		PropertyTestUtils.assertIdProperty(childButton, "myButton");
		PropertyTestUtils.assertTextContentProperty(childButton, "My Button");

	}


}
