package de.ruegnerlukas.simpleapplication.simpleui.streams;

import javafx.beans.property.SimpleStringProperty;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StreamTest {

	@Test
	public void testForEachObservable() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable).forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", null);
	}




	@Test
	public void testMap() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable)
				.map(value -> "element " + value)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("element a", "element b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("element a", "element b", "element c", "element d");

	}


	@Test
	public void testFlatMap() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable)
				.flatMap(value -> List.of(value+"1", value+"2"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2", "c1", "c2", "d1", "d2");
	}



	@Test
	public void testFilter() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable)
				.filter(value -> ((int)value.charAt(0)-((int)'a')) % 2 == 0)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "c");
	}



	@Test
	public void testFilterNulls() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable)
				.filterNulls()
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue(null);
		observable.setValue("c");
		assertThat(collectedValues).containsExactly("a", "b", "c");
	}



	@Test
	public void testPeek() {

		final List<String> peekedValues = new ArrayList<>();
		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		Stream.from(observable)
				.peek(peekedValues::add)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		assertThat(peekedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		assertThat(peekedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		assertThat(peekedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", null);
		assertThat(peekedValues).containsExactly("a", "b", "c", "d", null);
	}



}
