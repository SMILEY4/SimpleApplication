package de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNodeChildTransformListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.state.SuiState;

import java.util.ArrayList;
import java.util.List;

public abstract class BuilderExtensionContainer implements FactoryExtension {


	/**
	 * The properties of this builder.
	 */
	private final List<SuiProperty> internalProperties = new ArrayList<>();




	@Override
	public List<SuiProperty> getBuilderProperties() {
		return this.internalProperties;
	}




	/**
	 * Creates a new node.
	 *
	 * @param nodeType the type of the node
	 * @param state    the current state
	 * @return the created node
	 */
	protected SuiNode create(final Class<?> nodeType,
							 final SuiState state,
							 final Tags tags) {
		PropertyValidation.validate(nodeType, getBuilderProperties());
		return SuiNode.create(nodeType, getBuilderProperties(), state, tags);
	}




	/**
	 * Creates a new node.
	 *
	 * @param nodeType               the type of the node
	 * @param state                  the current state
	 * @param childListener          the child listener
	 * @param childTransformListener the child transform listener
	 * @return the created node
	 */
	protected SuiNode create(final Class<?> nodeType,
							 final SuiState state,
							 final Tags tags,
							 final SuiNodeChildListener childListener,
							 final SuiNodeChildTransformListener childTransformListener) {
		PropertyValidation.validate(nodeType, getBuilderProperties());
		return SuiNode.create(nodeType, getBuilderProperties(), state, tags, childListener, childTransformListener);
	}


}
