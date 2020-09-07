package de.ruegnerlukas.simpleapplication.simpleui.elements.basenode;

import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
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
	 * The listener for changes to child nodes (if required).
	 */
	@Getter
	private SuiNodeChildListener childListener;

	/**
	 * The listener for child node transformations (if required).
	 */
	@Getter
	private SuiNodeChildTransformListener childTransformListener;




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
				new ChildNodeStore(ChildNodeBuilder.build(state, properties)),
				new FxNodeStore(),
				childListener,
				childTransformListener);
	}


}
