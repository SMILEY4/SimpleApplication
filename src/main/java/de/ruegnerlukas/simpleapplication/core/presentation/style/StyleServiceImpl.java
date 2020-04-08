package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import javafx.scene.Node;

import java.util.Optional;

public class StyleServiceImpl implements StyleService {


	@Override
	public Style createFromResource(final String name, final Resource resource) {
		return null;
	}




	@Override
	public Style createFromString(final String name, final String... string) {
		return null;
	}




	@Override
	public void registerStyle(final Style style, final String name) {

	}




	@Override
	public void deregisterStyle(final String name) {

	}




	@Override
	public void applyStyleTo(final String name, final Node target) {

	}




	@Override
	public void applyStyleToExclusive(final String name, final Node target) {

	}




	@Override
	public void removeStyleFrom(final String name, final Node target) {

	}




	@Override
	public void disconnectNode(final Node node) {

	}




	@Override
	public void reloadStyle(final String name) {

	}




	@Override
	public void reloadAll() {

	}




	@Override
	public Optional<Style> findStyle(final String name) {
		return Optional.empty();
	}

}
