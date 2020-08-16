package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ListTransformer {


	private final List<String> source;

	private final List<String> target;

	private final Map<String, Integer> targetIndexMap;

	private final Set<String> setTarget;




	public ListTransformer(final List<String> source, final List<String> target) {
		this.source = source;
		this.target = target;
		this.targetIndexMap = buildIndexMap(target);
		this.setTarget = new HashSet<>(target);
	}




	public Set<Pair<String, Integer>> getElementsKept() {
		return source.stream()
				.filter(setTarget::contains)
				.map(element -> Pair.of(element, targetIndexMap.get(element)))
				.collect(Collectors.toSet());
	}




	public Set<Pair<Integer, Integer>> getIndicesKept() { // <srcIndex,tgtIndex>
		Set<Pair<Integer, Integer>> set = new HashSet<>();
		for (int i = 0, n = source.size(); i < n; i++) {
			final String elementSource = source.get(i);
			if (setTarget.contains(elementSource)) {
				set.add(Pair.of(i, targetIndexMap.get(elementSource)));
			}
		}
		return set;
	}




	public List<TransformOperation> calculateTransformations() {
		if (listsEqual(source, target)) {
			return new ArrayList<>();
		}
		final Set<String> setSource = new HashSet<>(source);
		return calculateTransformations(new ArrayList<>(source), setSource, Collections.unmodifiableList(target), setTarget);
	}




	private boolean listsEqual(final List<String> source, final List<String> target) {
		if (source.size() != target.size()) {
			return false;
		}
		for (int i = 0, n = source.size(); i < n; i++) {
			if (!source.get(i).equals(target.get(i))) {
				return false;
			}
		}
		return true;
	}




	private List<TransformOperation> calculateTransformations(final List<String> source, final Set<String> setSource,
															  final List<String> target, final Set<String> setTarget) {

		List<RemoveOperations> removeOperations = calculateRemoveOperations(source, setSource, target, setTarget);
		applyRemoveOperations(removeOperations, source);
		final List<TransformOperation> operations = new ArrayList<>(removeOperations);

		List<AddOperations> addOperations = calculateAddOperations(source, setSource, target, setTarget);
		applyAddOperations(addOperations, source);
		operations.addAll(addOperations);

		operations.addAll(calculateSwapOperations(source, target));

		return operations;
	}




	private List<RemoveOperations> calculateRemoveOperations(final List<String> source, final Set<String> setSource,
															 final List<String> target, final Set<String> setTarget) {
		final Map<String, Integer> sourceIndexMap = buildIndexMap(source);
		return setSource.stream()
				.filter(e -> !setTarget.contains(e))
				.map(sourceIndexMap::get)
				.sorted((a, b) -> -Integer.compare(a, b))
				.map(RemoveOperations::new)
				.collect(Collectors.toList());
	}




	private void applyRemoveOperations(List<RemoveOperations> operations, List<String> source) {
		operations.forEach(operation -> source.remove(operation.getIndex()));
	}




	private List<AddOperations> calculateAddOperations(final List<String> source, final Set<String> setSource,
													   final List<String> target, final Set<String> setTarget) {
		return setTarget.stream()
				.filter(e -> !setSource.contains(e))
				.map(e -> Pair.of(e, targetIndexMap.get(e)))
				.sorted(Comparator.comparingInt(Pair::getRight))
				.map(pair -> new AddOperations(pair.getLeft(), pair.getRight()))
				.collect(Collectors.toList());
	}




	private void applyAddOperations(List<AddOperations> operations, List<String> source) {
		operations.forEach(operation -> source.add(operation.getIndex(), operation.getElement()));
	}




	private List<SwapOperation> calculateSwapOperations(final List<String> source, final List<String> target) {
		Map<String, Integer> indexMap = buildIndexMap(target);
		List<IntPair> pairs = getSwapIndexPairs(source, indexMap);
		final List<IntPair> swaps = calculateSwaps(pairs);
		final List<SwapOperation> result = new ArrayList<>(swaps.size());
		swaps.forEach(swap -> result.add(new SwapOperation(swap.getLeft(), swap.getRight())));
		return result;
	}




	private List<IntPair> calculateSwaps(List<IntPair> pairs) {
		final List<IntPair> swaps = new ArrayList<>(pairs.size());
		while (!pairs.isEmpty()) {
			IntPair pair = pairs.remove(pairs.size()-1);
			swaps.add(pair);
			List<IntPair> toKeep = new ArrayList<>(pairs.size());
			for (int i = 0, n = pairs.size(); i < n; i++) {
				IntPair p = pairs.get(i);
				if (!(p.getRight() == pair.getRight() || p.getLeft() == pair.getRight())) {
					toKeep.add(p);
				}
			}
			pairs = toKeep;
		}
		return swaps;
	}




	private List<IntPair> getSwapIndexPairs(final List<String> source, final Map<String, Integer> indexMap) {
		List<IntPair> pairs = new ArrayList<>(source.size());
		for (int i = 0; i < source.size(); i++) {
			final Integer indexMatch = indexMap.get(source.get(i));
			if (indexMatch != null) {
				if (i != indexMatch) {
					pairs.add(IntPair.of(i, indexMatch));
				}
			}
		}
		return pairs;
	}




	private Map<String, Integer> buildIndexMap(List<String> list) {
		final Map<String, Integer> indexMap = new HashMap<>();
		for (int i = 0; i < list.size(); i++) {
			indexMap.put(list.get(i), i);
		}
		return indexMap;
	}




	@Getter
	@AllArgsConstructor
	public static class AddOperations implements TransformOperation {


		private final String element;

		private final int index;


	}






	@Getter
	@AllArgsConstructor
	public static class RemoveOperations implements TransformOperation {


		private final int index;

	}






	@Getter
	public static class SwapOperation implements TransformOperation {


		private final int indexMin;

		private final int indexMax;




		public SwapOperation(final int a, final int b) {
			this.indexMin = Math.min(a, b);
			this.indexMax = Math.max(a, b);
		}


	}






	@Getter
	@AllArgsConstructor
	public static class ReplaceOperation implements TransformOperation {


		private final int index;

		private final String element;


	}






	public interface TransformOperation {


	}






	@Getter
	@AllArgsConstructor
	public static class Pair<L, R> {


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






	@Getter
	@AllArgsConstructor
	public static class IntPair {


		public static IntPair of(final int left, final int right) {
			return new IntPair(left, right);
		}




		private final int left;
		private final int right;




		@Override
		public String toString() {
			return "<" + left + "," + right + ">";
		}

	}


}


