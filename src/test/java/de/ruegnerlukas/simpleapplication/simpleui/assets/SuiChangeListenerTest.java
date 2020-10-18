package de.ruegnerlukas.simpleapplication.simpleui.assets;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.SuiListChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiChangeListenerTest {


	@Test
	public void test_add_elements() {
		final ObservableList<String> list = FXCollections.observableList(new ArrayList<>(List.of("A", "B", "C")));

		final List<String> addedElements = new ArrayList<>();
		list.addListener(new SuiListChangeListener<>(addedElements::add, null));

		assertThat(addedElements).isEmpty();

		list.add("D");
		assertThat(addedElements).containsExactly("D");

		list.addAll("E", "F");
		assertThat(addedElements).containsExactly("D", "E", "F");

		list.add(1, "G");
		assertThat(addedElements).containsExactly("D", "E", "F", "G");

	}




	@Test
	public void test_remove_elements() {
		final ObservableList<String> list = FXCollections.observableList(new ArrayList<>(List.of("A", "B", "C", "D", "E", "F")));

		final List<String> removedElements = new ArrayList<>();
		list.addListener(new SuiListChangeListener<>(null, removedElements::add));

		list.remove("C");
		assertThat(removedElements).containsExactly("C");

		list.removeAll("E", "D");
		assertThat(removedElements).containsExactly("C", "D", "E");

		list.remove(0);
		assertThat(removedElements).containsExactly("C", "D", "E", "A");

	}


}
