module de.ruegnerlukas.simpleapplication {

	// requires
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.annotation;
	requires static lombok;

	// opens
	opens de.ruegnerlukas.simpleapplication.core.presentation to javafx.fxml;

	// exports
	exports de.ruegnerlukas.simpleapplication;

	exports de.ruegnerlukas.simpleapplication.common.events;
	exports de.ruegnerlukas.simpleapplication.common.events.specializedevents;

	exports de.ruegnerlukas.simpleapplication.common.plugins;
	exports de.ruegnerlukas.simpleapplication.common.validation;
	exports de.ruegnerlukas.simpleapplication.common.extensions;
	exports de.ruegnerlukas.simpleapplication.common.resources;

	exports de.ruegnerlukas.simpleapplication.core.presentation;
	exports de.ruegnerlukas.simpleapplication.core.presentation.styling;
	exports de.ruegnerlukas.simpleapplication.core.presentation.uimodule;
	exports de.ruegnerlukas.simpleapplication.core.presentation.utils;

	exports de.ruegnerlukas.simpleapplication.core.persistence.json;

}