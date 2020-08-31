package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.AddOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.BaseOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.RemoveOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.ReplaceOperation;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.operations.SwapOperation;
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


	/**
	 * The source list.
	 */
	private final List<String> source;

	/**
	 * The target list.
	 */
	private final List<String> target;

	/**
	 * The map of all (target) elements and their indices in the target list.
	 */
	private final Map<String, Integer> targetIndexMap;

	/**
	 * The set of all elements of the target list.
	 */
	private final Set<String> setTarget;




	/**
	 * @param source the source list
	 * @param target the target list to match
	 */
	public ListTransformer(final List<String> source, final List<String> target) {
		this.source = source;
		this.target = target;
		this.targetIndexMap = buildIndexMap(target);
		this.setTarget = new HashSet<>(target);
	}




	/**
	 * Return all elements that are in the source and target list together with their indices in the target list
	 *
	 * @return the elements as a pair of the element and the index
	 */
	public Set<Pair<String, Integer>> getPermanentElements() {
		return source.stream()
				.filter(setTarget::contains)
				.map(element -> Pair.of(element, targetIndexMap.get(element)))
				.collect(Collectors.toSet());
	}




	/**
	 * Calculates all {@link BaseTransformation} required to make the source list match the target list.
	 *
	 * @return the required transformations
	 */
	public List<BaseTransformation> calculateTransformations() {
		if (listsEqual(source, target)) {
			return new ArrayList<>();
		}
		final Set<String> setSource = new HashSet<>(source);
		return calculateTransformations(new ArrayList<>(source), setSource, Collections.unmodifiableList(target), setTarget);
	}




	/**
	 * Calculates all {@link BaseTransformation} required to make the given source list match the given target list.
	 *
	 * @param setSource the set of source elements
	 * @param setTarget the set of elements to match
	 * @return the required transformations
	 */
	private List<BaseTransformation> calculateTransformations(final List<String> source, final Set<String> setSource,
															  final List<String> target, final Set<String> setTarget) {

		final List<BaseTransformation> operations = new ArrayList<>();

		// remove
		List<RemoveTransformation> removeOperations = calculateRemoveTransformations(source, setSource, setTarget);
		applyRemoveTransformations(removeOperations, source);
		operations.addAll(removeOperations);

		// add
		List<AddTransformation> addOperations = calculateAddTransformations(setSource, setTarget);
		applyAddTransformations(addOperations, source);
		operations.addAll(addOperations);

		// swap
		operations.addAll(calculateSwapTransformations(source, target));

		return operations;
	}




	/**
	 * Calculates all {@link RemoveTransformation} required to make the given source list match the given target list.
	 *
	 * @param setSource the set of source elements
	 * @param setTarget the set of elements to match
	 * @return the required remove-transformations
	 */
	private List<RemoveTransformation> calculateRemoveTransformations(final List<String> source, final Set<String> setSource,
																	  final Set<String> setTarget) {
		if (setTarget.size() > setSource.size() && setTarget.containsAll(setSource)) {
			return List.of();
		}
		final Map<String, Integer> sourceIndexMap = buildIndexMap(source);
		return setSource.stream()
				.filter(e -> !setTarget.contains(e))
				.map(sourceIndexMap::get)
				.sorted((a, b) -> -Integer.compare(a, b))
				.map(RemoveTransformation::new)
				.collect(Collectors.toList());
	}




	/**
	 * Calculates all {@link AddTransformation} required to make the given source list match the given target list.
	 *
	 * @param setSource the set of source elements
	 * @param setTarget the set of elements to match
	 * @return the required add-transformations
	 */
	private List<AddTransformation> calculateAddTransformations(final Set<String> setSource, final Set<String> setTarget) {
		if (setSource.size() > setTarget.size() && setSource.containsAll(setTarget)) {
			return List.of();
		}
		return setTarget.stream()
				.filter(e -> !setSource.contains(e))
				.map(e -> Pair.of(e, targetIndexMap.get(e)))
				.sorted(Comparator.comparingInt(Pair::getRight))
				.map(pair -> new AddTransformation(pair.getLeft(), pair.getRight()))
				.collect(Collectors.toList());
	}




	/**
	 * Calculates all {@link SwapTransformation} required to make the given source list match the given target list.
	 *
	 * @param source the source list
	 * @param target the target list to match
	 * @return the required swap-transformations
	 */
	private List<SwapTransformation> calculateSwapTransformations(final List<String> source, final List<String> target) {
		final List<SwapTransformation> result = new ArrayList<>();

		final Map<String, Integer> sourceIndexMap = buildIndexMap(source);
		final List<String> workingList = new ArrayList<>(source);

		for (int i = 0; i < workingList.size(); i++) {
			final String elementSource = workingList.get(i);
			final String elementTarget = target.get(i);
			if (!elementSource.equals(elementTarget)) {
				int indexOfCorrect = sourceIndexMap.get(elementTarget);
				result.add(new SwapTransformation(i, indexOfCorrect));
				Collections.swap(workingList, i, indexOfCorrect);
				swapInIndexMap(sourceIndexMap, elementSource, elementTarget);
			}
		}

		return result;
	}




	/**
	 * Checks whether the two given lists are equal (equal size and equal elements at any index)
	 *
	 * @param a the first list
	 * @param b the other list
	 * @return whether the lists are equal
	 */
	private boolean listsEqual(final List<String> a, final List<String> b) {
		if (a.size() != b.size()) {
			return false;
		}
		for (int i = 0, n = a.size(); i < n; i++) {
			if (!a.get(i).equals(b.get(i))) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Applies all given add-transformations to the given list.
	 *
	 * @param transformations the transformations to apply to the list
	 * @param list            the list to transform
	 */
	private void applyAddTransformations(final List<AddTransformation> transformations, final List<String> list) {
		transformations.forEach(operation -> list.add(operation.getIndex(), operation.getElement()));
	}




	/**
	 * Applies all given remove-transformations to the given list.
	 *
	 * @param transformations the transformations to apply to the list
	 * @param list            the list to transform
	 */
	private void applyRemoveTransformations(final List<RemoveTransformation> transformations, final List<String> list) {
		transformations.forEach(operation -> list.remove(operation.getIndex()));
	}




	/**
	 * Swaps the indices of the two given elements in the given index map.
	 *
	 * @param indexMap the index map.
	 * @param a        the first element
	 * @param b        the other element
	 */
	public void swapInIndexMap(final Map<String, Integer> indexMap, final String a, final String b) {
		int indexA = indexMap.get(a);
		int indexB = indexMap.get(b);
		indexMap.put(a, indexB);
		indexMap.put(b, indexA);
	}




	/**
	 * Creates a map with all the given elements and their indices in the list as their keys.
	 *
	 * @param list the input list
	 * @return a map with the index into the input list as a key and the element at that index as the value
	 */
	private Map<String, Integer> buildIndexMap(final List<String> list) {
		final Map<String, Integer> indexMap = new HashMap<>(list.size());
		for (int i = 0; i < list.size(); i++) {
			indexMap.put(list.get(i), i);
		}
		return indexMap;
	}




	/**
	 * A transformation that adds the given element at the given index.
	 */
	@Getter
	@AllArgsConstructor
	public static class AddTransformation implements BaseTransformation {


		/**
		 * The element to add
		 */
		private final String element;

		/**
		 * The index
		 */
		private final int index;




		@Override
		public BaseOperation toOperation(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
			final SuiNode addNode = target.findChildUnsafe(this.getElement());
			nodeHandlers.getFxNodeBuilder().build(addNode);
			return new AddOperation(this.getIndex(), addNode);
		}

	}






	/**
	 * A transformation that removes the element at the given index from the list.
	 */
	@Getter
	@AllArgsConstructor
	public static class RemoveTransformation implements BaseTransformation {


		/**
		 * The index of the element to remove
		 */
		private final int index;




		@Override
		public RemoveOperation toOperation(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
			return new RemoveOperation(this.getIndex(), original.getChild(this.getIndex()));
		}

	}






	/**
	 * A transformation that swaps the elements at the given indices
	 */
	@Getter
	public static class SwapTransformation implements BaseTransformation {


		/**
		 * The first index (indexMin <= indexMax)
		 */
		private final int indexMin;

		/**
		 * The second index (indexMax >= indexMin)
		 */
		private final int indexMax;




		/**
		 * @param a the first index
		 * @param b the other index
		 */
		public SwapTransformation(final int a, final int b) {
			this.indexMin = Math.min(a, b);
			this.indexMax = Math.max(a, b);
		}




		@Override
		public SwapOperation toOperation(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
			return new SwapOperation(this.getIndexMin(), this.getIndexMax());
		}


	}






	/**
	 * A transformation that replaces the element at the given index with the given new element.
	 */
	@Getter
	@AllArgsConstructor
	public static class ReplaceTransformation implements BaseTransformation {


		/**
		 * The index
		 */
		private final int index;

		/**
		 * The new element to replace the existing element at the index.
		 */
		private final String element;




		@Override
		public ReplaceOperation toOperation(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
			final SuiNode replacementNode = target.findChildUnsafe(this.getElement());
			nodeHandlers.getFxNodeBuilder().build(replacementNode);
			return new ReplaceOperation(this.getIndex(), replacementNode, original.getChild(this.getIndex()));
		}

	}






	/**
	 * A generic transformation.
	 */
	public interface BaseTransformation {


		/**
		 * Converts this transformation into an operation
		 *
		 * @param nodeHandlers the simpleui hanelers
		 * @param original     the original node
		 * @param target       the target node
		 * @return the created operation
		 */
		BaseOperation toOperation(MasterNodeHandlers nodeHandlers, SuiNode original, SuiNode target);

	}

}


