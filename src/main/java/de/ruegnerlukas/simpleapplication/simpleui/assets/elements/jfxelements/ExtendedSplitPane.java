package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiListChangeListener;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.SplitPane;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class ExtendedSplitPane extends SplitPane {


	/**
	 * A map of the dividers with their position change listeners.
	 */
	private final Map<Divider, ChangeListener<Number>> internalDividerPosListeners = new HashMap<>();


	/**
	 * The divider listeners ( List<index,prevPos,nextPos> ).
	 */
	private final List<BiConsumer<Integer, Pair<Number, Number>>> dividerListeners = new ArrayList<>();

	/**
	 * Whether the (external) divider listeners should receive events.
	 */
	private boolean muted = false;


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
					muted = true;
					ChangeListener<Number> listener = (v, p, n) -> onDividerMove(addedDiv, p, n);
					internalDividerPosListeners.put(addedDiv, listener);
					addedDiv.positionProperty().addListener(listener);
					Platform.runLater(() -> muted = false);
				},
				removedDiv -> removedDiv.positionProperty().removeListener(internalDividerPosListeners.get(removedDiv))
		));
	}




	/**
	 * Adds the given divider listener.
	 *
	 * @param listener the position listener. Gets called with the index of the divider and a pair of the previous and next position.
	 */
	public void addDividerListener(final BiConsumer<Integer, Pair<Number, Number>> listener) {
		dividerListeners.add(listener);
	}




	/**
	 * Removes the given divider listener.
	 *
	 * @param listener the position listener to remove.
	 */
	public void removeDividerListener(final BiConsumer<Integer, Pair<Number, Number>> listener) {
		dividerListeners.remove(listener);
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
			if (!muted) {
				dividerListeners.forEach(listener -> listener.accept(getDividers().indexOf(divider), Pair.of(prevPos, nextPos)));
			}
		}
	}




	@Override
	public void setDividerPosition(final int dividerIndex, final double position) {
		muteDividerPositionListeners();
		super.setDividerPosition(dividerIndex, position);
		unmuteDividerPositionListeners();
	}




	@Override
	public void setDividerPositions(final double... positions) {
		muteDividerPositionListeners();
		super.setDividerPositions(positions);
		unmuteDividerPositionListeners();
	}




	/**
	 * Mutes the position listeners of all dividers
	 */
	public void muteDividerPositionListeners() {
		internalDividerPosListeners.forEach((divider, listener) -> divider.positionProperty().removeListener(listener));
		muted = true;
	}




	/**
	 * Unmutes the position listeners of all dividers
	 */
	public void unmuteDividerPositionListeners() {
		internalDividerPosListeners.forEach((divider, listener) -> divider.positionProperty().addListener(listener));
		muted = false;
	}

}
