package de.ruegnerlukas.simpleapplication;

import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.Pair;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.AddOperations;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.RemoveOperations;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.SwapOperation;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.TransformOperation;

@Slf4j
public class ListTransformerTest {


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

		List<TransformOperation> operations = new ListTransformer(testData.getLeft(), testData.getRight()).calculateTransformations();

		List<String> processed = new ArrayList<>(testData.getLeft());
		operations.forEach(op -> {
			if(op instanceof AddOperations) {
				AddOperations operation = (AddOperations) op;
				processed.add(operation.getIndex(), operation.getElement());
			}
			if(op instanceof RemoveOperations) {
				RemoveOperations operation = (RemoveOperations) op;
				processed.remove(operation.getIndex());
			}
			if(op instanceof SwapOperation) {
				SwapOperation operation = (SwapOperation) op;
				String elementMax = processed.remove(operation.getIndexMax());
				String elementMin = processed.set(operation.getIndexMin(), elementMax);
				processed.add(operation.getIndexMax(), elementMin);
			}
		});

		log.info("  done: {}", processed);
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
