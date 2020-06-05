package simpleui.core.properties;

import simpleui.core.nodes.SNode;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class Properties {


	public static Property actionListener(final ActionListenerProperty.ActionListener listener) {
		return new ActionListenerProperty(listener);
	}




	public static Property items(final SNode... items) {
		return new ItemListProperty(items);
	}




	public static Property items(final Collection<SNode> items) {
		return new ItemListProperty(items);
	}




	public static Property items(final Stream<SNode> items) {
		return new ItemListProperty(items);
	}




	public static Property items(final ItemListProperty.ItemListFactory factory) {
		return new ItemListProperty(factory);
	}




	public static Property buttonText(final String text) {
		return new ButtonTextProperty(text);
	}




	public static Set<Class<? extends Property>> getCommonProperties(final SNode nodeA, final SNode nodeB) {
		final List<Property> propsA = nodeA.getProperties();
		final List<Property> propsB = nodeB.getProperties();
		final Set<Class<? extends Property>> commonProps = new HashSet<>();
		propsA.stream().map(Property::getKey).forEach(commonProps::add);
		propsB.stream().map(Property::getKey).forEach(commonProps::add);
		return commonProps;
	}

}
