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

public class NodeMutator implements BaseNodeMutator {


	@Override
	public MutationResult mutateNode(final SNode original, final SNode target, final SceneContext context) {
		Set<Class<? extends Property>> commonProperties = new HashSet<>();
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

			PropFxNodeUpdater<Property, Node> propNodeUpdater = (PropFxNodeUpdater<Property, Node>) SimpleUIRegistry.get().getEntry(original.getNodeType()).getPropFxNodeUpdaters().get(key);
			if (propNodeUpdater == null) {
				return MutationResult.REBUILD;
			} else {
				if (isRemoved(propOriginal, propTarget)) {
					if (propNodeUpdater.remove(context, propOriginal, original, original.getFxNode()) == MutationResult.REBUILD) {
						return MutationResult.REBUILD;
					}
				}
				if (isAdded(propOriginal, propTarget) || isUpdated(propOriginal, propTarget)) {
					if (propNodeUpdater.update(context, propTarget, original, original.getFxNode()) == MutationResult.REBUILD) {
						return MutationResult.REBUILD;
					}
				}
				original.getProperties().put(propTarget.getKey(), propTarget);
			}

		}


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
				original.getChildren().set(i, null); // to remove inside the loop: set to null, remove nulls later
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

		return MutationResult.MUTATED;
	}




	private boolean isAdded(Property original, Property target) {
		return (original == null) && (target != null);
	}




	private boolean isRemoved(Property original, Property target) {
		return (original != null) && (target == null);
	}




	private boolean isUnchanged(Property original, Property target) {
		return (original != null) && (target != null) && (original.isEqual(target));
	}




	private boolean isUpdated(Property original, Property target) {
		return (original != null) && (target != null) && (!original.isEqual(target));
	}

}