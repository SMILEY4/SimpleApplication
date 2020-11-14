package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class KeyEventData {


	/**
	 * The pressed key.
	 */
	private final KeyCode keyCode;

	/**
	 * The pressed unicode character or character sequence.
	 */
	private String character;

	/**
	 * Whether or not the Alt modifier is down on this event.
	 */
	private final boolean altDown;

	/**
	 * Whether or not the Control modifier is down on this event.
	 */
	private final boolean ctrlDown;

	/**
	 * Whether or not the Meta modifier is down on this event.
	 */
	private final boolean metaDown;

	/**
	 * Whether or not the Shift modifier is down on this event.
	 */
	private final boolean shiftDown;

	/**
	 * Whether or not the host platform common shortcut modifier is down on this event.
	 * For Example "ctrl" on windows and "meta" on macOS.
	 */
	private final boolean shortcutDown;

	/**
	 * The original javafx event.
	 */
	private final KeyEvent source;

}
