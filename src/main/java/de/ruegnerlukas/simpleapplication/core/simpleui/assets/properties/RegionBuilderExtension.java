package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties;

import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.BackgroundProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.BorderProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.PaddingProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SizeMaxProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SizeMinProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SizePreferredProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.SizeProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;

public interface RegionBuilderExtension<T extends FactoryExtension> extends FactoryExtension,
		SizeMinProperty.PropertyBuilderExtension<T>,
		SizePreferredProperty.PropertyBuilderExtension<T>,
		SizeMaxProperty.PropertyBuilderExtension<T>,
		SizeProperty.PropertyBuilderExtension<T>,
		BackgroundProperty.PropertyBuilderExtension<T>,
		BorderProperty.PropertyBuilderExtension<T>,
		PaddingProperty.PropertyBuilderExtension<T> {


}
