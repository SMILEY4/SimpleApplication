package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.State;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;

public abstract class Component<T extends State> implements NodeFactory {


	private String id = null;




	public Component withId(String id) {
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




	public abstract NodeFactory render(T state);


}
