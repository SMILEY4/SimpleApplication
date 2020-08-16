package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.Sampler;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ListTransformer {


	private final List<String> source;

	private final List<String> target;

	private final Map<String, Integer> targetIndexMap;



	public ListTransformer(final List<String> source, final List<String> target) {
		this.source = source;
		this.target = target;
		this.targetIndexMap = buildIndexMap(target);
	}




	public Set<Pair<String, Integer>> getElementsKept() {
		Sampler.Sample samplerKept = Sampler.start("getKept " + source.size());

		final Set<String> setTarget = Set.copyOf(target);

		Set<Pair<String, Integer>> result = Set.copyOf(source).stream()
				.filter(setTarget::contains)
				.map(element -> Pair.of(element, targetIndexMap.get(element)))
				.collect(Collectors.toSet());

		samplerKept.stop();
		return result;
	}




	public List<TransformOperation> calculateTransformations() {
		Sampler.Sample sample = Sampler.start("calculateTransformations " + source.size() + "->" + target.size());
		if (listsEqual(source, target)) {
			sample.stop();
			return new ArrayList<>();
		}
		final Set<String> setSource = Set.copyOf(source);
		final Set<String> setTarget = Set.copyOf(target);
		List<TransformOperation> result = calculateTransformations(new ArrayList<>(source), setSource, Collections.unmodifiableList(target), setTarget);
		sample.stop();
		return result;
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

		Sampler.Sample sampleRemove = Sampler.start("removeOperations " + source.size() + "->" + target.size());
		List<RemoveOperations> removeOperations = calculateRemoveOperations(source, setSource, target, setTarget);
		applyRemoveOperations(removeOperations, source);
		operations.addAll(removeOperations);
		sampleRemove.stop();

		Sampler.Sample sampleAdd = Sampler.start("addOperations " + source.size() + "->" + target.size());
		List<AddOperations> addOperations = calculateAddOperations(source, setSource, target, setTarget);
		applyAddOperations(addOperations, source);
		operations.addAll(addOperations);
		sampleAdd.stop();

		Sampler.Sample sampleSwap = Sampler.start("swapOperations " + source.size() + "->" + target.size());
		operations.addAll(calculateSwapOperations(source, target));
		sampleSwap.stop();

		return operations;
	}




	private List<RemoveOperations> calculateRemoveOperations(final List<String> source, final Set<String> setSource,
															 final List<String> target, final Set<String> setTarget) {
		return setSource.stream()
				.filter(e -> !setTarget.contains(e))
				.map(e -> source.indexOf(e))
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

		final List<Pair<Integer, Integer>> pairs = new ArrayList<>();
		for (int i = 0; i < source.size(); i++) {
			final int indexMatch = indexMap.getOrDefault(source.get(i), -1);
			if (indexMatch > -1) {
				if (i != indexMatch) {
					pairs.add(Pair.of(i, indexMatch));
				}
			}
		}

		final List<Pair<Integer, Integer>> swaps = new ArrayList<>();
		if (!pairs.isEmpty()) {
			while (!pairs.isEmpty()) {
				Pair<Integer, Integer> pair = pairs.remove(0);
				swaps.add(pair);
				pairs.removeIf(p -> p.getRight().equals(pair.getRight()) || p.getLeft().equals(pair.getRight()));
			}
		}

		return swaps.stream()
				.map(swap -> new SwapOperation(swap.getLeft(), swap.getRight()))
				.collect(Collectors.toList());
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

}


