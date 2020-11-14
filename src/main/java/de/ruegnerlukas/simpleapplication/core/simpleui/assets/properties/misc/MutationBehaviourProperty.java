package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.tags.TagConditionExpression;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.List;
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
	 */
	public MutationBehaviourProperty(final MutationBehaviour behaviour) {
		this(behaviour, null);
	}




	/**
	 * @param behaviour the behaviour
	 * @param condition The filter for tag values (or null for no additional filter).
	 *                  See {@link MutationBehaviourProperty#condition} or table at {@link MutationBehaviourProperty}.
	 */
	public MutationBehaviourProperty(final MutationBehaviour behaviour, final TagConditionExpression condition) {
		super(MutationBehaviourProperty.class, COMPARATOR);
		Validations.INPUT.notNull(behaviour).exception("The behaviour may not be null.");
		this.behaviour = behaviour;
		this.condition = condition;
	}




	/**
	 * Get the mutation behaviour from the given node.
	 * Returns the default behaviour if the property was not added to the given node.
	 *
	 * @param node the node
	 * @return the {@link MutationBehaviour}.
	 */
	public static MutationBehaviour getMutationBehaviour(final SuiNode node) {
		return node.getPropertyStore().getSafe(MutationBehaviourProperty.class)
				.map(MutationBehaviourProperty::getBehaviour)
				.orElse(MutationBehaviour.DEFAULT);
	}




	/**
	 * Get the mutation behaviour from the given properties.
	 * Returns the default behaviour if the property was not added to the given store.
	 *
	 * @param properties the properties
	 * @return the {@link MutationBehaviour}.
	 */
	public static MutationBehaviour getMutationBehaviour(final List<SuiProperty> properties) {
		return properties.stream()
				.filter(prop -> prop.getKey() == MutationBehaviourProperty.class)
				.map(prop -> (MutationBehaviourProperty) prop)
				.map(MutationBehaviourProperty::getBehaviour)
				.findAny()
				.orElse(MutationBehaviour.DEFAULT);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param behaviour the behaviour
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T mutationBehaviour(final MutationBehaviour behaviour) {
			getBuilderProperties().add(new MutationBehaviourProperty(behaviour));
			return (T) this;
		}


		/**
		 * @param behaviour the behaviour
		 * @param condition The filter for tag values (or null for no additional filter).
		 *                  See {@link MutationBehaviourProperty#condition} or table at {@link MutationBehaviourProperty}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T mutationBehaviour(final MutationBehaviour behaviour, final TagConditionExpression condition) {
			getBuilderProperties().add(new MutationBehaviourProperty(behaviour, condition));
			return (T) this;
		}

	}


}



