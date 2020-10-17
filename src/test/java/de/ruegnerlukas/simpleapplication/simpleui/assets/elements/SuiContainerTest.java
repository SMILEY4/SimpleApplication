package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.PropertyValidation;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import javafx.scene.Node;
import javafx.scene.Parent;
import org.junit.Test;

import java.util.concurrent.Phaser;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiContainerTest extends SuiElementTest {


	@Test
	public void test_custom_layout() {
		if (shouldSkipFxTest()) {
			return;
		}

		final Phaser phaser = new Phaser(2);
		final AtomicInteger counter = new AtomicInteger(0);

		final Node container = new SuiSceneController(
				SuiContainer.container(
						PropertyValidation.layout(".", (parent, nodes) -> {
							counter.incrementAndGet();
							phaser.arrive();
						})
				)
		).getRootFxNode();

		show((Parent) container);
		assertThat(counter.get()).isEqualTo(1);

		syncJfxThread(100, () -> getStage().setWidth(getStage().getWidth() / 2));
		assertThat(counter.get()).isEqualTo(2);

	}


}
