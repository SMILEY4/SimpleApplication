package de.ruegnerlukas.simpleapplication.core.presentation.style;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import javafx.scene.Node;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
public class StringStyle extends Style {


	/**
	 * The style as strings.
	 */
	@Getter
	private final String[] styleStrings;




	/**
	 * @param styleStrings the style as strings.
	 */
	public StringStyle(final String[] styleStrings) {
		Validations.INPUT.notEmpty(styleStrings).exception("The style strings may not be null or empty.");
		this.styleStrings = styleStrings;
	}




	/**
	 * @return this style as a single string. Seperated and terminated by a ';'.
	 */
	public String getAsSingleString() {
		return String.join("; ", styleStrings) + "; ";
	}




	@Override
	public void applyTo(final Node target) {
		String prevStyle = Optional.ofNullable(target.getStyle()).orElse("");
		if (!prevStyle.strip().endsWith(";") && !prevStyle.isBlank()) {
			prevStyle += "; ";
		}
		target.setStyle(prevStyle + getAsSingleString());
	}




	@Override
	public void applyExclusive(final Node target) {
		target.setStyle(getAsSingleString());
		if (target instanceof Parent) {
			((Parent) target).getStylesheets().clear();
		}
	}




	@Override
	public void removeFrom(final Node target) {
		final String prevStyle = Optional.ofNullable(target.getStyle()).orElse("");
		target.setStyle(prevStyle.replace(this.getAsSingleString(), ""));
	}


}
