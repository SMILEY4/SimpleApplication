package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;

import org.junit.Test;

public class SuiCommonEventPropertiesTest extends SuiElementTest {


	@Test
	public void test_focus_events() {

//		THIS TEST DOES NOT REALLY WORK IN HEADLESS MODE


//		if(shouldSkipFxTest()) {
//			return;
//		}
//
//		final Phaser phaser = new Phaser(2);
//
//		final List<FocusEventData> capturedChangedEvents = new ArrayList<>();
//		final List<FocusEventData> capturedLostEvents = new ArrayList<>();
//		final List<FocusEventData> capturedReceivedEvents = new ArrayList<>();
//
//		final VBox vbox = (VBox) new SuiSceneController(
//				SuiVBox.vbox(
//						Properties.items(
//								SuiButton.button(
//										Properties.textContent("Button 1"),
//										EventProperties.eventAction(".", e -> phaser.arrive()),
//										EventProperties.eventFocusChanged(".", e -> phaser.arrive()),
//										EventProperties.eventFocusLost(".", e -> phaser.arrive()),
//										EventProperties.eventFocusReceived(".", e -> phaser.arrive())
//								),
//								SuiButton.button(
//										Properties.textContent("Button 2"),
//										EventProperties.eventAction(".", e -> phaser.arrive()),
//										EventProperties.eventFocusChanged(".", capturedChangedEvents::add),
//										EventProperties.eventFocusLost(".", capturedLostEvents::add),
//										EventProperties.eventFocusReceived(".", capturedReceivedEvents::add)
//								)
//						)
//				)
//		).getRootFxNode();
//		show(vbox);
//
//		final Button btn1 = (Button) vbox.getChildren().get(0);
//		final Button btn2 = (Button) vbox.getChildren().get(1);
//
//		// focus button 1
//		syncJfxThread(() -> clickOn(btn1, MouseButton.PRIMARY));
//		phaser.arriveAndAwaitAdvance();
//		assertThat(btn1.isFocused()).isTrue();
//		assertThat(btn2.isFocused()).isFalse();
//
//		// focus button 1 -> triggers focus-receive-events
//		syncJfxThread(() -> clickOn(btn2, MouseButton.PRIMARY));
//		phaser.arriveAndAwaitAdvance();
//		assertThat(btn1.isFocused()).isFalse();
//		assertThat(btn2.isFocused()).isTrue();
//
//		assertThat(capturedChangedEvents).hasSize(1);
//		assertThat(capturedChangedEvents.get(0).isFocused()).isTrue();
//		assertThat(capturedReceivedEvents).hasSize(1);
//		assertThat(capturedReceivedEvents.get(0).isFocused()).isTrue();
//		assertThat(capturedLostEvents).hasSize(0);
//
//		capturedChangedEvents.clear();
//		capturedReceivedEvents.clear();
//		capturedLostEvents.clear();
//
//		// focus button 2 -> triggers focus-lost-events
//		syncJfxThread(() -> clickOn(btn1, MouseButton.PRIMARY));
//		phaser.arriveAndAwaitAdvance();
//		assertThat(btn1.isFocused()).isTrue();
//		assertThat(btn2.isFocused()).isFalse();
//
//		assertThat(capturedChangedEvents).hasSize(1);
//		assertThat(capturedChangedEvents.get(0).isFocused()).isFalse();
//		assertThat(capturedReceivedEvents).hasSize(0);
//		assertThat(capturedLostEvents).hasSize(1);
//		assertThat(capturedLostEvents.get(0).isFocused()).isFalse();
	}

}
