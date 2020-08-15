package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.MUTATED;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.REBUILD;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty.MutationBehaviour;

public class NodeMutator implements BaseNodeMutator {


	@Override
	public MutationResult mutateNode(final SUINode original, final SUINode target, final MasterNodeHandlers nodeHandlers) {
		final MutationBehaviour mutationBehaviour = getMutationBehaviour(original);
		if (mutationBehaviour == MutationBehaviour.DEFAULT) {
			if (mutateProperties(nodeHandlers, original, target) == REBUILD) {
				return REBUILD;
			}
		}
		if (mutationBehaviour != MutationBehaviour.STATIC_SUBTREE) {
			if (mutateChildren(nodeHandlers, original, target) == REBUILD) {
				return REBUILD;
			}
		}
		return MUTATED;
	}




	/**
	 * Get the mutation behaviour from the given node.
	 * Returns the default behaviour if the property was not added to the given node.
	 *
	 * @param node the node
	 * @return the {@link MutationBehaviour}.
	 */
	private MutationBehaviour getMutationBehaviour(final SUINode node) {
		return node.getPropertySafe(MutationBehaviourProperty.class)
				.map(MutationBehaviourProperty::getBehaviour)
				.orElse(MutationBehaviour.DEFAULT);
	}




	/**
	 * Tries to mutate the properties (and fx-node) of the given original node to match the given target node.
	 * Ignores {@link ItemListProperty} and {@link ItemProperty}. These have to be handled separately.
	 * See {@link NodeMutator#mutateChildren}.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 */
	private MutationResult mutateProperties(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		final Set<Class<? extends Property>> commonProperties = new HashSet<>();
		commonProperties.addAll(original.getProperties().keySet());
		commonProperties.addAll(target.getProperties().keySet());

		for (Class<? extends Property> key : commonProperties) {
			if (key == ItemListProperty.class || key == ItemProperty.class) {
				continue;
			}
			final Property propOriginal = original.getProperty(key);
			final Property propTarget = target.getProperty(key);

			if (isUnchanged(propOriginal, propTarget)) {
				continue;
			}

			final PropFxNodeUpdater<Property, Node> updater = getPropNodeUpdater(original.getNodeType(), key);
			if (updater == null) {
				return REBUILD;
			} else {
				if (isRemoved(propOriginal, propTarget)) {
					if (updater.remove(nodeHandlers, propOriginal, original, original.getFxNode()) == REBUILD) {
						return REBUILD;
					}
				}
				if (isAdded(propOriginal, propTarget) || isUpdated(propOriginal, propTarget)) {
					if (updater.update(nodeHandlers, propTarget, original, original.getFxNode()) == REBUILD) {
						return REBUILD;
					}
				}
				original.getProperties().put(propTarget.getKey(), propTarget);
			}

		}

		return MUTATED;
	}




	/**
	 * Tries to mutate the children ({@link ItemListProperty} and {@link ItemProperty})
	 * of the given original node to match the given target node.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	private MutationResult mutateChildren(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {
		if (original.hasChildren() || target.hasChildren()) {
			if (allChildrenHaveId(original) && allChildrenHaveId(target)) {
				return mutateChildrenWithIdProp(nodeHandlers, original, target);
			} else {
				return mutateChildrenNoIdProp(nodeHandlers, original, target);
			}
		} else {
			return MUTATED;
		}
	}




	/**
	 * Checks whether all children of the given node have a valid id
	 *
	 * @param parent the parent to check
	 * @return whether all nodes have a valid id
	 */
	private boolean allChildrenHaveId(final SUINode parent) {
		return parent.streamChildren().allMatch(node -> node.hasProperty(IdProperty.class));
	}




	/**
	 * This method will be used to mutate the children of the given original node
	 * when some child nodes (original and target) are missing an id property.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	private MutationResult mutateChildrenNoIdProp(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {

		final List<SUINode> newChildList = new ArrayList<>();

		boolean childrenChanged = false; // todo: isn't this always true ?
		for (int i = 0; i < Math.max(original.childCount(), target.childCount()); i++) {
			final SUINode childTarget = target.childCount() <= i ? null : target.getChild(i);
			final SUINode childOriginal = original.childCount() <= i ? null : original.getChild(i);


			if (isRemoved(childOriginal, childTarget)) {
				childrenChanged = true;
				continue;
			}

			if (isAdded(childOriginal, childTarget)) {
				nodeHandlers.getFxNodeBuilder().build(childTarget);
				newChildList.add(childTarget);
				childrenChanged = true;
				continue;
			}

			if (notAddedOrRemoved(childOriginal, childTarget)) {
				SUINode childMutated = nodeHandlers.getMutator().mutate(childOriginal, childTarget);
				newChildList.add(childMutated);
				childrenChanged = true;
			}

		}

		original.setChildren(newChildList, childrenChanged);
		return MUTATED;
	}




	/**
	 * This method will be used to mutate the children of the given original node
	 * when all child nodes (original and target) have an id property.
	 *
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 * @return the result of the mutation
	 */
	private MutationResult mutateChildrenWithIdProp(final MasterNodeHandlers nodeHandlers, final SUINode original, final SUINode target) {
		final List<SUINode> newChildList = new ArrayList<>();
		for (int i = 0; i < target.childCount(); i++) {
			final SUINode childTarget = target.getChild(i);
			final String targetId = childTarget.getProperty(IdProperty.class).getId();
			final SUINode resultingNode = original.findChild(targetId)
					.map(childOriginal -> nodeHandlers.getMutator().mutate(childOriginal, childTarget))
					.orElseGet(() -> {
						nodeHandlers.getFxNodeBuilder().build(childTarget);
						return childTarget;
					});
			newChildList.add(resultingNode);
		}
		original.setChildren(newChildList, true);
		return MUTATED;
	}




	/**
	 * @param nodeType the identifying type of the node
	 * @param propType the identifying type of the property
	 * @return the {@link PropFxNodeUpdater} for the given property and given node type
	 */
	private PropFxNodeUpdater<Property, Node> getPropNodeUpdater(final Class<?> nodeType, final Class<? extends Property> propType) {
		return (PropFxNodeUpdater<Property, Node>) SUIRegistry.get()
				.getEntry(nodeType)
				.getPropFxNodeUpdaters().get(propType);
	}




	/**
	 * Check whether the object was added (i.e. only the target object exists).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return whether the object was added.
	 */
	private boolean isAdded(final Object original, final Object target) {
		return (original == null) && (target != null);
	}




	/**
	 * Check whether the object was removed (i.e. only the original object exists).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return whether the object was removed.
	 */
	private boolean isRemoved(final Object original, final Object target) {
		return (original != null) && (target == null);
	}




	/**
	 * Check whether the object was neither removed nor added (both != null).
	 *
	 * @param original the original object
	 * @param target   the target object
	 * @return true, if the object was neither removed nor added
	 */
	private boolean notAddedOrRemoved(final Object original, final Object target) {
		return (original != null) && (target != null);
	}




	/**
	 * Check whether the property is unchanged (i.e. both exist and are equal).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property is unchanged.
	 */
	private boolean isUnchanged(final Property original, final Property target) {
		return (original != null) && (target != null) && (original.isEqual(target));
	}




	/**
	 * Check whether the property was changed (i.e. both exist and are not equal).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property was changed.
	 */
	private boolean isUpdated(final Property original, final Property target) {
		return (original != null) && (target != null) && (!original.isEqual(target));
	}

}
