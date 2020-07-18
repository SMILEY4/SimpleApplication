package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.State;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;

public abstract class Component<T extends State> implements NodeFactory {


	/**
	 * The id of this component or null. (This will be transformed into a {@link IdProperty}).
	 */
	private String id = null;




	/**
	 * Adds the given id to this component. This will be transformed into a {@link IdProperty}
	 *
	 * @param id the id, unique among the siblings
	 * @return this component
	 */
	public Component<T> withId(final String id) {
		this.id = id;
		return this;
	}




	@Override
	public SNode create(final State state) {
		SNode node = render((T) state).create(state);
		if (id != null) {
			node.getProperties().put(IdProperty.class, Properties.id(this.id));
		}
		return node;
	}




	/**
	 * Renders this component, i.e. creates the node factories dependent in the given state.
	 * @param state the state
	 * @return the node factory
	 */
	public abstract NodeFactory render(T state);


}
