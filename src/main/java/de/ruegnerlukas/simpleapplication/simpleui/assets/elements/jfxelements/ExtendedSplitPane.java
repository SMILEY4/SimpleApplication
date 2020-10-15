package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiListChangeListener;
import de.ruegnerlukas.simpleapplication.simpleui.utils.MutableBiConsumer;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.SplitPane;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExtendedSplitPane extends SplitPane {


	/**
	 * A map of the dividers with their position change listeners.
	 */
	private final Map<Divider, ChangeListener<Number>> internalDividerPosListeners = new HashMap<>();

	/**
	 * The divider listener ( <index,<prevPos,nextPos>> ).
	 */
	private final MutableBiConsumer<Integer, Pair<Number, Number>> dividerListener = new MutableBiConsumer<>();

	/**
	 * Whether the user can change the positions of the dividers
	 */
	@Getter
	@Setter
	private boolean fixedDividers;




	/**
	 * Default constructor
	 */
	public ExtendedSplitPane() {
		getDividers().addListener(new SuiListChangeListener<>(
				addedDiv -> {
					dividerListener.runMuted(() -> {
						final ChangeListener<Number> listener = (v, p, n) -> onDividerMove(addedDiv, p, n);
						internalDividerPosListeners.put(addedDiv, listener);
						addedDiv.positionProperty().addListener(listener);
					});
				},
				removedDiv -> removedDiv.positionProperty().removeListener(internalDividerPosListeners.get(removedDiv))
		));
	}




	/**
	 * Sets the given divider listener.
	 *
	 * @param listener the position listener or null.
	 *                 Gets called with the index of the divider and a pair of the previous and next position.
	 */
	public void setDividerListener(final BiConsumer<Integer, Pair<Number, Number>> listener) {
		dividerListener.setConsumer(listener);
	}




	/**
	 * Called when the position of the given divider changes.
	 *
	 * @param divider the divider
	 * @param prevPos the previous position of the divider
	 * @param nextPos the new position of the divider
	 */
	private void onDividerMove(final Divider divider, final Number prevPos, final Number nextPos) {
		if (fixedDividers) {
			divider.positionProperty().removeListener(internalDividerPosListeners.get(divider));
			divider.setPosition(prevPos.doubleValue());
			divider.positionProperty().addListener(internalDividerPosListeners.get(divider));
		} else {
			dividerListener.accept(getDividers().indexOf(divider), Pair.of(prevPos, nextPos));
		}
	}




	@Override
	public void setDividerPosition(final int dividerIndex, final double position) {
		dividerListener.runMuted(() -> super.setDividerPosition(dividerIndex, position));
	}




	@Override
	public void setDividerPositions(final double... positions) {
		dividerListener.runMuted(() -> super.setDividerPositions(positions));
	}


}
