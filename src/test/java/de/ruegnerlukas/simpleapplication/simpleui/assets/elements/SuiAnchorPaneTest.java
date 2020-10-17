package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.PropertyValidation;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiAnchorPaneTest extends SuiElementTest {


	@Test
	public void test_anchor_pane() {

		final AnchorPane anchorPane = (AnchorPane) new SuiSceneController(
				SuiAnchorPane.anchorPane(
						PropertyValidation.items(
								SuiButton.button(
										PropertyValidation.id("btn-fit"),
										PropertyValidation.textContent("Button Fit"),
										PropertyValidation.anchorFitParent()
								),
								SuiButton.button(
										PropertyValidation.id("btn-anchored"),
										PropertyValidation.textContent("Button Anchored"),
										PropertyValidation.anchor(10, 20, null, null)
								),
								SuiButton.button(
										PropertyValidation.id("btn-missing"),
										PropertyValidation.textContent("Button Missing")
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



}
