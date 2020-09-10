package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

/**
 * (mutate parent) / (mutate children)
 * +----------------+-----------+----------------+-----------------------+
 * |                | NO FILTER | FILTER MATCHES | FILTER DOES NOT MATCH |
 * | -------------- + --------- + -------------- + --------------------- |
 * | DEFAULT        | yes / yes | yes / yes      | yes / yes             |
 * | STATIC NODE    |  no / yes | yes / yes      |  no / yes             |
 * | STATIC SUBTREE | yes /  no | yes / yes      | yes /  no             |
 * | STATIC         |  no /  no | yes / yes      |  no /  no             |
 * +----------------+-----------+----------------+-----------------------+
 **/
public class MutationBehaviourProperty extends SuiProperty {


	/**
	 * The possible behaviours.
	 */
	public enum MutationBehaviour {

		/**
		 * Node and its children nodes will be mutated.
		 */
		DEFAULT,

		/**
		 * This node (i.e. the properties of this node) will not be affected by mutations.
		 * Its children will still be mutated.
		 */
		STATIC_NODE,

		/**
		 * The children of this node will not be affected by mutations.
		 */
		STATIC_SUBTREE,

		/**
		 * Neither this node nor the child nodes will not be affected by mutations.
		 */
		STATIC

	}






	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<MutationBehaviourProperty, MutationBehaviourProperty, Boolean> COMPARATOR =
			(a, b) -> a.getBehaviour() == b.getBehaviour() && Objects.equals(a.getCondition(), b.getCondition());

	/**
	 * The behaviour.
	 */
	@Getter
	private final MutationBehaviour behaviour;

	/**
	 * The filter for tag values (or null for no additional filter).
	 * If the condition matches the given tags the behaviour will be overwritten / i.e. behaves like DEFAULT.
	 * See table at {@link MutationBehaviourProperty}.
	 */
	@Getter
	private final TagConditionExpression condition;




	/**
	 * @param behaviour the behaviour
	 * @param condition The filter for tag values (or null for no additional filter).
	 *                  See {@link MutationBehaviourProperty#condition} or table at {@link MutationBehaviourProperty}.
	 */
	public MutationBehaviourProperty(final MutationBehaviour behaviour, final TagConditionExpression condition) {
		super(MutationBehaviourProperty.class, COMPARATOR);
		this.behaviour = behaviour;
		this.condition = condition;
	}


}



