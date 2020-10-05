package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiAnchorPaneTest extends SuiElementTest {


	@Test
	public void test_anchor_pane() {

		final AnchorPane anchorPane = (AnchorPane) new SuiSceneController(
				SuiAnchorPane.anchorPane(
						Properties.items(
								SuiButton.button(
										Properties.id("btn-fit"),
										Properties.textContent("Button Fit"),
										Properties.anchorFitParent()
								),
								SuiButton.button(
										Properties.id("btn-anchored"),
										Properties.textContent("Button Anchored"),
										Properties.anchor(10, 20, null, null)
								),
								SuiButton.button(
										Properties.id("btn-missing"),
										Properties.textContent("Button Missing")
								)
						)
				)).getRootFxNode();


		assertThat(anchorPane.getChildren()).hasSize(3);

		final Button btnFit = (Button) anchorPane.getChildren().get(0);
		assertAnchors(btnFit, 0, 0, 0, 0);

		final Button btnAnchored = (Button) anchorPane.getChildren().get(1);
		assertAnchors(btnAnchored, 10, 20, null, null);

		final Button btnMissing = (Button) anchorPane.getChildren().get(2);
		assertAnchors(btnMissing, null, null, null, null);

	}




	private void assertAnchors(final Node node, final Integer top, final Integer bottom, final Integer left, final Integer right) {

		if (top == null) {
			assertThat(AnchorPane.getTopAnchor(node)).isNull();
		} else {
			assertThat(AnchorPane.getTopAnchor(node).intValue()).isEqualTo(top.intValue());
		}

		if (bottom == null) {
			assertThat(AnchorPane.getBottomAnchor(node)).isNull();
		} else {
			assertThat(AnchorPane.getBottomAnchor(node).intValue()).isEqualTo(bottom.intValue());
		}

		if (left == null) {
			assertThat(AnchorPane.getLeftAnchor(node)).isNull();
		} else {
			assertThat(AnchorPane.getLeftAnchor(node).intValue()).isEqualTo(left.intValue());
		}

		if (right == null) {
			assertThat(AnchorPane.getRightAnchor(node)).isNull();
		} else {
			assertThat(AnchorPane.getRightAnchor(node).intValue()).isEqualTo(right.intValue());
		}

	}


}
