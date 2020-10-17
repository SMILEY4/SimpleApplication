package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions.FactoryExtension;
import lombok.Getter;

import java.util.function.BiFunction;

public class IdProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<IdProperty, IdProperty, Boolean> COMPARATOR =
			(a, b) -> a.getId().equals(b.getId());


	/**
	 * The id of the element. Unique among the siblings.
	 */
	@Getter
	private final String id;




	/**
	 * @param id the id of the element. Unique among the siblings.
	 */
	public IdProperty(final String id) {
		super(IdProperty.class, COMPARATOR);
		this.id = id;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param id the id of the element
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T id(final String id) {
			getFactoryInternalProperties().add(new IdProperty(id));
			return (T) this;
		}

	}


}
