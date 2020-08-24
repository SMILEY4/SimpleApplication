package de.ruegnerlukas;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class ListTransformTest {


	@Test
	public void justARandomTest() {

		List<String> listMatch = new ArrayList<>(List.of("4", "1", "2", "3", "5", "6"));

		List<String> listInput = new ArrayList<>(List.of("5", "0", "2", "x", "6", "4"));
		log.info("  list before: {}", listInput);
		log.info(" should match: {}", listMatch);
		System.out.println();

//		List<ListOperation> operations = List.of(
//
//				// removes first -> back to front
//				l -> remove(l, 0),
//
//				// then swaps
//				l -> swap(l, 0, 1),
//
//				// then adds -> back to front
//				l -> add(l, "3", 2),
//				l -> add(l, "1", 1)
//		);

		List<Pair<ListOperation, Integer>> operations = new ArrayList<>();

		Set<String> setInput = Set.copyOf(listInput);
		Set<String> setMatch = Set.copyOf(listMatch);

		// remove
		Set<String> elementsToRemove = new HashSet<>(setInput);
		elementsToRemove.removeAll(setMatch);
		elementsToRemove.stream()
				.map(listInput::indexOf)
				.sorted((a, b) -> -Integer.compare(a, b))
				.peek(index -> log.info("remove @{}", index))
				.forEach(index -> operations.add(Pair.of(l -> remove(l, index), 1)));
		System.out.println();

		// add
		Set<String> elementsToAdd = new HashSet<>(setMatch);
		elementsToAdd.removeAll(setInput);
		elementsToAdd.stream()
				.map(e -> Pair.of(e, listMatch.indexOf(e)))
				.sorted(Comparator.comparingInt(Pair::getRight))
				.peek(pair -> log.info("add {} @{}", pair.getLeft(), pair.getRight()))
				.forEach(pair -> operations.add(Pair.of(l -> add(l, pair.getLeft(), pair.getRight()), 1)));
		System.out.println();

		// swap
		List<Pair<String, Pair<Integer, Integer>>> pairList = new ArrayList<>();
		for (int i = 0; i < listInput.size(); i++) {
			int indexMatch = listMatch.indexOf(listInput.get(i));
			if (indexMatch > -1) {
				if (i != indexMatch) {
					pairList.add(Pair.of("\"" + listInput.get(i) + "\"", Pair.of(i, indexMatch)));
				}
			}
		}

		List<Pair<Integer, Integer>> pairs = pairList.stream().map(Pair::getRight).collect(Collectors.toList());
		List<Pair<Integer, Integer>> swaps = new ArrayList<>();
		if (!pairs.isEmpty()) {
			while (!pairs.isEmpty()) {
				Pair<Integer, Integer> pair = pairs.remove(0);
				swaps.add(pair);
				pairs.removeIf(p -> p.getRight().equals(pair.getRight()) || p.getLeft().equals(pair.getRight()));
			}
		}
		swaps.stream()
				.peek(swap -> log.info("swap {} -> {}", swap.getLeft(), swap.getRight()))
				.forEach(swap -> operations.add(Pair.of(l -> swap(l, swap.getLeft(), swap.getRight()), 3)));



		for (ListOperation op : operations.stream().map(Pair::getLeft).collect(Collectors.toList())) {
			op.apply(listInput);
		}

		System.out.println();
		log.info("   list after: {}", listInput);
		log.info(" should match: {}", listMatch);
		log.info("total cost = {}", operations.stream().map(Pair::getRight).mapToInt(cost -> cost).sum());

	}




	private void add(List<String> list, String e, int i) {
		list.add(i, e);
	}




	private void remove(List<String> list, int i) {
		list.remove(i);
	}




	private void swap(List<String> list, int a, int b) {
		int ia = Math.min(a, b);
		int ib = Math.max(a, b);
		String eb = list.remove(ib);
		String ea = list.set(ia, eb);
		list.add(ib, ea);
	}




	interface ListOperation {





		void apply(List<String> list);

	}






	@Getter
	@AllArgsConstructor
	static class Pair<L, R> {


		public static <L, R> Pair<L, R> of(final L left, final R right) {
			return new Pair<>(left, right);
		}




		private final L left;
		private final R right;




		@Override
		public String toString() {
			return "<" + left + "," + right + ">";
		}

	}


}
