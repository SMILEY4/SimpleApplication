//package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;
//
//import de.ruegnerlukas.simpleapplication.simpleui.TestState;
//import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
//import de.ruegnerlukas.simpleapplication.simpleui.core.SuiNode;
//import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
//import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.PropertyTestUtils;
//import de.ruegnerlukas.simpleapplication.simpleui.utils.TestUtils;
//import javafx.stage.Stage;
//import org.junit.Test;
//import org.testfx.framework.junit.ApplicationTest;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//public class SuiComponentTest extends ApplicationTest {
//
//
//	@Override
//	public void start(Stage stage) {
//		SuiRegistry.initialize();
//	}
//
//
//
//
//	private static class TestSuiComponent extends SuiComponent<TestState> {
//
//
//		public TestSuiComponent() {
//			super(state -> SuiButton.button(
//					Properties.id("myButton"),
//					Properties.textContent("My Button")
//			));
//		}
//
//
//	}
//
//
//
//
//	@Test
//	public void testComponentSNode() {
//
//		final TestState state = new TestState();
//
//		NodeFactory vbox = SuiVBox.vbox(
//				Properties.id("myVBox"),
//				Properties.items(
//						new TestSuiComponent()
//				)
//		);
//
//		final SuiNode node = vbox.create(state);
//
//		TestUtils.assertNode(node, SuiVBox.class);
//		PropertyTestUtils.assertIdProperty(node, "myVBox");
//
//		final List<SuiNode> children = node.getChildrenUnmodifiable();
//		assertThat(children).isNotNull();
//		assertThat(children).hasSize(1);
//		assertThat(children).doesNotContainNull();
//
//		final SuiNode childButton = children.get(0);
//		PropertyTestUtils.assertIdProperty(childButton, "myButton");
//		PropertyTestUtils.assertTextContentProperty(childButton, "My Button");
//
//	}
//
//
//}
