package de.ruegnerlukas.simpleapplication.simpleui.core.node.factoriesextensions;

import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.Properties;

@SuppressWarnings ("unchecked")
public interface RegionBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


	default T sizeMin(final Number width, Number height) {
		getFactoryInternalProperties().add(Properties.minSize(width, height));
		return (T) this;
	}


	default T sizeMax(final Number width, Number height) {
		getFactoryInternalProperties().add(Properties.maxSize(width, height));
		return (T) this;
	}


	default T sizePreferred(final Number width, Number height) {
		getFactoryInternalProperties().add(Properties.preferredSize(width, height));
		return (T) this;
	}


	default T size(final Number minWidth, final Number minHeight,
				   final Number preferredWidth, final Number preferredHeight,
				   final Number maxWidth, final Number maxHeight) {
		getFactoryInternalProperties().add(Properties.size(minWidth, minHeight, preferredWidth, preferredHeight, maxWidth, maxHeight));
		return (T) this;
	}


}
