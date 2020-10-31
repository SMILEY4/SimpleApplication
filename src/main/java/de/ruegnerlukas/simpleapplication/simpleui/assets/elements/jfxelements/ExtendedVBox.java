package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.AllArgsConstructor;
import lombok.Getter;

public class ExtendedVBox extends VBox {


	/**
	 * The current parent scroll pane
	 */
	private ScrollPane parentScrollPane = null;

	/**
	 * The listener to the scroll pane
	 */
	private final ChangeListener<Number> scrollListener = (v, p, n) -> {
		if (parentScrollPane != null) {
			onScroll(parentScrollPane);
		}
	};




	/**
	 * Default constructor
	 */
	public ExtendedVBox() {
		this.parentProperty().addListener((value, prev, next) -> Platform.runLater(() -> {
			if (next != null
					&& next.getParent() != null
					&& next.getParent().getParent() != null
					&& next.getParent().getParent() instanceof ScrollPane) {
				setScrollPane((ScrollPane) next.getParent().getParent());
			} else {
				clearScrollPane();
			}
		}));
		this.getChildren().addListener((ListChangeListener<Node>) change -> Platform.runLater(() -> {
			if (parentScrollPane != null) {
				onScroll(parentScrollPane);
			}
		}));
	}




	/**
	 * Sets up the new given parent scroll pane. Clears/Resets the previous scroll pane if necessary.
	 *
	 * @param scrollPane the new parent scroll pane
	 */
	private void setScrollPane(final ScrollPane scrollPane) {
		clearScrollPane();
		parentScrollPane = scrollPane;
		scrollPane.vvalueProperty().addListener(scrollListener);
		scrollPane.heightProperty().addListener(scrollListener);
		onScroll(scrollPane);
	}




	/**
	 * Clears/Resets and removes the current parent scroll pane
	 */
	private void clearScrollPane() {
		if (parentScrollPane != null) {
			parentScrollPane.vvalueProperty().removeListener(scrollListener);
			parentScrollPane.heightProperty().removeListener(scrollListener);
			parentScrollPane = null;
		}
	}




	/**
	 * Called when the scroll value of the given scroll pane changes
	 *
	 * @param scrollPane the scrolled pane (always the current parent scroll pane)
	 */
	private void onScroll(final ScrollPane scrollPane) {
		final Bounds bounds = scrollPane.getViewportBounds();
		final double vValue = scrollPane.getVvalue();
		final double height = bounds.getHeight();
		final double y = (scrollPane.getContent().getBoundsInParent().getHeight() - height) * vValue;
		doUpdate(new Rectangle(y, height));
	}




	/**
	 * Optimizes the child nodes when the visible area changes
	 *
	 * @param visibleArea the new visible area
	 */
	private void doUpdate(final Rectangle visibleArea) {
		for (int i = 0, n = getChildrenUnmodifiable().size(); i < n; i++) {
			final Node node = getChildren().get(i);
			final boolean visible = isVisible(node, visibleArea);
			if (node.isDisabled()) {
				if (visible) {
					node.setDisable(false);
					node.setVisible(true);
				}
			} else {
				if (!visible) {
					if (node.isFocused()) {
						this.requestFocus();
						// give focus to another node that is not part of the list
						// when we disable the node, jfx has to give focus to another node. It chooses the next node in the list.
						// When jfx gives focus to another node, the scrollpane jumps to show that focused node and breaks the scroll.
					}
					node.setDisable(true);
					node.setVisible(false);
				}
			}
		}
	}




	/**
	 * @param node the node to check
	 * @param rect the visible area
	 * @return whether the given node is visible (intersects the givne area)
	 */
	private boolean isVisible(final Node node, final Rectangle rect) {
		Bounds bounds = node.getBoundsInParent();
		return rect.insideY(bounds.getMinY()) || rect.insideY(bounds.getMaxY());
	}




	@Getter
	@AllArgsConstructor
	private static class Rectangle {


		/**
		 * The y position
		 */
		private final double y;

		/**
		 * The height
		 */
		private final double h;




		/**
		 * @param v the vertical position
		 * @return whether the given position is inside this rectangle
		 */
		public boolean insideY(final double v) {
			return y < v && v < y + h;
		}

	}

}
