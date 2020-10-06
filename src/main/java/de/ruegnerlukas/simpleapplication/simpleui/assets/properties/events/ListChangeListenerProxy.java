package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiListChangeListener;
import javafx.collections.ObservableList;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

public class ListChangeListenerProxy<T> {


	/**
	 * the list change listener
	 */
	private final SuiListChangeListener<T> changeListener;

	/**
	 * the currently observing list.
	 */
	private Set<ObservableList<T>> observables = new HashSet<>();

	/**
	 * Whether this proxy should pass on events.
	 */
	@Getter
	@Setter
	private boolean muted = false;




	/**
	 * @param listenerAdded   a consumer called with the added elements
	 * @param listenerRemoved a consumer called with the removed elements
	 */
	public ListChangeListenerProxy(final Consumer<List<T>> listenerAdded, final Consumer<List<T>> listenerRemoved) {
		this.changeListener = new SuiListChangeListener<>(
				null,
				onAddAll -> {
					if (!isMuted()) {
						listenerAdded.accept(onAddAll);
					}
				},
				null,
				onRemoveAll -> {
					if (!isMuted()) {
						listenerRemoved.accept(onRemoveAll);
					}
				},
				null
		);
	}




	/**
	 * Adds this change listener to the given observable list.
	 *
	 * @param observableList the observable list to observe.
	 */
	public void addTo(final ObservableList<T> observableList) {
		if (!observables.contains(observableList)) {
			observables.add(observableList);
			observableList.addListener(this.changeListener);
		}
	}




	/**
	 * Removes this change listener from the given observable list.
	 *
	 * @param observableList the observable list.
	 */
	public void removeFrom(final ObservableList<T> observableList) {
		observables.remove(observableList);
		observableList.removeListener(changeListener);
	}




	/**
	 * Removes this change listener from all observable value.
	 */
	public void removeFromAll() {
		observables.forEach(observableList -> {
			observableList.removeListener(changeListener);
		});
		observables.clear();
	}


}
