package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.function.Consumer;

public class TabAwareTabPane extends TabPane {


	/**
	 * The listener handling closed tabs.
	 */
	private Consumer<Tab> listener;


	/**
	 * Whether the listener should receive any event on closed tabs.
	 */
	private boolean muteListener = false;


	/**
	 * Default constructor.
	 */
	public TabAwareTabPane() {
		getTabs().addListener((ListChangeListener<Tab>) change -> {
			while (change.next()) {
				change.getAddedSubList().forEach(tab -> {
					tab.setOnCloseRequest(e -> onCloseRequest(tab));
					tab.setOnClosed(e -> onClosed(tab));
				});
			}
		});
	}




	/**
	 * Set the event listener that gets called when a tab gets called.
	 *
	 * @param listener the listener handling the closed tab
	 */
	public void setOnTabClosed(final Consumer<Tab> listener) {
		this.listener = listener;
	}




	/**
	 * Mute/Unmute the listener for closed tabs.
	 * @param mute whether the listener should receive any event on closed tabs.
	 */
	public void muteOnTabClosed(final boolean mute) {
		this.muteListener = mute;
	}



	/**
	 * Called on a close-request on the given tab.
	 *
	 * @param tab the tab
	 */
	private void onCloseRequest(final Tab tab) {
		// do nothing for now
	}




	/**
	 * Called when the given tab was closed.
	 *
	 * @param tab the closed tab
	 */
	private void onClosed(final Tab tab) {
		if (listener != null && !muteListener) {
			listener.accept(tab);
		}
		tab.setOnCloseRequest(null);
		tab.setOnClosed(null);
		getSelectionModel().select(0);
	}

}
