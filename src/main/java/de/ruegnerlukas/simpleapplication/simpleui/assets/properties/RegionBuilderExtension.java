package de.ruegnerlukas.simpleapplication.simpleui.assets.properties;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;

public interface RegionBuilderExtension<T extends FactoryExtension> extends FactoryExtension,
		SizeMinProperty.PropertyBuilderExtension<T>,
		SizePreferredProperty.PropertyBuilderExtension<T>,
		SizeMaxProperty.PropertyBuilderExtension<T>,
		SizeProperty.PropertyBuilderExtension<T> {


}