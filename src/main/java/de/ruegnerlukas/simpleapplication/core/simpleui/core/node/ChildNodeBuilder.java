package de.ruegnerlukas.simpleapplication.core.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.common.utils.LoopUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemListProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.ItemProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.MutationBehaviourProperty.MutationBehaviour;
import de.ruegnerlukas.simpleapplication.common.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
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
		if (checkForEarlyOut(properties, tags)) {
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
	private boolean checkForEarlyOut(final List<SuiProperty> properties, final Tags tags) {

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
		List<SuiNode> childNodes;
		final List<NodeFactory> factories = property.getFactories();
		if (factories.size() < CREATE_CHILD_LIST_ASYNC_CUTOFF) {
			childNodes = factories.stream()
					.map(factory -> factory.create(state, tags))
					.collect(Collectors.toList());
		} else {
			childNodes = LoopUtils.asyncCollectingLoop(factories, factory -> factory.create(state, tags));
		}

		if (checkValidIds(childNodes)) {
			return childNodes;
		} else {
			Validations.STATE.fail().exception("The ids of the child nodes must be unique.");
			return List.of();
		}
	}




	/**
	 * @param nodes the nodes
	 * @return whether the ids of the given nodes are valid, i.e. no duplicate ids
	 */
	private boolean checkValidIds(final List<SuiNode> nodes) {
		final Set<String> uniqueIds = new HashSet<>();
		final Set<String> duplicateIds = nodes.stream()
				.map(node -> node.getPropertyStore().getIdUnsafe())
				.filter(Objects::nonNull)
				.filter(id -> !uniqueIds.add(id))
				.collect(Collectors.toSet());
		if (!duplicateIds.isEmpty()) {
			log.warn("Found duplicate ids: {}.", duplicateIds);
		}
		return duplicateIds.isEmpty();
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
