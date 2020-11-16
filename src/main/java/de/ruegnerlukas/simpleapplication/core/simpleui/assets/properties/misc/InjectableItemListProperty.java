package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.registry.SuiRegistry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InjectableItemListProperty extends ItemListProperty {


	/**
	 * The id of this injection point.
	 */
	private final String injectionPointId;

	/**
	 * The list of factories for default items.
	 */
	private final List<NodeFactory> defaultFactories;

	/**
	 * The marker defining at what position to inject items into
	 */
	private final InjectionIndexMarker injectionIndexMarker;




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final NodeFactory... items) {
		this(injectionPointId,
				indexMarker,
				List.of(Validations.INPUT.require(items, "The items can not be null.")));
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Stream<NodeFactory> items) {
		this(injectionPointId,
				indexMarker,
				Validations.INPUT.require(items, "The items can not be null.").collect(Collectors.toList()));
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param supplier         the supplier for creating factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Supplier<List<NodeFactory>> supplier) {
		this(injectionPointId,
				indexMarker,
				Validations.INPUT.require(supplier, "The supplier can not be null.").get());
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 */
	public InjectableItemListProperty(final String injectionPointId) {
		this(injectionPointId, InjectionIndexMarker.injectFirst(), List.of());
	}




	/**
	 * @param injectionPointId the id of this injection point.
	 * @param indexMarker      the marker defining at what position to inject items into
	 * @param items            the factories for creating the items/nodes.
	 */
	public InjectableItemListProperty(final String injectionPointId,
									  final InjectionIndexMarker indexMarker,
									  final Collection<NodeFactory> items) {
		Validations.INPUT.notEmpty(injectionPointId).exception("The injection point id can not be null or empty.");
		Validations.INPUT.notNull(indexMarker).exception("The index marker can not be null or empty.");
		Validations.INPUT.notNull(items).exception("The items can not be null.");
		Validations.INPUT.containsNoNull(items).exception("The items can not contain any null elements.");
		this.injectionPointId = injectionPointId;
		this.injectionIndexMarker = indexMarker;
		this.defaultFactories = List.copyOf(items);
	}




	@Override
	public List<NodeFactory> getFactories() {
		final SuiRegistry suiRegistry = new Provider<>(SuiRegistry.class).get();
		final List<NodeFactory> injected = suiRegistry.getInjectedNodeFactories(injectionPointId);
		final List<NodeFactory> factories = new ArrayList<>(injected.size() + defaultFactories.size());
		factories.addAll(defaultFactories);
		factories.addAll(injectionIndexMarker.getIndex(factories), injected);
		return factories;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param injectionPointId the id of this injection point.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemsInjectable(final String injectionPointId) {
			getBuilderProperties().add(new InjectableItemListProperty(injectionPointId));
			return (T) this;
		}

		/**
		 * @param injectionPointId the id of this injection point.
		 * @param indexMarker      the marker defining at what position to inject items into
		 * @param items            the factories for creating the default  items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemsInjectable(final String injectionPointId,
								  final InjectionIndexMarker indexMarker,
								  final NodeFactory... items) {
			getBuilderProperties().add(new InjectableItemListProperty(injectionPointId, indexMarker, items));
			return (T) this;
		}

		/**
		 * @param injectionPointId the id of this injection point.
		 * @param indexMarker      the marker defining at what position to inject items into
		 * @param items            the factories for creating the default items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemsInjectable(final String injectionPointId,
								  final InjectionIndexMarker indexMarker,
								  final Collection<NodeFactory> items) {
			getBuilderProperties().add(new InjectableItemListProperty(injectionPointId, indexMarker, items));
			return (T) this;
		}

		/**
		 * @param injectionPointId the id of this injection point.
		 * @param indexMarker      the marker defining at what position to inject items into
		 * @param items            the factories for creating the default items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemsInjectable(final String injectionPointId,
								  final InjectionIndexMarker indexMarker,
								  final Stream<NodeFactory> items) {
			getBuilderProperties().add(new InjectableItemListProperty(injectionPointId, indexMarker, items));
			return (T) this;
		}

		/**
		 * @param injectionPointId the id of this injection point.
		 * @param indexMarker      the marker defining at what position to inject items into
		 * @param supplier         the supplier for creating factories for creating the default items/nodes.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T itemsInjectable(final String injectionPointId,
								  final InjectionIndexMarker indexMarker,
								  final Supplier<List<NodeFactory>> supplier) {
			getBuilderProperties().add(new InjectableItemListProperty(injectionPointId, indexMarker, supplier));
			return (T) this;
		}

	}


}
