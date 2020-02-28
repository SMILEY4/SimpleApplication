package de.ruegnerlukas.simpleapplication.core.extensions;

import de.ruegnerlukas.simpleapplication.common.events.EventListener;

public class DummyExtensionPoint extends ExtensionPoint {


	/**
	 * The id of a dummy extension point.
	 */
	public static final String ID = "dummy.extension.point";




	/**
	 * The default constructor
	 */
	public DummyExtensionPoint() {
		super(DummyExtensionPoint.ID);
	}




	@Override
	public <T> void addSupportedType(final Class<T> type, final EventListener<T> listener) {
		// to nothing
	}




	@Override
	public boolean isSupported(final Class<?> type) {
		return true;
	}




	@Override
	public <T> void provide(final Class<T> type, final Object data) {
		// do nothing
	}

}
