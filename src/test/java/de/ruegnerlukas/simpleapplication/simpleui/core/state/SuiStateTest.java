package de.ruegnerlukas.simpleapplication.simpleui.core.state;

import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.testutils.TestState;
import javafx.application.Platform;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

public class SuiStateTest extends ApplicationTest {


	@Test
	public void test_normal_state_update() {

		final List<Triplet<Boolean, SuiState, Set<String>>> collectedData = new ArrayList<>();

		final TestState testState = new TestState("original text");
		testState.addStateListener((state, update, tags) -> collectedData.add(Triplet.of(Platform.isFxApplicationThread(), state, tags.getTags())));

		testState.update(TestState.class, state -> state.text = "new text");
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(collectedData).hasSize(1);
		assertThat(collectedData.get(0).getLeft()).isTrue();
		assertThat(collectedData.get(0).getMiddle()).isEqualTo(testState);
		assertThat(collectedData.get(0).getRight()).isEmpty();
	}




	@Test
	public void test_silent_state_update() {

		final List<Triplet<Boolean, SuiState, Set<String>>> collectedData = new ArrayList<>();

		final TestState testState = new TestState("original text");
		testState.addStateListener((state, update, tags) -> collectedData.add(Triplet.of(Platform.isFxApplicationThread(), state, tags.getTags())));

		testState.update(TestState.class, true, state -> state.text = "new text");
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(collectedData).isEmpty();
	}




	@Test
	public void test_unsafe_state_update() {

		final List<Triplet<Boolean, SuiState, Set<String>>> collectedData = new ArrayList<>();

		final TestState testState = new TestState("original text");
		testState.addStateListener((state, update, tags) -> collectedData.add(Triplet.of(Platform.isFxApplicationThread(), state, tags.getTags())));

		testState.updateUnsafe(TestState.class, state -> state.text = "new text");
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(collectedData).hasSize(1);
		assertThat(collectedData.get(0).getLeft()).isFalse();
		assertThat(collectedData.get(0).getMiddle()).isEqualTo(testState);
		assertThat(collectedData.get(0).getRight()).isEmpty();
	}




	@Test
	public void test_tagged_state_update() {

		final List<Triplet<Boolean, SuiState, Set<String>>> collectedData = new ArrayList<>();

		final TestState testState = new TestState("original text");
		testState.addStateListener((state, update, tags) -> collectedData.add(Triplet.of(Platform.isFxApplicationThread(), state, tags.getTags())));

		testState.updateUnsafe(TestState.class, (TaggedSuiStateUpdate<TestState>) state -> {
			state.text = "new text";
			return Tags.from("tag1", "tag2");
		});
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(collectedData).hasSize(1);
		assertThat(collectedData.get(0).getLeft()).isFalse();
		assertThat(collectedData.get(0).getMiddle()).isEqualTo(testState);
		assertThat(collectedData.get(0).getRight()).containsExactlyInAnyOrder("tag1", "tag2");
	}




	@Test
	public void test_tagged_silent_state_update() {

		final List<Triplet<Boolean, SuiState, Set<String>>> collectedData = new ArrayList<>();

		final TestState testState = new TestState("original text");
		testState.addStateListener((state, update, tags) -> collectedData.add(Triplet.of(Platform.isFxApplicationThread(), state, tags.getTags())));

		testState.updateUnsafe(TestState.class, true, (TaggedSuiStateUpdate<TestState>) state -> {
			state.text = "new text";
			return Tags.from("tag1", "tag2");
		});
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(collectedData).isEmpty();
	}




	@Test
	public void test_state_listener() {

		final AtomicInteger countBefore = new AtomicInteger(0);
		final AtomicInteger countAfter = new AtomicInteger(0);

		final TestState testState = new TestState("original text");
		testState.addStateListener(new SuiStateListener() {
			@Override
			public void beforeUpdate(final SuiState state, final SuiStateUpdate<?> update) {
				countBefore.incrementAndGet();
			}




			@Override
			public void stateUpdated(final SuiState state, final SuiStateUpdate<?> update, final Tags tags) {
				countAfter.incrementAndGet();
			}
		});

		testState.update(TestState.class, state -> state.text = "new text");
		delay(100);

		assertThat(testState.text).isEqualTo("new text");
		assertThat(countBefore.get()).isEqualTo(1);
		assertThat(countAfter.get()).isEqualTo(1);
	}




	private void delay(final long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
