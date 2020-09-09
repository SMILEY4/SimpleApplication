package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.Pipeline;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.PipelineImpl;
import javafx.beans.value.WritableValue;

public class CollectIntoValueStream<T> extends PipelineImpl<T, T> {


	/**
	 * The value to set to the latest element
	 */
	private final WritableValue<T> value;




	/**
	 * @param source the source pipeline
	 * @param value  the value to set to the latest element
	 */
	public CollectIntoValueStream(final Pipeline<?, T> source, final WritableValue<T> value) {
		super(source);
		this.value = value;
	}




	@Override
	protected void process(final T element) {
		value.setValue(element);
	}


}
