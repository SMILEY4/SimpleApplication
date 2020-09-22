package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.NumberUtils;
import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements.ExtendedSplitPane;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
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



