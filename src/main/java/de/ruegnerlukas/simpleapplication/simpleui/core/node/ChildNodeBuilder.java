package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.utils.LoopUtils;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ChildNodeBuilder {


	/**
	 * The default child node builder instance.
	 */
	public static final ChildNodeBuilder DEFAULT = new ChildNodeBuilder();


	/**
	 * The amount of child nodes that have to be created to use the async processing.
	 */
	private static final int CREATE_CHILD_LIST_ASYNC_CUTOFF = 1028;




	/**
	 * Creates child nodes from the given properties of the (parent) node.
	 *
	 * @param properties all properties of the (parent) node
	 * @return the created child nodes
	 */
	public List<SuiNode> build(final SuiState state, final List<SuiProperty> properties, final Tags tags) {
		if (checkEarlyOut(properties, tags)) {
			return List.of();
		}
		for (SuiProperty property : properties) {
			if (property.getKey() == ItemListProperty.class) {
				return fromItemListProperty(state, (ItemListProperty) property, tags);
			}
			if (property.getKey() == ItemProperty.class) {
				return fromItemProperty(state, (ItemProperty) property, tags);
			}
		}
		return List.of();
	}




	/**
	 * Check if the building of the node tree can be stopped early.
	 *
	 * @param properties the properties of the parent node
	 * @return whether we can stop building the tree
	 */
	private boolean checkEarlyOut(final List<SuiProperty> properties, final Tags tags) {

		if (tags == null) {
			// null = we do not mutate -> no early out here
			return false;
		}

		final MutationBehaviourProperty property = findMutationBehaviourProperty(properties);
		final MutationBehaviour mutationBehaviour = property == null ? MutationBehaviour.DEFAULT : property.getBehaviour();
		final TagConditionExpression tagCondition = property == null ? null : property.getCondition();

		if (mutationBehaviour == MutationBehaviour.STATIC) {
			if (tagCondition != null) {
				return !tagCondition.matches(tags);
			} else {
				return true;
			}
		} else {
			return false;
		}
	}




	/**
	 * @param properties the list of properties
	 * @return the property or null
	 */
	private MutationBehaviourProperty findMutationBehaviourProperty(final List<SuiProperty> properties) {
		for (SuiProperty property : properties) {
			if (property.getKey() == MutationBehaviourProperty.class) {
				return (MutationBehaviourProperty) property;
			}
		}
		return null;
	}




	/**
	 * Create the child nodes from given item list property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item list property
	 */
	private List<SuiNode> fromItemListProperty(final SuiState state, final ItemListProperty property, final Tags tags) {
		if (property.getFactories().size() < CREATE_CHILD_LIST_ASYNC_CUTOFF) {
			return property.getFactories().stream()
					.map(factory -> factory.create(state, tags))
					.collect(Collectors.toList());
		} else {
			return LoopUtils.asyncCollectingLoop(property.getFactories(), factory -> factory.create(state, tags));
		}
	}




	/**
	 * Create the child nodes from given item property and the given state.
	 *
	 * @param state    the current state
	 * @param property the item property
	 */
	private List<SuiNode> fromItemProperty(final SuiState state, final ItemProperty property, final Tags tags) {
		return Optional.ofNullable(property.getFactory())
				.map(factory -> factory.create(state, tags))
				.map(List::of)
				.orElse(List.of());
	}


}
