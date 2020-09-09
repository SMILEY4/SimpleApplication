package de.ruegnerlukas.simpleapplication.simpleui.assets.streams;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events.SUIEventListener;
import de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations.JFXTimer;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.util.Duration;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiStreamTest extends ApplicationTest {


	@Test
	public void test_source_is_event_stream() {

		final List<String> collectedValues = new ArrayList<>();

		SUIEventListener<String> eventListener = SuiStream.eventStream(String.class, stream -> stream.forEach(collectedValues::add));

		assertThat(collectedValues).isEmpty();
		eventListener.onEvent("a");
		eventListener.onEvent("b");
		assertThat(collectedValues).containsExactly("a", "b");
		eventListener.onEvent("c");
		eventListener.onEvent("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		eventListener.onEvent(null);
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", null);

	}




	@Test
	public void test_iterate_over_observable_value_changes() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable).forEach(collectedValues::add);

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
	public void test_map_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.map(value -> "element " + value)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("element a", "element b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("element a", "element b", "element c", "element d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("element a", "element b", "element c", "element d", "element null");
	}




	@Test
	public void test_map_operation_with_ignore_nulls() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.mapIgnoreNulls(value -> "element " + value)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("element a", "element b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("element a", "element b", "element c", "element d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("element a", "element b", "element c", "element d", null);
	}




	@Test
	public void test_map_operation_for_null_values() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.mapNulls(() -> "null element")
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", "null element");
	}




	@Test
	public void test_map_elements_to_string_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleObjectProperty<Integer> observable = new SimpleObjectProperty<>();
		SuiStream.from(observable)
				.mapToString()
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue(1);
		observable.setValue(2);
		assertThat(collectedValues).containsExactly("1", "2");
		observable.setValue(3);
		observable.setValue(4);
		assertThat(collectedValues).containsExactly("1", "2", "3", "4");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("1", "2", "3", "4", "null");
	}




	@Test
	public void test_flat_map_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.flatMap(value -> List.of(value + "1", value + "2"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2", "c1", "c2", "d1", "d2");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2", "c1", "c2", "d1", "d2", "null1", "null2");
	}




	@Test
	public void test_flat_map_operation_with_ignore_nulls() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.flatMapIgnoreNulls(value -> List.of(value + "1", value + "2"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2", "c1", "c2", "d1", "d2");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a1", "a2", "b1", "b2", "c1", "c2", "d1", "d2", null);
	}




	@Test
	public void test_flat_map_null_values() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.flatMapNulls(() -> List.of("null1", "null2"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(null);
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", "null1", "null2");
	}




	@Test
	public void test_filter_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.filter(value -> ((int) value.charAt(0) - ((int) 'a')) % 2 == 0)
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
	public void test_filter_out_null_values() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
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
	public void test_peek_operation() {

		final List<String> peekedValues = new ArrayList<>();
		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
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




	@Test
	public void test_switch_to_jfx_application_thread() {

		final List<String> threadNames = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.peek(value -> threadNames.add(Thread.currentThread().getName()))
				.onJavaFxThread()
				.forEach(value -> threadNames.add(Thread.currentThread().getName()));

		observable.setValue("a");
		observable.setValue("b");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertThat(threadNames).containsExactlyInAnyOrder("main", "JavaFX Application Thread", "main", "JavaFX Application Thread");
	}




	@Test
	public void test_collect_elements_to_list() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable).collectInto(collectedValues);

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
	public void test_collect_elements_to_single_property() {

		final SimpleStringProperty value = new SimpleStringProperty();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable).collectInto(value);

		assertThat(value.get()).isNull();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(value.get()).isEqualTo("b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(value.get()).isEqualTo("d");
		observable.setValue(null);
		assertThat(value.get()).isNull();
	}




	@Test
	public void test_distinct_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.distinct()
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("c");
		assertThat(collectedValues).containsExactly("a", "b", "c");
		observable.setValue(null);
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "c", null, "d");
		observable.setValue("e");
		observable.setValue("e");
		observable.setValue("e");
		observable.setValue("e");
		observable.setValue("e");
		assertThat(collectedValues).containsExactly("a", "b", "c", null, "d", "e");
	}




	@Test
	public void test_wait_for_operation_excluding_matching_value() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.filter(value -> !" ".equalsIgnoreCase(value))
				.waitFor(false, "x"::equalsIgnoreCase)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).isEmpty();
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(" ");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d");
		observable.setValue(null);
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "c", "d", null);

	}




	@Test
	public void test_wait_for_operation_including_matching_value() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.filter(value -> !" ".equalsIgnoreCase(value))
				.waitFor(true, "x"::equalsIgnoreCase)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).isEmpty();
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "x");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "x");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "x", "c", "d", "x");
		observable.setValue(" ");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "x", "c", "d", "x", "x");
		observable.setValue(null);
		observable.setValue("x");
		assertThat(collectedValues).containsExactly("a", "b", "x", "c", "d", "x", "x", null, "x");

	}




	@Test
	public void test_wait_for_and_pack_operation() {

		final List<List<String>> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.filter(value -> !" ".equalsIgnoreCase(value))
				.waitForAndPack(false, "x"::equalsIgnoreCase)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).isEmpty();
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b"));
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly(List.of("a", "b"));
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b"), List.of("c", "d"));
		observable.setValue(" ");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b"), List.of("c", "d"), List.of());

	}




	@Test
	public void test_wait_for_and_pack_operation_including_matching_value() {

		final List<List<String>> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.filter(value -> !" ".equalsIgnoreCase(value))
				.waitForAndPack(true, "x"::equalsIgnoreCase)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).isEmpty();
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b", "x"));
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly(List.of("a", "b", "x"));
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b", "x"), List.of("c", "d", "x"));
		observable.setValue(" ");
		observable.setValue("x");
		assertThat(collectedValues).containsExactly(List.of("a", "b", "x"), List.of("c", "d", "x"), List.of("x"));

	}




	@Test
	public void test_process_elements_async() {

		final List<String> threadNames = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.async()
				.forEach(value -> threadNames.add(Thread.currentThread().getName()));

		observable.setValue("a");
		observable.setValue("b");

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		assertThat(threadNames).hasSize(2);
		for (String name : threadNames) {
			assertThat(name).startsWith("pool-");
		}

	}




	@Test
	public void test_skip_elements_while_flag_is_set() {

		final List<String> collectedValues = new ArrayList<>();

		final AtomicBoolean skipFlag = new AtomicBoolean(false);

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.skip(skipFlag::get)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");

		skipFlag.set(true);
		observable.setValue("c");
		observable.setValue("d");
		skipFlag.set(false);

		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("e");
		observable.setValue("f");
		assertThat(collectedValues).containsExactly("a", "b", "e", "f");
	}




	@Test
	public void test_group_last_n_elements() {

		final List<List<String>> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.lastN(3)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly(List.of("a"), List.of("a", "b"));
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly(List.of("a"), List.of("a", "b"), List.of("a", "b", "c"), List.of("b", "c", "d"));
		observable.setValue("e");
		assertThat(collectedValues).containsExactly(List.of("a"), List.of("a", "b"), List.of("a", "b", "c"), List.of("b", "c", "d"), List.of("c", "d", "e"));
	}




	@Test
	public void test_unpack_list_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.lastN(2)
				.unpack(String.class)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "a", "b");
		observable.setValue("c");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "a", "b", "b", "c", "c", "d");
	}




	@Test
	public void test_unpack_list_operation_with_null_elements() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleObjectProperty<List<String>> observable = new SimpleObjectProperty<>();
		SuiStream.from(observable)
				.unpack(String.class)
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue(List.of("a"));
		observable.setValue(List.of("a", "b"));
		assertThat(collectedValues).containsExactly("a", "a", "b");
		observable.setValue(null);
		observable.setValue(List.of("d", "e", "f"));
		assertThat(collectedValues).containsExactly("a", "a", "b", null, "d", "e", "f");
	}




	@Test
	public void test_suppress_exceptions_operation() {

		final List<String> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.suppressErrors()
				.peek(value -> Validations.INPUT.notEqual(value, "x").exception("Can not be x"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		observable.setValue("x");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "d");
	}




	@Test
	public void test_handle_exceptions_operation() {

		final List<String> collectedValues = new ArrayList<>();
		final List<String> collectedExceptions = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.handleErrors(e -> collectedExceptions.add(e.getMessage()))
				.peek(value -> Validations.INPUT.notEqual(value, "x").exception("Can not be x"))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();
		assertThat(collectedExceptions).isEmpty();
		observable.setValue("a");
		observable.setValue("b");
		assertThat(collectedValues).containsExactly("a", "b");
		assertThat(collectedExceptions).isEmpty();
		observable.setValue("x");
		observable.setValue("d");
		assertThat(collectedValues).containsExactly("a", "b", "d");
		assertThat(collectedExceptions).containsExactly("Can not be x");

	}




	@Test
	public void test_accumulate_with_max_amount_and_duration_operation() {

		final List<List<String>> collectedValues = new ArrayList<>();

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.accumulate(3, Duration.millis(200))
				.forEach(collectedValues::add);

		assertThat(collectedValues).isEmpty();

		observable.setValue("a");
		observable.setValue("b");
		observable.setValue("c");
		observable.setValue("d");
		delay(400);
		observable.setValue("e");
		observable.setValue("f");
		delay(400);
		observable.setValue("g");
		observable.setValue("h");
		observable.setValue("i");
		observable.setValue("j");
		delay(1000);

		assertThat(collectedValues)
				.containsExactly(List.of("a", "b", "c"), List.of("d"), List.of("e", "f"), List.of("g", "h", "i"), List.of("j"));
	}




	@Test
	public void test_timer_utility_class() {

		final List<String> list = new ArrayList<>();
		final JFXTimer timer = new JFXTimer(javafx.util.Duration.millis(500), () -> {
			list.add("action");
		});


		timer.start();

		delay(50);

		assertThat(list).isEmpty();
		assertThat(timer.isRunning()).isTrue();

		delay(50);

		timer.stop();

		assertThat(list).isEmpty();
		assertThat(timer.isRunning()).isFalse();

		delay(50);

		timer.start();

		assertThat(list).isEmpty();
		assertThat(timer.isRunning()).isTrue();

		delay(600);

		assertThat(list).hasSize(1);
		assertThat(timer.isRunning()).isFalse();

	}




	@Test
	public void test_update_state_operation() {


		final TestState testState = new TestState("-");

		final List<String> collectedValues = new ArrayList<>();
		testState.addStateListener((state, update) -> {
			collectedValues.add(((TestState) state).text);
		});

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.updateState(TestState.class, testState, (state, value) -> state.text = value);

		assertThat(testState.text).isEqualTo("-");

		observable.setValue("a");
		delay(200);
		assertThat(testState.text).isEqualTo("a");

		observable.setValue("b");
		observable.setValue("c");
		delay(200);
		assertThat(testState.text).isEqualTo("c");

		observable.setValue(null);
		delay(200);
		assertThat(testState.text).isEqualTo(null);

		observable.setValue("e");
		delay(200);
		assertThat(testState.text).isEqualTo("e");

		assertThat(collectedValues).containsExactly("a", "b", "c", null, "e");
	}




	@Test
	public void test_update_state_silent_operation() {


		final TestState testState = new TestState("-");

		final List<String> collectedValues = new ArrayList<>();
		testState.addStateListener((state, update) -> {
			collectedValues.add(((TestState) state).text);
		});

		final SimpleStringProperty observable = new SimpleStringProperty();
		SuiStream.from(observable)
				.updateStateSilent(TestState.class, testState, (state, value) -> state.text = value);

		assertThat(testState.text).isEqualTo("-");

		observable.setValue("a");
		delay(200);
		assertThat(testState.text).isEqualTo("a");

		observable.setValue("b");
		observable.setValue("c");
		delay(200);
		assertThat(testState.text).isEqualTo("c");

		observable.setValue(null);
		delay(200);
		assertThat(testState.text).isEqualTo(null);

		observable.setValue("e");
		delay(200);
		assertThat(testState.text).isEqualTo("e");

		assertThat(collectedValues).isEmpty();
	}




	private void delay(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}


}
