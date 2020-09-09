package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import lombok.Getter;

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
		 * This node and its children nodes will not be affected by mutations.
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
	protected boolean isPropertyEqual(final SuiProperty other) {
		return behaviour == ((MutationBehaviourProperty) other).getBehaviour();
	}


}



