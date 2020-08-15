package de.ruegnerlukas.simpleapplication.simpleui.properties;


import lombok.Getter;

public class MutationBehaviourProperty extends Property {


	/**
	 * The possible behaviours.
	 */
	public enum MutationBehaviour {

		/**
		 * Node and its children will be mutated.
		 */
		DEFAULT,

		/**
		 * This node will not be affected by mutations. Its children will still be mutated.
		 */
		STATIC_NODE,

		/**
		 * This and its children node will not be affected by mutations.
		 */
		STATIC_SUBTREE;
	}






	/**
	 * The behaviour.
	 */
	@Getter
	private final MutationBehaviour behaviour;




	/**
	 * @param behaviour the behaviour
	 */
	public MutationBehaviourProperty(final MutationBehaviour behaviour) {
		super(MutationBehaviourProperty.class);
		this.behaviour = behaviour;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return behaviour == ((MutationBehaviourProperty) other).getBehaviour();
	}




	@Override
	public String printValue() {
		return this.behaviour.toString();
	}


}



