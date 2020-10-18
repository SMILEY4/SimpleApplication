package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ImageSizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.assertj.core.data.Percentage;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;


public class SuiImageTest extends SuiElementTest {


	@Test
	public void test_absolute() {
		if (shouldSkipFxTest()) {
			return;
		}

		final SuiSceneController controller = buildScene(
				ImageSizeProperty.ImageDimension.absolute(200),
				ImageSizeProperty.ImageDimension.absolute(150));

		final AnchorPane root = (AnchorPane) controller.getRootFxNode();
		final VBox vbox = (VBox) root.getChildren().get(0);
		final ImageView imageView = (ImageView) vbox.getChildren().get(0);
		show(root);

		assertThat(imageView.getFitWidth()).isCloseTo(200, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(150, Percentage.withPercentage(0.01));

		syncJfxThread(200, () -> {
			AnchorPane.setBottomAnchor(vbox, 200.0);
			AnchorPane.setRightAnchor(vbox, 200.0);
		});

		assertThat(imageView.getFitWidth()).isCloseTo(200, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(150, Percentage.withPercentage(0.01));
	}




	@Test
	public void test_relative() {
		if (shouldSkipFxTest()) {
			return;
		}

		final SuiSceneController controller = buildScene(
				ImageSizeProperty.ImageDimension.relative(0.1),
				ImageSizeProperty.ImageDimension.relative(0.1));

		final AnchorPane root = (AnchorPane) controller.getRootFxNode();
		final VBox vbox = (VBox) root.getChildren().get(0);
		final ImageView imageView = (ImageView) vbox.getChildren().get(0);
		show(root);

		assertThat(imageView.getFitWidth()).isCloseTo(192, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(108, Percentage.withPercentage(0.01));

		syncJfxThread(200, () -> {
			AnchorPane.setBottomAnchor(vbox, 200.0);
			AnchorPane.setRightAnchor(vbox, 200.0);
		});

		assertThat(imageView.getFitWidth()).isCloseTo(192, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(108, Percentage.withPercentage(0.01));
	}




	@Test
	public void test_parent_relative() {
		if (shouldSkipFxTest()) {
			return;
		}

		final SuiSceneController controller = buildScene(
				ImageSizeProperty.ImageDimension.parentRelative(0.5),
				ImageSizeProperty.ImageDimension.parentRelative(0.25));

		final AnchorPane root = (AnchorPane) controller.getRootFxNode();
		final VBox vbox = (VBox) root.getChildren().get(0);
		final ImageView imageView = (ImageView) vbox.getChildren().get(0);
		show(root);

		assertThat(imageView.getFitWidth()).isCloseTo(200, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(100, Percentage.withPercentage(0.01));

		syncJfxThread(200, () -> {
			AnchorPane.setBottomAnchor(vbox, 200.0);
			AnchorPane.setRightAnchor(vbox, 200.0);
		});

		assertThat(imageView.getFitWidth()).isCloseTo(100, Percentage.withPercentage(0.01));
		assertThat(imageView.getFitHeight()).isCloseTo(50, Percentage.withPercentage(0.01));
	}




	private SuiSceneController buildScene(ImageSizeProperty.ImageDimension width, ImageSizeProperty.ImageDimension height) {
		return new SuiSceneController(
				SuiElements.anchorPane()
						.sizeMin(400, 400)
						.sizeMax(400, 400)
						.item(
								SuiElements.vBox()
										.anchors(0, 0, 0, 0)
										.item(
												SuiElements.image()
														.image(Resource.internal("testResources/test-image.png"))
														.imageSize(width, height)
										)
								)
		);
	}


}
