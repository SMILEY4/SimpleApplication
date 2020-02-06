package de.ruegnerlukas.simpleapplication.core.presentation.views;

import javafx.scene.Parent;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class View {


	private final String id;

	private final int width;

	private final int height;

	private final String title;

	private final Parent node; // TODO temp

}
