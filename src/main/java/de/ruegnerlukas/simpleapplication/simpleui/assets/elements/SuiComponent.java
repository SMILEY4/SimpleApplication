package de.ruegnerlukas.simpleapplication.simpleui.assets.elements;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiBaseNode;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;

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
	public SuiBaseNode create(final SuiState state) {
		@SuppressWarnings ("unchecked") final T typedState = (T) state;
		final SuiBaseNode node = renderer.render(typedState).create(state);
		if (id != null) {
			node.getPropertyStore().upsert(Properties.id(this.id));
		}
		return node;
	}


}
