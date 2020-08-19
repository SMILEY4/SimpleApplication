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
		this(source, null, target, new HashSet<>(target));
	}




	public ListTransformer(final List<String> source, final Set<String> initialSetSource, final List<String> target, final Set<String> setTarget) {
		this.source = source;
		this.target = target;
		this.targetIndexMap = buildIndexMap(target);
		this.setTarget = setTarget;
	}




	public Set<Pair<String, Integer>> getElementsKept() {
		return source.stream()
				.filter(setTarget::contains)
				.map(element -> Pair.of(element, targetIndexMap.get(element)))
				.collect(Collectors.toSet());
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

		final List<TransformOperation> operations = new ArrayList<>();

		List<RemoveOperations> removeOperations = calculateRemoveOperations(source, setSource, target, setTarget);
		applyRemoveOperations(removeOperations, source);
		operations.addAll(removeOperations);

		List<AddOperations> addOperations = calculateAddOperations(source, setSource, target, setTarget);
		applyAddOperations(addOperations, source);
		operations.addAll(addOperations);

		operations.addAll(calculateSwapOperations(source, target));

		return operations;
	}




	private List<RemoveOperations> calculateRemoveOperations(final List<String> source, final Set<String> setSource,
															 final List<String> target, final Set<String> setTarget) {
		if (setTarget.size() > setSource.size() && setTarget.containsAll(setSource)) {
			return List.of();
		}
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
		if (setSource.size() > setTarget.size() && setSource.containsAll(setTarget)) {
			return List.of();
		}
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
		final List<SwapOperation> result = new ArrayList<>();

		final Map<String, Integer> sourceIndexMap = buildIndexMap(source);
		final List<String> workingList = new ArrayList<>(source);

		for (int i = 0; i < workingList.size(); i++) {
			final String elementSource = workingList.get(i);
			final String elementTarget = target.get(i);
			if (!elementSource.equals(elementTarget)) {
				int indexOfCorrect = sourceIndexMap.get(elementTarget);
				result.add(new SwapOperation(i, indexOfCorrect));
				Collections.swap(workingList, i, indexOfCorrect);
				swapInIndexMap(sourceIndexMap, elementSource, elementTarget);
			}
		}

		return result;
	}




	public void swapInIndexMap(Map<String, Integer> indexMap, String a, String b) {
		int indexA = indexMap.get(a);
		int indexB = indexMap.get(b);
		indexMap.put(a, indexB);
		indexMap.put(b, indexA);
	}




	private Map<String, Integer> buildIndexMap(List<String> list) {
		final Map<String, Integer> indexMap = new HashMap<>(list.size());
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


