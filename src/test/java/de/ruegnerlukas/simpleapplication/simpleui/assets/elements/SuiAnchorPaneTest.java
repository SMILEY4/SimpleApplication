package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiAnchorPaneTest extends SuiElementTest {


	@Test
	public void test_anchor_pane() {

		final AnchorPane anchorPane = buildFxNode(
				state -> SuiElements.anchorPane()
						.items(
								SuiElements.button()
										.id("btn-fit")
										.textContent("Button Fit")
										.anchorsFitParent(),
								SuiElements.button()
										.id("btn-anchored")
										.textContent("Button Anchored")
										.anchors(10, 20, null, null),
								SuiElements.button()
										.id("btn-missing")
										.textContent("Button Missing")

						)
		);


		assertThat(anchorPane.getChildren()).hasSize(3);

		final Button btnFit = (Button) anchorPane.getChildren().get(0);
		assertAnchors(btnFit, 0, 0, 0, 0);

		final Button btnAnchored = (Button) anchorPane.getChildren().get(1);
		assertAnchors(btnAnchored, 10, 20, null, null);

		final Button btnMissing = (Button) anchorPane.getChildren().get(2);
		assertAnchors(btnMissing, null, null, null, null);

	}


}
