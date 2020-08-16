package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.AsyncChunkProcessor;
import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ListTransformer.Pair;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class IdMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		final List<String> idsOriginal = original.streamChildren()
				.map(child -> child.getProperty(IdProperty.class).getId())
				.collect(Collectors.toList());

		final List<String> idsTarget = target.streamChildren()
				.map(child -> child.getProperty(IdProperty.class).getId())
				.collect(Collectors.toList());

		final ListTransformer transformer = new ListTransformer(idsOriginal, idsTarget);
		final List<ListTransformer.TransformOperation> transformations = transformer.calculateTransformations();

		transformations.addAll(mutateKeepChildren(nodeHandlers, original, target, transformer.getElementsKept()));

		final List<ReplaceOperation> replaceOperations = new ArrayList<>();
		final List<RemoveOperation> removeOperations = new ArrayList<>();
		final List<AddOperation> addOperations = new ArrayList<>();
		final List<SwapOperation> swapOperations = new ArrayList<>();

		transformations.forEach(transformation -> {
			if (transformation instanceof ListTransformer.ReplaceOperation) {
				final ListTransformer.ReplaceOperation replaceOperation = (ListTransformer.ReplaceOperation) transformation;
				final SUINode replacementNode = target.findChildUnsafe(replaceOperation.getElement());
				nodeHandlers.getFxNodeBuilder().build(replacementNode);
				replaceOperations.add(new ReplaceOperation(
						replaceOperation.getIndex(),
						replacementNode,
						original.getChild(replaceOperation.getIndex())));
			}
			if (transformation instanceof ListTransformer.RemoveOperations) {
				final ListTransformer.RemoveOperations removeTransformation = (ListTransformer.RemoveOperations) transformation;
				removeOperations.add(new RemoveOperation(
						removeTransformation.getIndex(),
						original.getChild(removeTransformation.getIndex())));
			}
			if (transformation instanceof ListTransformer.AddOperations) {
				final ListTransformer.AddOperations addTransformation = (ListTransformer.AddOperations) transformation;
				final SUINode addNode = target.findChildUnsafe(addTransformation.getElement());
				nodeHandlers.getFxNodeBuilder().build(addNode);
				addOperations.add(new AddOperation(
						addTransformation.getIndex(),
						addNode));
			}

			if (transformation instanceof ListTransformer.SwapOperation) {
				final ListTransformer.SwapOperation swapOperation = (ListTransformer.SwapOperation) transformation;
				swapOperations.add(new SwapOperation(
						swapOperation.getIndexMin(),
						swapOperation.getIndexMax()));
			}
		});

		if (!replaceOperations.isEmpty() || !removeOperations.isEmpty() || !addOperations.isEmpty() || !swapOperations.isEmpty()) {
			original.applyChildTransformations(replaceOperations, removeOperations, addOperations, swapOperations);
		}

		return BaseNodeMutator.MutationResult.MUTATED;
	}




	private List<ListTransformer.TransformOperation> mutateKeepChildren(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target,
																		final Set<Pair<String, Integer>> elementsKept) {

		List<ChildNodeKept> childNodesKept = elementsKept.stream()
				.map(pair -> {
					final SUINode childOriginal = original.findChildUnsafe(pair.getLeft());
					final SUINode childTarget = target.findChildUnsafe(pair.getLeft());
					return new ChildNodeKept(pair.getRight(), pair.getLeft(), childOriginal, childTarget);
				})
				.collect(Collectors.toList());

		AsyncChunkProcessor<ChildNodeKept, ListTransformer.TransformOperation> processor =
				new AsyncChunkProcessor<>(childNodesKept, target.childCount() / 12,
						chunk -> chunk.stream()
								.map(keepData -> {
									final SUINode childOriginal = keepData.getChildNode();
									final SUINode childTarget = keepData.getTargetNode();
									BaseNodeMutator.MutationResult result = nodeHandlers.getMutator().mutateNode(childOriginal, childTarget, nodeHandlers);
									if (result == BaseNodeMutator.MutationResult.REQUIRES_REBUILD) {
										return new ListTransformer.ReplaceOperation(keepData.getChildIndex(), keepData.getIdTarget());
									} else {
										return null;
									}
								})
								.filter(Objects::nonNull)
								.collect(Collectors.toList())
				);

		return new ArrayList<>(processor.get());
	}




	@Getter
	@AllArgsConstructor
	private static class ChildNodeKept {


		private final int childIndex;
		private final String idTarget;
		private final SUINode childNode;
		private final SUINode targetNode;


	}






	@Getter
	@AllArgsConstructor
	public static abstract class Operation {


		private final int cost;




		public abstract void apply(List<SUINode> list);


		public abstract void apply(Map<String, SUINode> map);

		public abstract void apply(Pane pane);


	}






	@Getter
	public static class AddOperation extends Operation {


		private final int index;

		private final SUINode node;




		public AddOperation(int index, SUINode node) {
			super(1);
			this.index = index;
			this.node = node;
		}




		@Override
		public void apply(final List<SUINode> list) {
			list.add(index, node);
		}




		@Override
		public void apply(final Map<String, SUINode> map) {
			map.put(node.getProperty(IdProperty.class).getId(), node);
		}




		@Override
		public void apply(final Pane pane) {
			pane.getChildren().add(index, node.getFxNode());
		}

	}






	@Getter
	public static class RemoveOperation extends Operation {


		private final int index;

		private final SUINode node;




		public RemoveOperation(int index, SUINode node) {
			super(1);
			this.index = index;
			this.node = node;
		}




		@Override
		public void apply(final List<SUINode> list) {
			list.remove(index);
		}




		@Override
		public void apply(final Map<String, SUINode> map) {
			map.remove(node.getProperty(IdProperty.class).getId());
		}




		@Override
		public void apply(final Pane pane) {
			pane.getChildren().remove(index);
		}

	}






	@Getter
	public static class SwapOperation extends Operation {


		private final int indexMin;

		private final int indexMax;




		public SwapOperation(int indexMin, int indexMax) {
			super(3);
			this.indexMin = indexMin;
			this.indexMax = indexMax;
		}




		@Override
		public void apply(final List<SUINode> list) {
			SUINode elementMax = list.remove(indexMax);
			SUINode elementMin = list.set(indexMin, elementMax);
			list.add(indexMax, elementMin);
		}




		@Override
		public void apply(final Map<String, SUINode> map) {
			// do nothing here
		}




		@Override
		public void apply(final Pane pane) {
			Node elementMax = pane.getChildren().remove(indexMax);
			Node elementMin = pane.getChildren().set(indexMin, elementMax);
			pane.getChildren().add(indexMax, elementMin);
		}


	}






	@Getter
	public static class ReplaceOperation extends Operation {


		private final int index;

		private final SUINode node;

		private final SUINode replaced;




		public ReplaceOperation(int index, SUINode node, SUINode replaced) {
			super(1);
			this.index = index;
			this.node = node;
			this.replaced = replaced;
		}




		@Override
		public void apply(final List<SUINode> list) {
			list.set(index, node);
		}




		@Override
		public void apply(final Map<String, SUINode> map) {
			map.remove(replaced.getProperty(IdProperty.class).getId());
			map.put(node.getProperty(IdProperty.class).getId(), node);
		}




		@Override
		public void apply(final Pane pane) {
			pane.getChildren().set(index, node.getFxNode());
		}

	}


}
