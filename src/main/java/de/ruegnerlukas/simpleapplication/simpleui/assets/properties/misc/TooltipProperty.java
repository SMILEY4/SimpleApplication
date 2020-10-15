package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.SuiLabeledSlider;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Control;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.function.BiFunction;

public class TooltipProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<TooltipProperty, TooltipProperty, Boolean> COMPARATOR =
			(a, b) -> a.getText().equals(b.getText());

	/**
	 * The text shown in the tooltip.
	 */
	@Getter
	private final String text;

	/**
	 * Whether to wrap the text or null.
	 */
	@Getter
	private final Boolean wrapText;


	/**
	 * The preferred width of the tooltip or null.
	 */
	@Getter
	private final Number prefWidth;




	/**
	 * @param text the text shown in the tooltip
	 */
	public TooltipProperty(final String text) {
		this(text, null, null);
	}




	/**
	 * @param text      the text shown in the tooltip
	 * @param wrapText  whether to wrap the text or null
	 * @param prefWidth the preferred width of the tooltip or null
	 */
	public TooltipProperty(final String text, final Boolean wrapText, final Number prefWidth) {
		super(TooltipProperty.class, COMPARATOR);
		this.text = text;
		this.wrapText = wrapText;
		this.prefWidth = prefWidth;
	}




	/**
	 * Creates a new tooltip from this property.
	 *
	 * @return the created tooltip
	 */
	private Tooltip buildTooltip() {
		Tooltip tooltip = new Tooltip();
		tooltip.setText(getText());
		if (wrapText != null && prefWidth != null) {
			tooltip.setWrapText(wrapText);
			tooltip.setPrefWidth(prefWidth.doubleValue());
		}
		return tooltip;
	}




	public static class ControlUpdatingBuilder implements PropFxNodeUpdatingBuilder<TooltipProperty, Control> {


		@Override
		public void build(final SuiNode node, final TooltipProperty property, final Control fxNode) {
			fxNode.setTooltip(property.buildTooltip());
		}




		@Override
		public MutationResult update(final TooltipProperty property, final SuiNode node, final Control fxNode) {
			if (fxNode.getTooltip() == null) {
				fxNode.setTooltip(property.buildTooltip());
			} else {
				fxNode.getTooltip().setText(property.getText());
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TooltipProperty property, final SuiNode node, final Control fxNode) {
			fxNode.setTooltip(null);
			return MutationResult.MUTATED;
		}


	}






	public static class LabeledSliderUpdatingBuilder implements PropFxNodeUpdatingBuilder<TooltipProperty, Pane> {


		@Override
		public void build(final SuiNode node, final TooltipProperty property, final Pane fxNode) {
			final TextField label = SuiLabeledSlider.getLabel(fxNode);
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			if (label != null) {
				label.setTooltip(property.buildTooltip());
			}
			if (slider != null) {
				slider.setTooltip(property.buildTooltip());
			}
		}




		@Override
		public MutationResult update(final TooltipProperty property, final SuiNode node, final Pane fxNode) {
			final TextField label = SuiLabeledSlider.getLabel(fxNode);
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			if (label != null) {
				if (label.getTooltip() == null) {
					label.setTooltip(property.buildTooltip());
				} else {
					label.getTooltip().setText(property.getText());
				}
			}
			if (slider != null) {
				if (slider.getTooltip() == null) {
					slider.setTooltip(property.buildTooltip());
				} else {
					slider.getTooltip().setText(property.getText());
				}
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final TooltipProperty property, final SuiNode node, final Pane fxNode) {
			final TextField label = SuiLabeledSlider.getLabel(fxNode);
			final Slider slider = SuiLabeledSlider.getSlider(fxNode);
			if (label != null) {
				label.setTooltip(null);
			}
			if (slider != null) {
				slider.setTooltip(null);
			}
			return MutationResult.MUTATED;
		}


	}

}
