package de.ruegnerlukas.simpleapplication.simpleui.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.properties.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;

public class SuiComponent<T extends SuiState> implements NodeFactory {


	/**
	 * The id of this component or null. (This will be transformed into a {@link IdProperty}).
	 */
	private String id = null;

	/**
	 * The renderer for the node factory of this component.
	 */
	private final SuiComponentRenderer<T> renderer;




	/**
	 * @param renderer the renderer for the node factory of this component
	 */
	public SuiComponent(final SuiComponentRenderer<T> renderer) {
		Validations.INPUT.notNull(renderer).exception("The renderer may not be null.");
		this.renderer = renderer;
	}




	/**
	 * Adds the given id to this component. This will be transformed into a {@link IdProperty}
	 *
	 * @param id the id, unique among the siblings
	 * @return this component
	 */
	public SuiComponent<T> withId(final String id) {
		this.id = id;
		return this;
	}




	@Override
	public SuiNode create(final SuiState state) {
		SuiNode node = renderer.render((T) state).create(state);
		if (id != null) {
			node.getProperties().put(IdProperty.class, Properties.id(this.id));
		}
		return node;
	}


}
