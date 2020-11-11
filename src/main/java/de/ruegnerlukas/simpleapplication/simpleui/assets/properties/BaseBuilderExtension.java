package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.AnchorProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.DisabledProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.HGrowProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.IdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.MutationBehaviourProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleClassProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleIdProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.StyleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.VGrowProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.VisibleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;

import java.util.List;

public interface BaseBuilderExtension<T extends FactoryExtension> extends FactoryExtension,
		IdProperty.PropertyBuilderExtension<T>,
		MutationBehaviourProperty.PropertyBuilderExtension<T>,
		DisabledProperty.PropertyBuilderExtension<T>,
		VisibleProperty.PropertyBuilderExtension<T>,
		AnchorProperty.PropertyBuilderExtension<T>,
		HGrowProperty.PropertyBuilderExtension<T>,
		VGrowProperty.PropertyBuilderExtension<T>,
		TitleProperty.PropertyBuilderExtension<T>,
		StyleProperty.PropertyBuilderExtension<T>,
		StyleClassProperty.PropertyBuilderExtension<T>,
		StyleIdProperty.PropertyBuilderExtension<T> {


	/**
	 * @param properties properties to add the the element manually
	 * @return this builder for chaining
	 */
	@SuppressWarnings ("unchecked")
	default T properties(final SuiProperty... properties) {
		for (SuiProperty property : properties) {
			getBuilderProperties().add(property);
		}
		return (T) this;
	}

	/**
	 * @param properties properties to add the the element manually
	 * @return this builder for chaining
	 */
	@SuppressWarnings ("unchecked")
	default T properties(final List<SuiProperty> properties) {
		getBuilderProperties().addAll(properties);
		return (T) this;
	}


}
