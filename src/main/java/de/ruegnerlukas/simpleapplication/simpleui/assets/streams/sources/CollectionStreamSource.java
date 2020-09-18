package de.ruegnerlukas.simpleapplication.simpleui.assets.streams.sources;

import java.util.Collection;

public class CollectionStreamSource<T> extends StreamSource<T> {


	/**
	 * @param collection the source collection with all elements
	 */
	public void acceptCollection(final Collection<T> collection) {
		for (T e : collection) {
			pushElementToNext(e);
		}
	}

}
