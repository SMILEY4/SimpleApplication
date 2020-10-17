package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.application.Platform;
import javafx.scene.control.SplitPane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class SplitDividerPositionProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<SplitDividerPositionProperty, SplitDividerPositionProperty, Boolean> COMPARATOR =
			(a, b) -> {
				if (a.isFixed() != b.isFixed()) {
					return false;
				} else if (a.getPositions().size() != b.getPositions().size()) {
					return false;
				} else {
					for (int i = 0; i < a.getPositions().size(); i++) {
						final Number posA = a.getPositions().get(i);
						final Number posB = b.getPositions().get(i);
						return NumberUtils.isEqual(posA, posB);
					}
				}
				return true;
			};


	/**
	 * The positions of the dividers
	 */
	@Getter
	private final List<Number> positions;


	/**
	 * Whether the user can drag the dividers to change their positions.
	 */
	@Getter
	private final boolean fixed;




	/**
	 * @param fixed     whether the user can drag the dividers to change their positions.
	 * @param positions the positions of the dividers
	 */
	public SplitDividerPositionProperty(final boolean fixed, final Number... positions) {
		this(fixed, List.of(positions));
	}




	/**
	 * @param fixed     whether the user can drag the dividers to change their positions.
	 * @param positions the positions of the dividers
	 */
	public SplitDividerPositionProperty(final boolean fixed, final List<Number> positions) {
		super(SplitDividerPositionProperty.class, COMPARATOR);
		Validations.INPUT.notNull(positions).exception("The position-list may not be null.");
		Validations.INPUT.containsNoNull(positions).exception("The position-list may not contain null-elements.");
		this.positions = new ArrayList<>(positions);
		this.fixed = fixed;
	}




	/**
	 * @return the divider positions as an array.
	 */
	public double[] getPositionsArray() {
		final double[] positions = new double[getPositions().size()];
		for (int i = 0; i < positions.length; i++) {
			positions[i] = getPositions().get(i).doubleValue();
		}
		return positions;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param positions the positions as percentage (between 0 and 1).
		 * @return this builder for chaining
		 */
		default T dividerPositions(final Number... positions) {
			return dividerPositions(false, positions);
		}


		/**
		 * @param positions the positions as percentage (between 0 and 1).
		 * @return this builder for chaining
		 */
		default T dividerPositions(final List<Number> positions) {
			return dividerPositions(false, positions);
		}


		/**
		 * @param fixed     whether the user can move the dividers
		 * @param positions the positions as percentage (between 0 and 1).
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T dividerPositions(final boolean fixed, final Number... positions) {
			getBuilderProperties().add(new SplitDividerPositionProperty(fixed, positions));
			return (T) this;
		}

		/**
		 * @param fixed     whether the user can move the dividers
		 * @param positions the positions as percentage (between 0 and 1).
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T dividerPositions(final boolean fixed, final List<Number> positions) {
			getBuilderProperties().add(new SplitDividerPositionProperty(fixed, positions));
			return (T) this;
		}

	}






	public static class SplitPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<SplitDividerPositionProperty, SplitPane> {


		@Override
		public void build(final SuiNode node,
						  final SplitDividerPositionProperty property,
						  final SplitPane fxNode) {
			((ExtendedSplitPane) fxNode).setFixedDividers(property.isFixed());
			Platform.runLater(() -> fxNode.setDividerPositions(property.getPositionsArray()));
		}




		@Override
		public MutationResult update(final SplitDividerPositionProperty property,
									 final SuiNode node,
									 final SplitPane fxNode) {
			((ExtendedSplitPane) fxNode).setFixedDividers(property.isFixed());
			Platform.runLater(() -> fxNode.setDividerPositions(property.getPositionsArray()));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final SplitDividerPositionProperty property,
									 final SuiNode node,
									 final SplitPane fxNode) {
			((ExtendedSplitPane) fxNode).setFixedDividers(false);
			return MutationResult.MUTATED;
		}

	}


}



