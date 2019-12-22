module de.ruegnerlukas.simpleapplication {
    requires javafx.controls;
    requires javafx.fxml;
    requires reflections;
    requires static lombok;

    opens de.ruegnerlukas.simpleapplication.core.presentation to javafx.fxml;
    exports de.ruegnerlukas.simpleapplication.core.presentation;
}