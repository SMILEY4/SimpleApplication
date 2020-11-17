package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;

import de.ruegnerlukas.simpleapplication.common.tags.Tags;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.events.ColorSelectedEventData;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEmittingEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.events.SuiEventListener;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.ColorPicker;
import lombok.Getter;

public class OnSelectedColorEventProperty extends AbstractEventListenerProperty<ColorSelectedEventData> {


	/**
	 * The listener for events with {@link ColorSelectedEventData}.
	 */
	@Getter
	private final SuiEventListener<ColorSelectedEventData> listener;




	/**
	 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
	 * @param listener   the listener for events with {@link ColorSelectedEventData}.
	 */
	public OnSelectedColorEventProperty(final String propertyId, final SuiEventListener<ColorSelectedEventData> listener) {
		super(OnSelectedColorEventProperty.class, propertyId);
		Validations.INPUT.notNull(listener).exception("The listener may not be null");
		this.listener = listener;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param propertyId see {@link de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty#getPropertyId()}.
		 * @param listener   the listener for events with {@link ColorSelectedEventData}.
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T eventSelectedColor(final String propertyId, final SuiEventListener<ColorSelectedEventData> listener) {
			getBuilderProperties().add(new OnSelectedColorEventProperty(propertyId, listener));
			return (T) this;
		}

		/**
		 * @param tags the tags to attach to the emitted event
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T emitEventSelectedColor(final Tags tags) {
			getBuilderProperties().add(new OnSelectedColorEventProperty(".", new SuiEmittingEventListener<>(tags)));
			return (T) this;
		}

	}






	public static class ColorPickerUpdatingBuilder implements PropFxNodeUpdatingBuilder<OnSelectedColorEventProperty, ColorPicker> {


		@Override
		public void build(final SuiNode node, final OnSelectedColorEventProperty property, final ColorPicker fxNode) {
			setListener(fxNode, property);
		}




		@Override
		public MutationResult update(final OnSelectedColorEventProperty property, final SuiNode node, final ColorPicker fxNode) {
			setListener(fxNode, property);
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final OnSelectedColorEventProperty property, final SuiNode node, final ColorPicker fxNode) {
			fxNode.setOnAction(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Attaches the listener to the given fx node.
		 *
		 * @param fxNode   the fx node to listen to
		 * @param property the property with the listener to add
		 */
		private void setListener(final ColorPicker fxNode, final OnSelectedColorEventProperty property) {
			fxNode.setOnAction(e -> property.getListener().onEvent(new ColorSelectedEventData(fxNode.getValue(), e)));
		}

	}

}
