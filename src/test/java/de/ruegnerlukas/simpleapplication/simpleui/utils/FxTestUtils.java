package de.ruegnerlukas.simpleapplication.simpleui.utils;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.assertj.core.data.Percentage;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FxTestUtils {


	public static void assertButton(Button button, ButtonInfo info) {
		assertThat(button).isNotNull();
		assertThat(button.getMinWidth()).isCloseTo(info.getMinWidth(), Percentage.withPercentage(0.01));
		assertThat(button.getMinHeight()).isCloseTo(info.getMinHeight(), Percentage.withPercentage(0.01));
		assertThat(button.getPrefWidth()).isCloseTo(info.getPrefWidth(), Percentage.withPercentage(0.01));
		assertThat(button.getPrefHeight()).isCloseTo(info.getPrefHeight(), Percentage.withPercentage(0.01));
		assertThat(button.getMaxWidth()).isCloseTo(info.getMaxWidth(), Percentage.withPercentage(0.01));
		assertThat(button.getMaxHeight()).isCloseTo(info.getMaxHeight(), Percentage.withPercentage(0.01));
		assertThat(button.getText()).isEqualTo(info.getText());
		assertThat(button.isWrapText()).isEqualTo(info.isWrap());
		assertThat(button.isDisabled()).isEqualTo(info.isDisabled());
		if (info.hasOnActionListener) {
			assertThat(button.getOnAction()).isNotNull();
		} else {
			assertThat(button.getOnAction()).isNull();
		}
	}




	@Getter
	@Setter
	@Builder
	public static class ButtonInfo {


		private Double minWidth;
		private Double minHeight;

		private Double prefWidth;
		private Double prefHeight;

		private Double maxWidth;
		private Double maxHeight;

		private String text;

		private boolean wrap;

		private boolean disabled;

		private boolean hasOnActionListener;

	}




	public static void assertAnchorPane(AnchorPane anchorPane, AnchorPaneInfo info) {

		assertThat(anchorPane).isNotNull();
		assertThat(anchorPane.getMinWidth()).isCloseTo(info.getMinWidth(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.getMinHeight()).isCloseTo(info.getMinHeight(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.getPrefWidth()).isCloseTo(info.getPrefWidth(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.getPrefHeight()).isCloseTo(info.getPrefHeight(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.getMaxWidth()).isCloseTo(info.getMaxWidth(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.getMaxHeight()).isCloseTo(info.getMaxHeight(), Percentage.withPercentage(0.01));
		assertThat(anchorPane.isDisabled()).isEqualTo(info.isDisabled());
		assertThat(anchorPane.getChildren()).hasSize(info.children.size());
		for (int i = 0; i < info.children.size(); i++) {
			final AnchorPaneChildButtonInfo childInfo = info.children.get(i);
			final Button child = (Button) anchorPane.getChildren().get(i);
			assertThat(child.getText()).isEqualTo(childInfo.getText());
			if (childInfo.getAnchorTop() == null) {
				assertThat(AnchorPane.getTopAnchor(child)).isNull();
			} else {
				assertThat(AnchorPane.getTopAnchor(child)).isCloseTo(childInfo.getAnchorTop().doubleValue(), Percentage.withPercentage(0.01));
			}
			if (childInfo.getAnchorBottom() == null) {
				assertThat(AnchorPane.getBottomAnchor(child)).isNull();
			} else {
				assertThat(AnchorPane.getBottomAnchor(child)).isCloseTo(childInfo.getAnchorBottom().doubleValue(), Percentage.withPercentage(0.01));
			}
			if (childInfo.getAnchorLeft() == null) {
				assertThat(AnchorPane.getLeftAnchor(child)).isNull();
			} else {
				assertThat(AnchorPane.getLeftAnchor(child)).isCloseTo(childInfo.getAnchorLeft().doubleValue(), Percentage.withPercentage(0.01));
			}
			if (childInfo.getAnchorRight() == null) {
				assertThat(AnchorPane.getRightAnchor(child)).isNull();
			} else {
				assertThat(AnchorPane.getRightAnchor(child)).isCloseTo(childInfo.getAnchorRight().doubleValue(), Percentage.withPercentage(0.01));
			}
		}

	}




	@Getter
	@Setter
	@Builder
	public static class AnchorPaneInfo {


		private Double minWidth;
		private Double minHeight;

		private Double prefWidth;
		private Double prefHeight;

		private Double maxWidth;
		private Double maxHeight;

		private boolean disabled;

		private List<AnchorPaneChildButtonInfo> children;

	}






	@Getter
	@Setter
	@Builder
	public static class AnchorPaneChildButtonInfo {


		private Number anchorTop;
		private Number anchorBottom;
		private Number anchorLeft;
		private Number anchorRight;

		private String text;

	}




	public static void assertScrollPane(ScrollPane scrollPane, ScrollPaneInfo info) {
		assertThat(scrollPane).isNotNull();
		assertThat(scrollPane.getMinWidth()).isCloseTo(info.getMinWidth(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.getMinHeight()).isCloseTo(info.getMinHeight(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.getPrefWidth()).isCloseTo(info.getPrefWidth(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.getPrefHeight()).isCloseTo(info.getPrefHeight(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.getMaxWidth()).isCloseTo(info.getMaxWidth(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.getMaxHeight()).isCloseTo(info.getMaxHeight(), Percentage.withPercentage(0.01));
		assertThat(scrollPane.isDisabled()).isEqualTo(info.isDisabled());
		assertThat(scrollPane.isFitToWidth()).isEqualTo(info.isFitToWidth());
		assertThat(scrollPane.isFitToHeight()).isEqualTo(info.isFitToHeight());
		assertThat(scrollPane.getHbarPolicy()).isEqualTo(info.getHorzBar());
		assertThat(scrollPane.getVbarPolicy()).isEqualTo(info.getVertBar());
		assertThat(scrollPane.getContent()).isNotNull();
		assertThat(((Button) scrollPane.getContent()).getText()).isEqualTo(info.getContentButtonText());
	}




	@Getter
	@Setter
	@Builder
	public static class ScrollPaneInfo {


		private Double minWidth;
		private Double minHeight;

		private Double prefWidth;
		private Double prefHeight;

		private Double maxWidth;
		private Double maxHeight;

		private boolean disabled;

		private boolean fitToWidth;
		private boolean fitToHeight;
		private ScrollPane.ScrollBarPolicy horzBar;
		private ScrollPane.ScrollBarPolicy vertBar;

		private String contentButtonText;

	}




	public static void assertVBox(final VBox vbox, VBoxInfo info) {
		assertThat(vbox).isNotNull();
		assertThat(vbox.getMinWidth()).isCloseTo(info.getMinWidth(), Percentage.withPercentage(0.01));
		assertThat(vbox.getMinHeight()).isCloseTo(info.getMinHeight(), Percentage.withPercentage(0.01));
		assertThat(vbox.getPrefWidth()).isCloseTo(info.getPrefWidth(), Percentage.withPercentage(0.01));
		assertThat(vbox.getPrefHeight()).isCloseTo(info.getPrefHeight(), Percentage.withPercentage(0.01));
		assertThat(vbox.getMaxWidth()).isCloseTo(info.getMaxWidth(), Percentage.withPercentage(0.01));
		assertThat(vbox.getMaxHeight()).isCloseTo(info.getMaxHeight(), Percentage.withPercentage(0.01));
		assertThat(vbox.isDisabled()).isEqualTo(info.isDisabled());
		assertThat(vbox.isFillWidth()).isEqualTo(info.isFitToWidth());
		assertThat(vbox.getSpacing()).isCloseTo(info.getSpacing(), Percentage.withPercentage(0.01));
		assertThat(vbox.getAlignment()).isEqualTo(info.getAlignment());
		assertThat(vbox.getChildren()).hasSameSizeAs(info.getContentButtonTexts());
		for (int i = 0; i < info.getContentButtonTexts().size(); i++) {
			assertThat(((Button) vbox.getChildren().get(i)).getText()).isEqualTo(info.getContentButtonTexts().get(i));
		}
	}




	@Getter
	@Setter
	@Builder
	public static class VBoxInfo {


		private Double minWidth;
		private Double minHeight;

		private Double prefWidth;
		private Double prefHeight;

		private Double maxWidth;
		private Double maxHeight;

		private boolean disabled;

		private boolean fitToWidth;

		private Pos alignment;

		private double spacing;

		private List<String> contentButtonTexts;

	}




	public static void assertSeparator(final Separator separator, SeparatorInfo info) {
		assertThat(separator).isNotNull();
		assertThat(separator.getMinWidth()).isCloseTo(info.getMinWidth(), Percentage.withPercentage(0.01));
		assertThat(separator.getMinHeight()).isCloseTo(info.getMinHeight(), Percentage.withPercentage(0.01));
		assertThat(separator.getPrefWidth()).isCloseTo(info.getPrefWidth(), Percentage.withPercentage(0.01));
		assertThat(separator.getPrefHeight()).isCloseTo(info.getPrefHeight(), Percentage.withPercentage(0.01));
		assertThat(separator.getMaxWidth()).isCloseTo(info.getMaxWidth(), Percentage.withPercentage(0.01));
		assertThat(separator.getMaxHeight()).isCloseTo(info.getMaxHeight(), Percentage.withPercentage(0.01));
		assertThat(separator.isDisabled()).isEqualTo(info.isDisabled());
		assertThat(separator.getOrientation()).isEqualTo(info.getOrientation());
	}




	@Getter
	@Setter
	@Builder
	public static class SeparatorInfo {


		private Double minWidth;
		private Double minHeight;

		private Double prefWidth;
		private Double prefHeight;

		private Double maxWidth;
		private Double maxHeight;

		private boolean disabled;

		private Orientation orientation;

	}

}