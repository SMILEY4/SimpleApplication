package de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies;

import de.ruegnerlukas.simpleapplication.common.Sampler;
import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class IdMutationStrategy implements ChildNodesMutationStrategy {


	@Override
	public BaseNodeMutator.MutationResult mutate(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		Sampler.Sample samplerMapIds = Sampler.start("samplerMapIds " + original.childCount());
		final List<String> idsOriginal = original.streamChildren()
				.map(child -> child.getProperty(IdProperty.class).getId())
				.collect(Collectors.toList());
		final List<String> idsTarget = target.streamChildren()
				.map(child -> child.getProperty(IdProperty.class).getId())
				.collect(Collectors.toList());
		samplerMapIds.stop();

		final ListTransformer transformer = new ListTransformer(idsOriginal, idsTarget);
		final List<ListTransformer.TransformOperation> transformations = transformer.calculateTransformations();

		transformations.addAll(mutateKeepChildren(nodeHandlers, original, target, transformer.getElementsKept()));

		Sampler.Sample samplerConvert = Sampler.start("convertT2O");
		final List<Operation> operations = new ArrayList<>();
		transformations.forEach(transformation -> {
			if (transformation instanceof ListTransformer.ReplaceOperation) {
				final ListTransformer.ReplaceOperation replaceOperation = (ListTransformer.ReplaceOperation) transformation;
				final SUINode replacementNode = target.findChild(replaceOperation.getElement()).orElse(null);
				nodeHandlers.getFxNodeBuilder().build(replacementNode);
				operations.add(new ReplaceOperation(
						replaceOperation.getIndex(),
						replacementNode,
						original.getChild(replaceOperation.getIndex())));
			}
			if (transformation instanceof ListTransformer.RemoveOperations) {
				final ListTransformer.RemoveOperations removeTransformation = (ListTransformer.RemoveOperations) transformation;
				operations.add(new RemoveOperation(
						removeTransformation.getIndex(),
						original.getChild(removeTransformation.getIndex())));
			}
			if (transformation instanceof ListTransformer.AddOperations) {
				final ListTransformer.AddOperations addTransformation = (ListTransformer.AddOperations) transformation;
				final SUINode addNode = target.findChild(addTransformation.getElement()).orElse(null);
				nodeHandlers.getFxNodeBuilder().build(addNode);
				operations.add(new AddOperation(
						addTransformation.getIndex(),
						addNode));
			}

			if (transformation instanceof ListTransformer.SwapOperation) {
				final ListTransformer.SwapOperation swapOperation = (ListTransformer.SwapOperation) transformation;
				operations.add(new SwapOperation(
						swapOperation.getIndexMin(),
						swapOperation.getIndexMax()));
			}
		});
		samplerConvert.stop();

		Sampler.Sample samplerApply = Sampler.start("apply " + operations.size());
		if (!operations.isEmpty()) {
			original.applyChildTransformations(operations);
		}
		samplerApply.stop();

		return BaseNodeMutator.MutationResult.MUTATED;
	}




	private List<ListTransformer.TransformOperation> mutateKeepChildren(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target,
																		final Set<ListTransformer.Pair<String, Integer>> elementsKept) {

		final List<ListTransformer.TransformOperation> transformations = new ArrayList<>();

		Sampler.Sample samplerReplace = Sampler.start("replaceOp " + elementsKept.size());
		elementsKept.forEach(pair -> {
			Sampler.Sample samplerReplaceIn = Sampler.start("replaceOpIter " + elementsKept.size());
			final SUINode childOriginal = original.findChild(pair.getLeft()).orElse(null);
			final SUINode childTarget = target.findChild(pair.getLeft()).orElse(null);
			BaseNodeMutator.MutationResult result = nodeHandlers.getMutator().mutateNode(childOriginal, childTarget, nodeHandlers);
			if (result == BaseNodeMutator.MutationResult.REQUIRES_REBUILD) {
				transformations.add(new ListTransformer.ReplaceOperation(pair.getRight(), pair.getLeft()));
			}
			samplerReplaceIn.stop();
		});
		samplerReplace.stop();

		return transformations;
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
	static class AddOperation extends Operation {


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
	static class RemoveOperation extends Operation {


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
	static class SwapOperation extends Operation {


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
	static class ReplaceOperation extends Operation {


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
