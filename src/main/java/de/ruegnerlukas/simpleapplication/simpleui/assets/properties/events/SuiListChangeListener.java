package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;

import javafx.collections.ListChangeListener;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Consumer;

@AllArgsConstructor
public class SuiListChangeListener<T> implements ListChangeListener<T> {


	/**
	 * The action called when elements were added or removed.
	 */
	private final Runnable onChange;

	/**
	 * The consumer called with all element added to the list.
	 */
	private final Consumer<List<T>> onAddAll;

	/**
	 * The consumer called for each element added to the list, after the onAddAll-consumer was called.
	 */
	private final Consumer<T> onAdd;


	/**
	 * The consumer called with all element removed from the list.
	 */
	private final Consumer<List<T>> onRemoveAll;

	/**
	 * The consumer called for each element removed from the list, after the onRemoveAll-consumer was called.
	 */
	private final Consumer<T> onRemove;




	/**
	 * @param onChange the action called when elements were added or removed.
	 */
	public SuiListChangeListener(final Runnable onChange) {
		this(onChange, null, null, null, null);
	}




	/**
	 * @param onAdd    the consumer called for each element added to the list
	 * @param onRemove the consumer called for each element removed from the list
	 */
	public SuiListChangeListener(final Consumer<T> onAdd, final Consumer<T> onRemove) {
		this(null, null, onAdd, null, onRemove);
	}




	@Override
	public void onChanged(final Change<? extends T> change) {
		if (onChange != null) {
			onChange.run();
		}
		while (change.next()) {
			if (!change.getAddedSubList().isEmpty() && onAddAll != null) {
				onAddAll.accept((List<T>) change.getAddedSubList());
			}
			change.getAddedSubList().forEach(element -> {
				if (onAdd != null) {
					onAdd.accept(element);
				}
			});
			if (!change.getRemoved().isEmpty() && onRemoveAll != null) {
				onRemoveAll.accept((List<T>) change.getRemoved());
			}
			change.getRemoved().forEach(element -> {
				if (onRemove != null) {
					onRemove.accept(element);
				}
			});
		}
	}


}
