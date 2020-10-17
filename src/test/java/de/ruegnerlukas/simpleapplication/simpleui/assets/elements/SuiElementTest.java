package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.events.CheckedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.DateSelectedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.SectionEventData;
import de.ruegnerlukas.simpleapplication.simpleui.assets.events.ValueChangedEventData;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Labeled;
import javafx.scene.control.Slider;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.Getter;
import org.junit.After;
import org.testfx.framework.junit.ApplicationTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.concurrent.Phaser;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiElementTest extends ApplicationTest {


	@Getter
	private Stage stage;




	@Override
	public void start(Stage stage) {
		SuiRegistry.initialize();
		this.stage = stage;
		this.stage.show();
	}




	@After
	public void cleanup() {
		syncJfxThread(() -> {
			getStage().setScene(null);
			getStage().hide();
		});
		delay(100);
	}




	public void show(final Parent node) {
		syncJfxThread(() -> {
			getStage().setScene(new Scene(node));
			getStage().setAlwaysOnTop(true);
			getStage().show();
			getStage().sizeToScene();
		});
		delay(100);
	}




	public void syncJfxThread(final Runnable action) {
		syncJfxThread(0, action);
	}




	public void syncJfxThread(final long postDelayMs, final Runnable action) {
		Phaser phaser = new Phaser(2);
		Platform.runLater(() -> {
			action.run();
			phaser.arrive();
		});
		phaser.arriveAndAwaitAdvance();
		delay(postDelayMs);
	}




	public void delay(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




	public boolean shouldSkipFxTest() {
		return Boolean.getBoolean("skipFxTests");
	}




	public void expandOrCollapse(final Accordion accordion, final int sectionIndex) {
		syncJfxThread(100, () -> clickOn(accordion.getPanes().get(sectionIndex), MouseButton.PRIMARY));
	}




	public void assertSections(final Accordion accordion, final String... sectionTitles) {
		assertThat(
				accordion.getPanes().stream()
						.map(Labeled::getText)
						.collect(Collectors.toList())
		).containsExactly(sectionTitles);
	}




	public void assertEvent(final List<SectionEventData> events, final String title, final boolean expanded) {
		assertThat(events).hasSize(1);
		SectionEventData event = events.remove(0);
		assertThat(event.getSectionTitle()).isEqualTo(title);
		assertThat(event.isExpanded()).isEqualTo(expanded);
	}




	public void assertEvent(final List<CheckedEventData> events, final boolean isChecked) {
		assertThat(events).hasSize(1);
		CheckedEventData event = events.remove(0);
		assertThat(event.isChecked()).isEqualTo(isChecked);
	}




	public <T> void assertEvent(final List<ValueChangedEventData<T>> events, final T prev, final T next) {
		assertThat(events).hasSize(1);
		ValueChangedEventData<T> event = events.remove(0);
		assertThat(event.getPrevValue()).isEqualTo(prev);
		assertThat(event.getValue()).isEqualTo(next);
	}




	public void assertEvent(final List<DateSelectedEventData> events, final int year, final Month month, final int day) {
		assertThat(events).hasSize(1);
		final DateSelectedEventData event = events.remove(0);
		assertThat(event.getDate()).isEqualTo(LocalDate.of(year, month, day));
	}




	public void assertNoEvent(final List<?> events) {
		assertThat(events).isEmpty();
	}




	public void assertExpanded(final Accordion accordion, final String title) {
		if (title != null) {
			assertThat(accordion.getExpandedPane()).isNotNull();
			assertThat(accordion.getExpandedPane().getText()).isEqualTo(title);
		} else {
			assertThat(accordion.getExpandedPane()).isNull();
		}
	}




	public void assertAnchors(final Node node, final Integer top, final Integer bottom, final Integer left, final Integer right) {

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




	public <T> void assertItems(final ChoiceBox<T> choiceBox, final List<T> items) {
		assertThat(choiceBox.getItems()).hasSize(items.size());
		for (int i = 0; i < items.size(); i++) {
			assertThat(choiceBox.getItems().get(i)).isEqualTo(items.get(i));
		}
	}




	public <T> void assertItems(final ComboBox<T> comboBox, final List<T> items) {
		assertThat(comboBox.getItems()).hasSize(items.size());
		for (int i = 0; i < items.size(); i++) {
			assertThat(comboBox.getItems().get(i)).isEqualTo(items.get(i));
		}
	}




	public <T> void assertSelected(final ChoiceBox<T> choiceBox, final T selectedItem) {
		assertThat(choiceBox.getValue()).isEqualTo(selectedItem);
	}




	public <T> void assertSelected(final ComboBox<T> comboBox, final T selectedItem) {
		assertThat(comboBox.getValue()).isEqualTo(selectedItem);
	}




	public void clickButton(final ButtonBase button) {
		syncJfxThread(100, () -> clickOn(button, MouseButton.PRIMARY));
	}




	public void selectItem(final ChoiceBox<?> choiceBox, final int index) {
		syncJfxThread(200, () -> this.clickOn(choiceBox, MouseButton.PRIMARY));
		for (int i = 0; i < index; i++) {
			syncJfxThread(100, () -> type(KeyCode.DOWN));
		}
		syncJfxThread(100, () -> type(KeyCode.ENTER));
	}




	public void selectItem(final ComboBox<?> comboBox, final int index) {
		selectItem(comboBox, false, index);
	}




	public void selectItem(final ComboBox<?> comboBox, final boolean editable, final int index) {
		if (editable) {
			syncJfxThread(200, () -> clickOnArrowButton(comboBox));
		} else {
			syncJfxThread(200, () -> clickOn(comboBox, MouseButton.PRIMARY));
		}
		for (int i = 0; i < (index + 1); i++) {
			syncJfxThread(100, () -> type(KeyCode.DOWN));
		}
		syncJfxThread(100, () -> type(KeyCode.ENTER));
	}




	public void selectItemEscape(final ComboBox<?> comboBox) {
		syncJfxThread(200, () -> this.clickOn(comboBox, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.ESCAPE));
	}




	public void selectItemTyped(final ComboBox<?> comboBox, final KeyCode... keys) {
		syncJfxThread(200, () -> this.clickOn(comboBox, MouseButton.PRIMARY));
		for (KeyCode key : keys) {
			syncJfxThread(100, () -> type(key));
		}
		syncJfxThread(100, () -> type(KeyCode.ENTER));
	}




	public void clickOnArrowButton(final ComboBox<?> comboBox) {
		for (Node child : comboBox.getChildrenUnmodifiable()) {
			if (child.getStyleClass().contains("arrow-button")) {
				Node arrowRegion = ((Pane) child).getChildren().get(0);
				clickOn(arrowRegion);
			}
		}
	}




	public void assertDate(final DatePicker datePicker, final int year, final Month month, final int day) {
		assertThat(datePicker.getValue()).isEqualTo(LocalDate.of(year, month, day));
	}




	public void enterDateTyping(final DatePicker datePicker, final int year, final Month month, final int day) {

		syncJfxThread(100, () -> clickOn(datePicker, MouseButton.PRIMARY));
		syncJfxThread(100, () -> type(KeyCode.BACK_SPACE, 10));

		for (char c : String.valueOf(day).toCharArray()) {
			syncJfxThread(50, () -> type(KeyCode.getKeyCode(String.valueOf(c))));
		}
		syncJfxThread(50, () -> type(KeyCode.PERIOD));
		for (char c : String.valueOf(month.getValue()).toCharArray()) {
			syncJfxThread(50, () -> type(KeyCode.getKeyCode(String.valueOf(c))));
		}
		syncJfxThread(50, () -> type(KeyCode.PERIOD));
		for (char c : String.valueOf(year).toCharArray()) {
			syncJfxThread(50, () -> type(KeyCode.getKeyCode(String.valueOf(c))));
		}
		syncJfxThread(50, () -> type(KeyCode.ENTER));
	}




	public void assertSlider(final Slider slider, final int min, final int max, final int value) {
		assertThat((int) slider.getMin()).isEqualTo(min);
		assertThat((int) slider.getMax()).isEqualTo(max);
		assertThat((int) slider.getValue()).isEqualTo(value);
	}


}
