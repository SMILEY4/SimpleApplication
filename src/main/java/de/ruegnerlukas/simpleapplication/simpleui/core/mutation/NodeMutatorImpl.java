package de.ruegnerlukas.simpleapplication.simpleui.core.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.profiler.SuiProfiler;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour;
import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.MUTATED;
import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.REQUIRES_REBUILD;

public class NodeMutatorImpl implements NodeMutator {


	/**
	 * The strategy decider for mutation child nodes.
	 */
	private final MutationStrategyDecider strategyDecider;






	/**
	 * The state of a property in mutation.
	 */
	private enum PropertyState {

		/**
		 * The property was not changed.
		 */
		UNCHANGED,

		/**
		 * The property was removed completely.
		 */
		REMOVED,

		/**
		 * The property was just added.
		 */
		ADDED,

		/**
		 * The property was modified.
		 */
		UPDATED

	}




	/**
	 * @param mutationStrategies the strategies for mutating child nodes
	 */
	public NodeMutatorImpl(final List<ChildNodesMutationStrategy> mutationStrategies) {
		this.strategyDecider = new MutationStrategyDecider(mutationStrategies);
	}




	@Override
	public MutationResult mutateNode(final SuiNode original, final SuiNode target) {
		final MutationBehaviour mutationBehaviour = getMutationBehaviour(original);

		if (mutationBehaviour == MutationBehaviour.DEFAULT && original.getNodeType() != target.getNodeType()) {
			return REQUIRES_REBUILD;
		}

		if (mutationBehaviour == MutationBehaviour.DEFAULT) {
			if (mutateProperties(original, target) == REQUIRES_REBUILD) {
				return REQUIRES_REBUILD;
			}
		}

		if (mutationBehaviour != MutationBehaviour.STATIC_SUBTREE) {
			if (mutateChildren(original, target) == REQUIRES_REBUILD) {
				return REQUIRES_REBUILD;
			}
		}

		return MUTATED;
	}




	/**
	 * Tries to mutate the properties (and fx-node) of the given original node to match the given target node.
	 * Ignores {@link ItemListProperty} and {@link ItemProperty}. These have to be handled separately.
	 * See {@link NodeMutatorImpl#mutateChildren}.
	 *
	 * @param original the original node
	 * @param target   the target node to match
	 */
	private MutationResult mutateProperties(final SuiNode original, final SuiNode target) {

		final Set<Class<? extends SuiProperty>> commonProperties = getCommonProperties(original, target);

		for (Class<? extends SuiProperty> key : commonProperties) {
			if (shouldSkipPropertyMutation(key)) {
				continue;
			}

			final SuiProperty propOriginal = original.getPropertyStore().get(key);
			final SuiProperty propTarget = target.getPropertyStore().get(key);
			if (isUnchanged(propOriginal, propTarget)) {
				continue;
			}

			final PropFxNodeUpdater<SuiProperty, Node> updater = getPropNodeUpdater(original.getNodeType(), key);
			if (updater == null || mutateProperty(original, propOriginal, propTarget, updater) == REQUIRES_REBUILD) {
				return REQUIRES_REBUILD;
			}

		}

		return MUTATED;
	}




	/**
	 * Mutate the given property.
	 *
	 * @param original     the original node
	 * @param propOriginal the original property
	 * @param propTarget   the target property
	 * @param updater      the node updater
	 * @return whether the node has to be rebuild or was mutated successfully
	 */
	private MutationResult mutateProperty(final SuiNode original,
										  final SuiProperty propOriginal,
										  final SuiProperty propTarget,
										  final PropFxNodeUpdater<SuiProperty, Node> updater) {
		switch (getPropertyState(propOriginal, propTarget)) {

			case REMOVED:
				SuiProfiler.get().countPropertyRemoved();
				if (updater.remove(propOriginal, original, original.getFxNodeStore().get()) == REQUIRES_REBUILD) {
					return REQUIRES_REBUILD;
				} else {
					original.getPropertyStore().remove(propOriginal.getKey());
					return MUTATED;
				}

			case ADDED:
			case UPDATED:
				SuiProfiler.get().countPropertyUpsert();
				if (updater.update(propTarget, original, original.getFxNodeStore().get()) == REQUIRES_REBUILD) {
					return REQUIRES_REBUILD;
				} else {
					original.getPropertyStore().upsert(propTarget);
					return MUTATED;
				}

			case UNCHANGED:
			default:
				return MUTATED;
		}
	}




