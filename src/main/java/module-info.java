module de.ruegnerlukas.simpleapplication {
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive org.slf4j;
	requires static lombok;

	opens de.ruegnerlukas.simpleapplication.core.presentation to javafx.fxml;

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

}