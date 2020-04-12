package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.resources.Resource;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.Getter;


public class ResourceStyle extends Style {


	/**
	 * The resource pointing to a stylesheet
	 */
	@Getter
	private final Resource resource;




	/**
	 * @param resource the resource pointing to a stylesheet
	 */
	public ResourceStyle(final Resource resource) {
		Validations.INPUT.notNull(resource).exception("The resource may not be null.");
		this.resource = resource;
	}




	/**
	 * @return this style as a path.
	 */
	private String getAsPath() {
		String path = resource.getPath();
		if (!resource.isInternal()) {
			path = "file:" + path;
		}
		return path;
	}




	@Override
	public void applyTo(final Node target) {
		validateIsParent(target);
		((Parent) target).getStylesheets().add(getAsPath());
	}




	@Override
	public void applyExclusive(final Node target) {
		validateIsParent(target);
		((Parent) target).getStylesheets().clear();
		applyTo(target);
	}




	@Override
	public void removeFrom(final Node target) {
		validateIsParent(target);
		((Parent) target).getStylesheets().remove(getAsPath());
	}




	/**
	 * Validates whether the given node is of type {@link Parent}.
	 *
	 * @param target the {@link Node} to check
	 */
	private void validateIsParent(final Node target) {
		Validations.INPUT.typeOf(target, Parent.class)
				.exception("The type of the target is invalid: Expected: {}. Actual: {}",
						Parent.class.getName(), target.getClass().getName());
	}

}
