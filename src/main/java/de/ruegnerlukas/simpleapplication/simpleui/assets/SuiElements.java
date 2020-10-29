package de.ruegnerlukas.simpleapplication.simpleui.assets;

import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAccordion;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiAnchorPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiButton;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiCheckbox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiChoiceBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComboBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponent;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiComponentRenderer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiContainer;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiDatePicker;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiHBox;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiImage;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabel;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiList;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiMenuBar;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiScrollPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSeparator;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSlider;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSpinner;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTabPane;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTextArea;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiTextField;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiVBox;
import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;

public final class SuiElements {


	/**
	 * Hidden constructor for utility class.
	 */
	private SuiElements() {
		// do nothing
	}




	/**
	 * Build a new accordion
	 *
	 * @return the builder for an accordion
	 */
	public static SuiAccordion.SuiAccordionBuilder accordion() {
		return new SuiAccordion.SuiAccordionBuilder();
	}




	/**
	 * Build a new anchor pane
	 *
	 * @return the builder for an anchor pane
	 */
	public static SuiAnchorPane.SuiAnchorPaneBuilder anchorPane() {
		return new SuiAnchorPane.SuiAnchorPaneBuilder();
	}




	/**
	 * Build a new button
	 *
	 * @return the builder for a button
	 */
	public static SuiButton.SuiButtonBuilder button() {
		return new SuiButton.SuiButtonBuilder();
	}




	/**
	 * Build a new checkbox
	 *
	 * @return the builder for a checkbox
	 */
	public static SuiCheckbox.SuiCheckBoxBuilder checkBox() {
		return new SuiCheckbox.SuiCheckBoxBuilder();
	}




	/**
	 * Build a new choice box
	 *
	 * @return the builder for a choice box
	 */
	public static SuiChoiceBox.SuiChoiceBoxBuilder choiceBox() {
		return new SuiChoiceBox.SuiChoiceBoxBuilder();
	}




	/**
	 * Build a new combo box
	 *
	 * @return the builder for a combo box
	 */
	public static SuiComboBox.SuiComboBoxBuilder comboBox() {
		return new SuiComboBox.SuiComboBoxBuilder();
	}




	/**
	 * Builds a new component
	 *
	 * @param state    the type of the state
	 * @param renderer the renderer for the component
	 * @param <T>      the generic type of the state
	 * @return the component
	 */
	public static <T extends SuiState> SuiComponent<T> component(final Class<T> state, final SuiComponentRenderer<T> renderer) {
		return new SuiComponent<>(renderer);
	}




	/**
	 * Build a new container
	 *
	 * @return the builder for the container
	 */
	public static SuiContainer.SuiContainerBuilder container() {
		return new SuiContainer.SuiContainerBuilder();
	}




	/**
	 * Build a new date picker
	 *
	 * @return the builder for date picker
	 */
	public static SuiDatePicker.SuiDatePickerBuilder datePicker() {
		return new SuiDatePicker.SuiDatePickerBuilder();
	}




	/**
	 * Build a new h-box
	 *
	 * @return the builder a h-box
	 */
	public static SuiHBox.SuiHBoxBuilder hBox() {
		return new SuiHBox.SuiHBoxBuilder();
	}




	/**
	 * Build a new image
	 *
	 * @return the builder for an image
	 */
	public static SuiImage.SuiImageBuilder image() {
		return new SuiImage.SuiImageBuilder();
	}




	/**
	 * Build a new label
	 *
	 * @return the builder for a label
	 */
	public static SuiLabel.SuiLabelBuilder label() {
		return new SuiLabel.SuiLabelBuilder();
	}




	/**
	 * Build a new labeled slider
	 *
	 * @return the builder for a labeled slider
	 */
	public static SuiLabeledSlider.SuiLabeledSliderBuilder labeledSlider() {
		return new SuiLabeledSlider.SuiLabeledSliderBuilder();
	}




	/**
	 * Build a new list
	 *
	 * @return the builder for a list
	 */
	public static SuiList.SuiListBuilder list() {
		return new SuiList.SuiListBuilder();
	}




	/**
	 * Build a new menu bar
	 *
	 * @return the builder for a menu bar
	 */
	public static SuiMenuBar.SuiMenuBarBuilder menuBar() {
		return new SuiMenuBar.SuiMenuBarBuilder();
	}




	/**
	 * Build a new scroll pane
	 *
	 * @return the builder for a scroll pane
	 */
	public static SuiScrollPane.SuiScrollPaneBuilder scrollPane() {
		return new SuiScrollPane.SuiScrollPaneBuilder();
	}




	/**
	 * Build a new separator
	 *
	 * @return the builder for a separator
	 */
	public static SuiSeparator.SuiSeparatorBuilder separator() {
		return new SuiSeparator.SuiSeparatorBuilder();
	}




	/**
	 * Build a new slider
	 *
	 * @return the builder for a slider
	 */
	public static SuiSlider.SuiSliderBuilder slider() {
		return new SuiSlider.SuiSliderBuilder();
	}




	/**
	 * Build a new spinner
	 *
	 * @return the builder for a spinner
	 */
	public static SuiSpinner.SuiSpinnerBuilder spinner() {
		return new SuiSpinner.SuiSpinnerBuilder();
	}




	/**
	 * Build a new split pane
	 *
	 * @return the builder for a split pane
	 */
	public static SuiSplitPane.SuiSplitPaneBuilder splitPane() {
		return new SuiSplitPane.SuiSplitPaneBuilder();
	}




	/**
	 * Build a new tab pane
	 *
	 * @return the builder for a tab pane
	 */
	public static SuiTabPane.SuiTabPaneBuilder tabPane() {
		return new SuiTabPane.SuiTabPaneBuilder();
	}




	/**
	 * Build a new text area
	 *
	 * @return the builder for a text area
	 */
	public static SuiTextArea.SuiTextAreaBuilder textArea() {
		return new SuiTextArea.SuiTextAreaBuilder();
	}




	/**
	 * Build a new text field
	 *
	 * @return the builder for a text field
	 */
	public static SuiTextField.SuiTextFieldBuilder textField() {
		return new SuiTextField.SuiTextFieldBuilder();
	}




	/**
	 * Build a new v-box
	 *
	 * @return the builder for a v-box
	 */
	public static SuiVBox.SuiVBoxBuilder vBox() {
		return new SuiVBox.SuiVBoxBuilder();
	}


}