	/**
	 * Get the state of the property (-type) during mutation. Both properties must be of the same type (or null).
	 *
	 * @param original the original property or null
	 * @param target   the target property or null
	 * @return the {@link PropertyState}
	 */
	private PropertyState getPropertyState(final SuiProperty original, final SuiProperty target) {
		if (isRemoved(original, target)) {
			return PropertyState.REMOVED;
		}
		if (isAdded(original, target)) {
			return PropertyState.ADDED;
		}
		if (isUpdated(original, target)) {
			return PropertyState.UPDATED;
		}
		return PropertyState.UNCHANGED;
	}




	/**
	 * Tries to mutate the children ({@link ItemListProperty} and {@link ItemProperty})
	 * of the given original node to match the given target node.
	 *
	 * @param original the original node
	 * @param target   the target node to match
	 * @return the result of the mutation
	 */
	private MutationResult mutateChildren(final SuiNode original, final SuiNode target) {
		return strategyDecider.mutate(original, target);
	}




	/**
	 * @param a the first node
	 * @param b the other node
	 * @return the set of property-key the two given nodes have in common.
	 */
	private Set<Class<? extends SuiProperty>> getCommonProperties(final SuiNode a, final SuiNode b) {
		final Set<Class<? extends SuiProperty>> properties = new HashSet<>(a.getPropertyStore().getTypes());
		properties.addAll(b.getPropertyStore().getTypes());
		return properties;
	}




	/**
	 * @param propertyKey the key of the property
	 * @return whether the given property type should be skipped during property mutation.
	 */
	private boolean shouldSkipPropertyMutation(final Class<? extends SuiProperty> propertyKey) {
		return propertyKey == ItemListProperty.class || propertyKey == ItemProperty.class;
	}




	/**
	 * Get the mutation behaviour from the given node.
	 * Returns the default behaviour if the property was not added to the given node.
	 *
	 * @param node the node
	 * @return the {@link MutationBehaviour}.
	 */
	private MutationBehaviour getMutationBehaviour(final SuiNode node) {
		return node.getPropertyStore().getSafe(MutationBehaviourProperty.class)
				.map(MutationBehaviourProperty::getBehaviour)
				.orElse(MutationBehaviour.DEFAULT);
	}




	/**
	 * @param nodeType the identifying type of the node
	 * @param propType the identifying type of the property
	 * @return the {@link PropFxNodeUpdater} for the given property and given node type
	 */
	private PropFxNodeUpdater<SuiProperty, Node> getPropNodeUpdater(final Class<?> nodeType, final Class<? extends SuiProperty> propType) {
		@SuppressWarnings ("unchecked") final PropFxNodeUpdater<SuiProperty, Node> updater =
				(PropFxNodeUpdater<SuiProperty, Node>) SuiRegistry.get()
						.getEntry(nodeType)
						.getPropFxNodeUpdaters().get(propType);
		return updater;
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
	 * Check whether the property is unchanged (i.e. both exist and are equal).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property is unchanged.
	 */
	private boolean isUnchanged(final SuiProperty original, final SuiProperty target) {
		return (original != null) && (target != null) && (original.isPropertyEqual(target));
	}




	/**
	 * Check whether the property was changed (i.e. both exist and are not equal).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property was changed.
	 */
	private boolean isUpdated(final SuiProperty original, final SuiProperty target) {
		return (original != null) && (target != null) && (!original.isPropertyEqual(target));
	}

}
