package de.ruegnerlukas.simpleapplication.simpleui.assets.events;

import javafx.collections.ListChangeListener;
import lombok.AllArgsConstructor;

import java.util.function.Consumer;

@AllArgsConstructor
public class SuiListChangeListener<T> implements ListChangeListener<T> {


	/**
	 * The consumer called for each element added to the list.
	 */
	private final Consumer<T> onAdd;

	/**
	 * The consumer called for each element removed from the list.
	 */
	private final Consumer<T> onRemove;




	@Override
	public void onChanged(final Change<? extends T> change) {
		while (change.next()) {
			change.getAddedSubList().forEach(element -> {
				if (onAdd != null) {
					onAdd.accept(element);
				}
			});
			change.getRemoved().forEach(element -> {
				if (onRemove != null) {
					onRemove.accept(element);
				}
			});
		}
	}


}
