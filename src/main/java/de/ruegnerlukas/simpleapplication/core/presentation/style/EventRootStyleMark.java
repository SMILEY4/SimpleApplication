package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.events.Channel;
import de.ruegnerlukas.simpleapplication.core.events.Publishable;
import lombok.Builder;
import lombok.Getter;


/**
 * The event when marking or un-marking a style as root-style.
 */
@Getter
@Builder
public class EventRootStyleMark extends Publishable {


	/**
	 * The name of the registered style.
	 */
	private final String style;

	/**
	 * Whether the style is marked as root-style.
	 */
	private final boolean rootStyle;




	/**
	 * @param style     the name of the registered style
	 * @param rootStyle whether the style is marked as root-style
	 */
	public EventRootStyleMark(final String style, final boolean rootStyle) {
		super(Channel.type(EventRootStyleMark.class));
		this.style = style;
		this.rootStyle = rootStyle;
	}

}
