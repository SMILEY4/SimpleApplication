package de.ruegnerlukas.simpleapplication.simpleui.mutation;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdater;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import javafx.scene.Node;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.MUTATED;
import static de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator.MutationResult.REBUILD;

public class NodeMutator implements BaseNodeMutator {


	@Override
	public MutationResult mutateNode(final SNode original, final SNode target, final SceneContext context) {
		if (mutateProperties(context, original, target) == REBUILD) {
			return REBUILD;
		}
		if (mutateChildren(context, original, target) == REBUILD) {
			return REBUILD;
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
	private MutationResult mutateProperties(final SceneContext context, final SNode original, final SNode target) {

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
					if (updater.remove(context, propOriginal, original, original.getFxNode()) == REBUILD) {
						return REBUILD;
					}
				}
				if (isAdded(propOriginal, propTarget) || isUpdated(propOriginal, propTarget)) {
					if (updater.update(context, propTarget, original, original.getFxNode()) == REBUILD) {
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
	 * @param original the original node
	 * @param target   the target node to match
	 */
	private MutationResult mutateChildren(final SceneContext context, final SNode original, final SNode target) {

		// TODO: optimize: make use of id property
		boolean childrenChanged = false;
		for (int i = 0; i < Math.max(original.getChildren().size(), target.getChildren().size()); i++) {
			final SNode childOriginal = original.getChildren().size() <= i ? null : original.getChildren().get(i);
			final SNode childTarget = target.getChildren().size() <= i ? null : target.getChildren().get(i);

			if (childOriginal != null && childTarget != null) {
				SNode childMutated = context.getMutator().mutate(childOriginal, childTarget);
				original.getChildren().set(i, childMutated);
				childrenChanged = true;
			}
			if (childOriginal != null && childTarget == null) {
				// to remove inside the loop: set to null, remove nulls later
				original.getChildren().set(i, null);
				childrenChanged = true;
			}
			if (childOriginal == null && childTarget != null) {
				context.getFxNodeBuilder().build(childTarget);
				if (i < original.getChildren().size()) {
					original.getChildren().set(i, childTarget);
				} else {
					original.getChildren().add(childTarget);
				}
				childrenChanged = true;
			}
		}
		original.getChildren().removeAll(Collections.singleton(null));

		if (childrenChanged) {
			original.triggerChildListChange();
		}

		return MUTATED;
	}




	/**
	 * @param nodeType the identifying type of the node
	 * @param propType the identifying type of the property
	 * @return the {@link PropFxNodeUpdater} for the given property and given node type
	 */
	private PropFxNodeUpdater<Property, Node> getPropNodeUpdater(final Class<?> nodeType, final Class<? extends Property> propType) {
		return (PropFxNodeUpdater<Property, Node>) SimpleUIRegistry.get()
				.getEntry(nodeType)
				.getPropFxNodeUpdaters().get(propType);
	}




	/**
	 * Check whether the property was added (i.e. only the target property exists).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property was added.
	 */
	private boolean isAdded(final Property original, final Property target) {
		return (original == null) && (target != null);
	}




	/**
	 * Check whether the property was removed (i.e. only the original property exists).
	 *
	 * @param original the original property
	 * @param target   the target property
	 * @return whether the property was removed.
	 */
	private boolean isRemoved(final Property original, final Property target) {
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
