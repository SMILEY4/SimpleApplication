package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;

import java.util.List;

public class InjectableItemProperty extends ItemProperty {


	/**
	 * The id of this injection point.
	 */
	private final String injectionPointId;

	/**
	 * The factory for a default item.
	 */
	private final NodeFactory defaultFactory;




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param item             the factory for creating the default item.
	 */
	public InjectableItemProperty(final String injectionPointId, final NodeFactory item) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null or empty.");
		this.injectionPointId = injectionPointId;
		this.defaultFactory = item;
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 */
	public InjectableItemProperty(final String injectionPointId) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null or empty.");
		this.injectionPointId = injectionPointId;
		this.defaultFactory = null;
	}




	@Override
	public NodeFactory getFactory() {
		List<NodeFactory> injected = SuiRegistry.get().getInjected(injectionPointId);
		if (!injected.isEmpty()) {
			return injected.get(injected.size() - 1);
		} else {
			return defaultFactory;
		}
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param injectionPointId the id of this injection point.
		 * @param item             the factory for creating the default item/node.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemInjectable(final String injectionPointId, final NodeFactory item) {
			getBuilderProperties().add(new InjectableItemProperty(injectionPointId, item));
			return (T) this;
		}

		/**
		 * @param injectionPointId the id of this injection point.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemInjectable(final String injectionPointId) {
			getBuilderProperties().add(new InjectableItemProperty(injectionPointId));
			return (T) this;
		}


	}

}
