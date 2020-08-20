package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.AddTransformation;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.RemoveTransformation;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.SwapTransformation;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.BaseTransformation;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

@Slf4j
public class ListTransformerTest {


	@Test
	public void someTest() {

		List<String> source = List.of("a", "b", "c");
		List<String> target = List.of("a", "c");

		log.info("source: {}", source);
		log.info("target: {}", target);

		List<BaseTransformation> operations = new ListTransformer(source, target).calculateTransformations();
		List<String> processed = new ArrayList<>(source);
		operations.forEach(op -> {
			if (op instanceof AddTransformation) {
				AddTransformation operation = (AddTransformation) op;
				processed.add(operation.getIndex(), operation.getElement());
			}
			if (op instanceof RemoveTransformation) {
				RemoveTransformation operation = (RemoveTransformation) op;
				processed.remove(operation.getIndex());
			}
			if (op instanceof SwapTransformation) {
				SwapTransformation operation = (SwapTransformation) op;
				String elementMax = processed.remove(operation.getIndexMax());
				String elementMin = processed.set(operation.getIndexMin(), elementMax);
				processed.add(operation.getIndexMax(), elementMin);
			}
		});

		log.info("result: {}", processed);

	}



	@Test
	public void testShuffle() {

		int n = 10;

		Random random = new Random(1234);
		for(int i=0; i<10; i++) {

			List<String> source = new ArrayList<>();
			List<String> target = new ArrayList<>();
			for (int j = 0; j < n; j++) {
				source.add(String.valueOf((char) ('a' + j)));
				target.add(String.valueOf((char) ('a' + j)));
			}
			Collections.shuffle(source, random);

			log.info("source: {}", source);
			log.info("target: {}", target);

			List<BaseTransformation> operations = new ListTransformer(source, target).calculateTransformations();
			List<String> processed = new ArrayList<>(source);
			operations.forEach(op -> {
				if (op instanceof AddTransformation) {
					fail("not allowed: add");
				}
				if (op instanceof RemoveTransformation) {
					fail("not allowed: remove");
				}
				if (op instanceof SwapTransformation) {
					SwapTransformation operation = (SwapTransformation) op;
					String elementMax = processed.remove(operation.getIndexMax());
					String elementMin = processed.set(operation.getIndexMin(), elementMax);
					processed.add(operation.getIndexMax(), elementMin);
				}
			});

			log.info("  done: {}  (ops: {})", processed, operations.size());
			assertThat(processed).containsExactlyElementsOf(target);

			System.out.println();
		}

	}



	@Test
	public void test() {

		log.info("test 1: ");
		doTest(createRandomTest(3, 1));
		System.out.println();

		log.info("test 2: ");
		doTest(createRandomTest(6, 2));
		System.out.println();

		log.info("test 3: ");
		doTest(createRandomTest(6, 3));
		System.out.println();

		log.info("test 4: ");
		doTest(createRandomTest(10, 4));

	}




	public void doTest(Pair<List<String>, List<String>> testData) {

		log.info("source: {}", testData.getLeft());
		log.info("target: {}", testData.getRight());

		List<BaseTransformation> operations = new ListTransformer(testData.getLeft(), testData.getRight()).calculateTransformations();
		List<String> processed = new ArrayList<>(testData.getLeft());
		operations.forEach(op -> {
			if (op instanceof AddTransformation) {
				AddTransformation operation = (AddTransformation) op;
				processed.add(operation.getIndex(), operation.getElement());
			}
			if (op instanceof RemoveTransformation) {
				RemoveTransformation operation = (RemoveTransformation) op;
				processed.remove(operation.getIndex());
			}
			if (op instanceof SwapTransformation) {
				SwapTransformation operation = (SwapTransformation) op;
				String elementMax = processed.remove(operation.getIndexMax());
				String elementMin = processed.set(operation.getIndexMin(), elementMax);
				processed.add(operation.getIndexMax(), elementMin);
			}
		});

		log.info("  done: {}  (ops: {})", processed, operations.size());
		assertThat(processed).containsExactlyElementsOf(testData.getRight());
	}




	public Pair<List<String>, List<String>> createRandomTest(int n, long seed) {

		Random random = new Random();

		List<String> source = new ArrayList<>();
		List<String> target = new ArrayList<>();

		for (int i = 0; i < n; i++) {
			source.add(String.valueOf((char) ('a' + i)));
		}
		target.addAll(source);

		for (int i = 0; i < random.nextInt(Math.max(0, n - 1)); i++) {
			source.remove(random.nextInt(source.size()));
		}

		for (int i = 0; i < random.nextInt(Math.max(0, n - 1)); i++) {
			target.remove(random.nextInt(target.size()));
		}

		Collections.shuffle(source, random);
		Collections.shuffle(target, random);

		return Pair.of(source, target);

	}


}
