package de.ruegnerlukas.simpleapplication.simpleui.mutation;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

public interface PropertyMutator<T extends Property> {

	Class<? extends Property> getPropertyKey();

	BaseNodeMutator.MutationResult propertyAdded(SNode original, SNode target, T property, SceneContext context);

	BaseNodeMutator.MutationResult propertyRemoved(SNode original, SNode target, T property, SceneContext context);

	BaseNodeMutator.MutationResult propertyUpdated(SNode original, SNode target, T prevProp, T nextProp, SceneContext context);

}
