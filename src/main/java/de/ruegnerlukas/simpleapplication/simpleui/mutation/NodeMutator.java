package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.stategies.ChildNodesMutationStrategy;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult.MUTATED;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult.REQUIRES_REBUILD;
import static de.ruegnerlukas.simpleapplication.simpleui.properties.MutationBehaviourProperty.MutationBehaviour;

public class NodeMutator implements BaseNodeMutator {


	/**
	 * The strategy decider for mutation child nodes
	 */
	private final MutationStrategyDecider strategyDecider;




	/**
	 * @param mutationStrategies the strategies for mutating child nodes
	 */
	public NodeMutator(final List<ChildNodesMutationStrategy> mutationStrategies) {
		this.strategyDecider = new MutationStrategyDecider(mutationStrategies);
	}




	@Override
	public MutationResult mutateNode(final SuiNode original, final SuiNode target, final MasterNodeHandlers nodeHandlers) {
		final MutationBehaviour mutationBehaviour = getMutationBehaviour(original);
		if (mutationBehaviour == MutationBehaviour.DEFAULT) {
			if (mutateProperties(nodeHandlers, original, target) == REQUIRES_REBUILD) {
				return REQUIRES_REBUILD;
			}
		}

		if (mutationBehaviour != MutationBehaviour.STATIC_SUBTREE) {
			if (mutateChildren(nodeHandlers, original, target) == REQUIRES_REBUILD) {
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
	 * @param nodeHandlers the primary node handlers
	 * @param original     the original node
	 * @param target       the target node to match
	 */
	private MutationResult mutateProperties(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {

		final Set<Class<? extends Property>> commonProperties = getCommonProperties(original, target);

		for (Class<? extends Property> key : commonProperties) {
			if (shouldSkipPropertyMutation(key)) {
				continue;
			}

			final Property propOriginal = original.getProperty(key);
			final Property propTarget = target.getProperty(key);
			if (isUnchanged(propOriginal, propTarget)) {
				continue;
			}

			final PropFxNodeUpdater<Property, Node> updater = getPropNodeUpdater(original.getNodeType(), key);
			if (updater == null) {
				return REQUIRES_REBUILD;
			} else {
				if (isRemoved(propOriginal, propTarget)) {
					if (updater.remove(nodeHandlers, propOriginal, original, original.getFxNode()) == REQUIRES_REBUILD) {
						return REQUIRES_REBUILD;
					}
				}
				if (isAdded(propOriginal, propTarget) || isUpdated(propOriginal, propTarget)) {
					if (updater.update(nodeHandlers, propTarget, original, original.getFxNode()) == REQUIRES_REBUILD) {
						return REQUIRES_REBUILD;
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
	private MutationResult mutateChildren(final MasterNodeHandlers nodeHandlers, final SuiNode original, final SuiNode target) {
		return strategyDecider.mutate(nodeHandlers, original, target);
	}




	/**
	 * @param a the first node
	 * @param b the other node
	 * @return the set of property-key the two given nodes have in common.
	 */
	private Set<Class<? extends Property>> getCommonProperties(final SuiNode a, final SuiNode b) {
		final Set<Class<? extends Property>> properties = new HashSet<>(a.getProperties().keySet());
		properties.addAll(b.getProperties().keySet());
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
	private MutationBehaviour getMutationBehaviour(final SuiNode node) {
		return node.getPropertySafe(MutationBehaviourProperty.class)
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
