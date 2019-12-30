module de.ruegnerlukas.simpleapplication {
	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive reflections;
	requires transitive org.slf4j;
	requires static lombok;

	opens de.ruegnerlukas.simpleapplication.core.presentation to javafx.fxml;
	opens de.ruegnerlukas.test to javafx.fxml;

	exports de.ruegnerlukas.simpleapplication;
    exports de.ruegnerlukas.simpleapplication.core.presentation;
	exports de.ruegnerlukas.simpleapplication.core.presentation.uimodule;
	exports de.ruegnerlukas.simpleapplication.core.presentation.utils;
	exports de.ruegnerlukas.simpleapplication.common.events;
	exports de.ruegnerlukas.simpleapplication.common.events.group;
	exports de.ruegnerlukas.test;
}