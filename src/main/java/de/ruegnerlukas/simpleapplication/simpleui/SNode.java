package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemListProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.ItemProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Getter
public class SNode {


	private final Class<?> nodeType;


	private final Map<Class<? extends Property>, Property> properties = new HashMap<>();

	@Getter
	public final List<SNode> children;

	@Setter
	private javafx.scene.Node fxNode;

	@Getter
	private ChildListener childListener;




	public SNode(final Class<?> nodeType, final List<Property> propertyList, final State state, final ChildListener childListener) {
		this.nodeType = nodeType;
		propertyList.forEach(property -> properties.put(property.getKey(), property));
		this.children= childNodesFromProperties(state);
		this.childListener = childListener;
	}


	private List<SNode> childNodesFromProperties(final State state) {
		final List<SNode> children = new ArrayList<>();
		getPropertySafe(ItemListProperty.class).ifPresent(itemListProp -> itemListProp.getFactories().forEach(factory -> {
			final SNode child = factory.create(state);
			children.add(child);
		}));
		getPropertySafe(ItemProperty.class).ifPresent(itemProp -> children.add(itemProp.getFactory().create(state)));
		return children;
	}



	public void triggerChildListChange() {
		if (childListener != null && getFxNode() != null) {
			childListener.onChange(this);
		}
	}




	public <T> Optional<T> getPropertySafe(Class<T> key) {
		return Optional.ofNullable(getProperty(key));
	}




	public <T> T getProperty(Class<T> key) {
		Property property = getProperties().get(key);
		if (property != null) {
			return property.getAs(key);
		} else {
			return null;
		}
	}




	public boolean hasProperty(Class<? extends Property> key) {
		return getProperties().containsKey(key);
	}




	public interface ChildListener {


		void onChange(SNode parent);

	}

}
