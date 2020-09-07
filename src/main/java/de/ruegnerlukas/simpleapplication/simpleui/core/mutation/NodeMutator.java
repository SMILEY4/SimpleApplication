package de.ruegnerlukas.simpleapplication.simpleui.core.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour;
import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.MUTATED;
import static de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult.REQUIRES_REBUILD;

public class NodeMutator implements BaseNodeMutator {


	/**
	 * The strategy decider for mutation child nodes
	 */
	private final MutationStrategyDecider strategyDecider;






	private enum PropertyState {

		UNCHANGED,
		REMOVED,
		ADDED,
		UPDATED

	}




	/**
	 * @param mutationStrategies the strategies for mutating child nodes
	 */
	public NodeMutator(final List<ChildNodesMutationStrategy> mutationStrategies) {
		this.strategyDecider = new MutationStrategyDecider(mutationStrategies);
	}




	@Override
	public MutationResult mutateNode(final SuiBaseNode original, final SuiBaseNode target) {
		final MutationBehaviour mutationBehaviour = getMutationBehaviour(original);

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
	 * See {@link NodeMutator#mutateChildren}.
	 *
	 * @param original the original node
	 * @param target   the target node to match
	 */
	private MutationResult mutateProperties(final SuiBaseNode original, final SuiBaseNode target) {

		// TODO: refactoring
		//
		//  - split NodeMutator in ChildMutator and PropertyMutator
		//	- split mutateProperties(...) into mutateProperties() and mutateSingleProperty()

		final Set<Class<? extends Property>> commonProperties = getCommonProperties(original, target);

		for (Class<? extends Property> key : commonProperties) {
			if (shouldSkipPropertyMutation(key)) {
				continue;
			}

			final Property propOriginal = original.getPropertyStore().get(key);
			final Property propTarget = target.getPropertyStore().get(key);
			if (isUnchanged(propOriginal, propTarget)) {
				continue;
			}

			final PropFxNodeUpdater<Property, Node> updater = getPropNodeUpdater(original.getNodeType(), key);
			if (updater == null || mutateProperty(original, propOriginal, propTarget, updater) == REQUIRES_REBUILD) {
				return REQUIRES_REBUILD;
			}

		}

		return MUTATED;
	}




	private MutationResult mutateProperty(final SuiBaseNode original, final Property propOriginal,
										  final Property propTarget,
										  final PropFxNodeUpdater<Property, Node> updater) {
		final PropertyState propertyState = getPropertyState(propOriginal, propTarget);
		switch (propertyState) {

			case REMOVED:
				if (updater.remove(propOriginal, original, original.getFxNodeStore().get()) == REQUIRES_REBUILD) {
					return REQUIRES_REBUILD;
				} else {
					original.getProperties().put(propTarget.getKey(), propTarget); // todo
					return MUTATED;
				}

			case ADDED:
			case UPDATED:
				if (updater.update(propTarget, original, original.getFxNodeStore().get()) == REQUIRES_REBUILD) {
					return REQUIRES_REBUILD;
				} else {
					original.getProperties().put(propTarget.getKey(), propTarget); // todo
					return MUTATED;
				}

			case UNCHANGED:
			default:
				return MUTATED;
		}
	}




	private PropertyState getPropertyState(final Property original, final Property target) {
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
	private MutationResult mutateChildren(final SuiBaseNode original, final SuiBaseNode target) {
		return strategyDecider.mutate(original, target);
	}




	/**
	 * @param a the first node
	 * @param b the other node
	 * @return the set of property-key the two given nodes have in common.
	 */
	private Set<Class<? extends Property>> getCommonProperties(final SuiBaseNode a, final SuiBaseNode b) {
		final Set<Class<? extends Property>> properties = new HashSet<>(a.getPropertyStore().getTypes());
		properties.addAll(b.getPropertyStore().getTypes());
		return properties;
	}




	/**
	 * @param propertyKey the key of the property
	 * @return whether the given property type should be skipped during property mutation.
	 */
	private boolean shouldSkipPropertyMutation(final Class<? extends Property> propertyKey) {
		return propertyKey == ItemListProperty.class || propertyKey == ItemProperty.class;
	}




	/**
	 * Get the mutation behaviour from the given node.
	 * Returns the default behaviour if the property was not added to the given node.
	 *
	 * @param node the node
	 * @return the {@link MutationBehaviour}.
	 */
	private MutationBehaviour getMutationBehaviour(final SuiBaseNode node) {
		return node.getPropertyStore().getSafe(MutationBehaviourProperty.class)
				.map(MutationBehaviourProperty::getBehaviour)
				.orElse(MutationBehaviour.DEFAULT);
	}




	/**
	 * @param nodeType the identifying type of the node
	 * @param propType the identifying type of the property
	 * @return the {@link PropFxNodeUpdater} for the given property and given node type
	 */
	private PropFxNodeUpdater<Property, Node> getPropNodeUpdater(final Class<?> nodeType, final Class<? extends Property> propType) {
		return (PropFxNodeUpdater<Property, Node>) SuiRegistry.get()
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
