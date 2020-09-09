package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Property;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import lombok.Getter;

import java.util.List;

public class SuiBaseNode {


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
	public SuiBaseNode(final Class<?> nodeType,
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
	public static SuiBaseNode create(final Class<?> nodeType,
									 final List<Property> properties,
									 final SuiState state) {
		return create(nodeType, properties, state, SuiNodeChildListener.NO_OP, SuiNodeChildTransformListener.NO_OP);
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
	public static SuiBaseNode create(final Class<?> nodeType,
									 final List<Property> properties,
									 final SuiState state,
									 final SuiNodeChildListener childListener,
									 final SuiNodeChildTransformListener childTransformListener) {
		return new SuiBaseNode(nodeType,
				new PropertyStore(properties),
				new ChildNodeStore(state, properties, ChildNodeBuilder.DEFAULT, childListener, childTransformListener),
				new FxNodeStore());
	}


}
