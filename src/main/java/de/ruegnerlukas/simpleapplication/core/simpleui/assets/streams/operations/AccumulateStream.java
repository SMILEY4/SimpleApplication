package de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.PipelineImpl;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class AccumulateStream<T> extends PipelineImpl<T, List<T>> {


	/**
	 * The max amount of held back elements.
	 */
	private final int maxAmount;

	/**
	 * The held back elements.
	 */
	private final List<T> heldBackElements = new ArrayList<>();

	/**
	 * The timer.
	 */
	private final JFXTimer timer;




	/**
	 * @param source    the source pipeline
	 * @param maxAmount The max amount of held back elements.
	 * @param timeout   The max time of holding back elements
	 */
	public AccumulateStream(final Pipeline<?, T> source, final int maxAmount, final Duration timeout) {
		super(source);
		this.maxAmount = maxAmount;
		this.timer = new JFXTimer(timeout, this::onTimeout);
	}




	@Override
	protected void process(final T element) {
		if (timer.isRunning()) {
			heldBackElements.add(element);
			if (heldBackElements.size() >= maxAmount) {
				timer.stop();
				pushElementToNext(new ArrayList<>(heldBackElements));
				heldBackElements.clear();
			}
		} else {
			timer.start();
			heldBackElements.add(element);
		}
	}




	/**
	 * Called when the timer runs out.
	 */
	private void onTimeout() {
		pushElementToNext(new ArrayList<>(heldBackElements));
		heldBackElements.clear();
	}


}
