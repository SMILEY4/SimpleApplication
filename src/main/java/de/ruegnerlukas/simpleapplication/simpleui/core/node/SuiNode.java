package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.simpleui.core.tags.Tags;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import lombok.Getter;

import java.util.List;

public class SuiNode {


	/**
	 * The type of this node.
	 */
	@Getter
	private final Class<?> nodeType;

	/**
	 * The properties of this node.
	 */
	@Getter
	private final PropertyStore propertyStore;

	/**
	 * The child nodes of this node.
	 */
	@Getter
	private final ChildNodeStore childNodeStore;

	/**
	 * The javafx node of this node.
	 */
	@Getter
	private final FxNodeStore fxNodeStore;




	/**
	 * @param nodeType       the type of this node
	 * @param propertyStore  the properties of this node
	 * @param childNodeStore the child nodes of this node
	 * @param fxNodeStore    the javafx node of this node
	 */
	public SuiNode(final Class<?> nodeType,
				   final PropertyStore propertyStore,
				   final ChildNodeStore childNodeStore,
				   final FxNodeStore fxNodeStore) {
		this.nodeType = nodeType;
		this.propertyStore = propertyStore;
		this.childNodeStore = childNodeStore;
		this.fxNodeStore = fxNodeStore;
		childNodeStore.setManagedNode(this);
	}




	/**
	 * Creates a new node.
	 *
	 * @param nodeType   the type of the node
	 * @param properties the properties
	 * @param state      the current state
	 * @return the created node
	 */
	public static SuiNode create(final Class<?> nodeType,
								 final List<SuiProperty> properties,
								 final SuiState state,
								 final Tags tags) {
		return create(nodeType, properties, state, tags, SuiNodeChildListener.NO_OP, SuiNodeChildTransformListener.NO_OP);
	}




	/**
	 * Creates a new node.
	 *
	 * @param nodeType               the type of the node
	 * @param properties             the properties
	 * @param state                  the current state
	 * @param childListener          the child listener
	 * @param childTransformListener the child transform listener
	 * @return the created node
	 */
	public static SuiNode create(final Class<?> nodeType,
								 final List<SuiProperty> properties,
								 final SuiState state,
								 final Tags tags,
								 final SuiNodeChildListener childListener,
								 final SuiNodeChildTransformListener childTransformListener) {
		return new SuiNode(nodeType,
				new PropertyStore(properties),
				new ChildNodeStore(state, properties, tags, ChildNodeBuilder.DEFAULT, childListener, childTransformListener),
				new FxNodeStore());
	}


}
