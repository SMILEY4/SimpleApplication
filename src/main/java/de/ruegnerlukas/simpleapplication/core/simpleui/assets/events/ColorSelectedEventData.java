package de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;

import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@AllArgsConstructor
@ToString
public class ColorSelectedEventData {


	/**
	 * The now selected color.
	 */
	private final Color color;

	/**
	 * The original javafx event.
	 */
	private final ActionEvent source;

}
