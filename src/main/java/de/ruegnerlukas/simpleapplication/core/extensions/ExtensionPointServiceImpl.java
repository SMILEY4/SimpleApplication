package de.ruegnerlukas.simpleapplication.core.extensions;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class ExtensionPointServiceImpl implements ExtensionPointService {


	/**
	 * The registered {@link ExtensionPoint}. The key is the id of the extension point.
	 */
	private final Map<String, ExtensionPoint> extensionPoints = new HashMap<>();




	@Override
	public void register(final ExtensionPoint extensionPoint) {
		Validations.INPUT.notNull(extensionPoint).exception("The extension point may not be null.");
		if (extensionPoints.containsKey(extensionPoint.getId())) {
			log.warn("The extension point with the id '{}' already exists and will not be registered again.",
					extensionPoint.getId());
		} else {
			extensionPoints.put(extensionPoint.getId(), extensionPoint);
		}
	}




	@Override
	public void deregister(final String id) {
		extensionPoints.remove(id);
	}




	@Override
	public Optional<ExtensionPoint> find(final String id) {
		final ExtensionPoint extensionPoint = extensionPoints.get(id);
		return Optional.ofNullable(extensionPoint);
	}




	@Override
	public ExtensionPoint findOrDummy(final String id) {
		final Optional<ExtensionPoint> optional = find(id);
		return optional.orElseGet(DummyExtensionPoint::new);
	}

}
