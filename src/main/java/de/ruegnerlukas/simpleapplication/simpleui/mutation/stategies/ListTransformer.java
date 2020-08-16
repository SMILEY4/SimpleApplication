package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListTransformer {


	private final List<String> source;

	private final List<String> target;




	public ListTransformer(final List<String> source, final List<String> target) {
		this.source = source;
		this.target = target;
	}




	public Set<Pair<String, Integer>> getElementsKept() {
		final Set<String> setTarget = Set.copyOf(target);
		return Set.copyOf(source).stream()
				.filter(setTarget::contains)
				.map(element -> Pair.of(element, target.indexOf(element)))
				.collect(Collectors.toSet());
	}




	public List<TransformOperation> calculateTransformations() {
		if (listsEqual(source, target)) {
			return new ArrayList<>();
		}
		final Set<String> setSource = Set.copyOf(source);
		final Set<String> setTarget = Set.copyOf(target);
		return calculateTransformations(new ArrayList<>(source), setSource, new ArrayList<>(target), setTarget);
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
		operations.addAll(calculateRemoveOperations(source, setSource, target, setTarget));
		operations.addAll(calculateAddOperations(source, setSource, target, setTarget));
		operations.addAll(calculateSwapOperations(source, target));
		return operations;
	}




	private List<RemoveOperations> calculateRemoveOperations(final List<String> source, final Set<String> setSource,
															 final List<String> target, final Set<String> setTarget) {
		// TODO: apply operations to source list for real -> better results, less bugs
		return setSource.stream()
				.filter(e -> !setTarget.contains(e))
				.map(source::indexOf)
				.sorted((a, b) -> -Integer.compare(a, b))
				.map(RemoveOperations::new)
				.collect(Collectors.toList());
	}




	private List<AddOperations> calculateAddOperations(final List<String> source, final Set<String> setSource,
													   final List<String> target, final Set<String> setTarget) {
		// TODO: apply operations to source list for real -> better results, less bugs
		return setTarget.stream()
				.filter(e -> !setSource.contains(e))
				.map(e -> Pair.of(e, target.indexOf(e)))
				.sorted(Comparator.comparingInt(Pair::getRight))
				.map(pair -> new AddOperations(pair.getLeft(), pair.getRight()))
				.collect(Collectors.toList());
	}




	private List<SwapOperation> calculateSwapOperations(final List<String> source, final List<String> target) {

		// TODO: apply operations to source list for real -> better results, less bugs
		final List<Pair<Integer, Integer>> pairs = new ArrayList<>();
		for (int i = 0; i < source.size(); i++) {
			final int indexMatch = target.indexOf(source.get(i));
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


