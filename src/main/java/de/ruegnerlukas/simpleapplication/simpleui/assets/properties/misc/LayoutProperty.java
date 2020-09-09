package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;

public class LayoutProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<LayoutProperty, LayoutProperty, Boolean> COMPARATOR =
			(a, b) -> a.getLayoutId().equals(b.getLayoutId());

	/**
	 * The id of the layout. This property will only be mutated, when this id changes.
	 */
	@Getter
	private final String layoutId;

	/**
	 * The function used to calculate the layout of the child nodes
	 */
	@Getter
	private final LayoutFunction layoutFunction;




	/**
	 * @param layoutId       the id of the layout. This property will only be mutated, when this id changes.
	 * @param layoutFunction the function used to calculate the layout of the child nodes
	 */
	public LayoutProperty(final String layoutId, final LayoutFunction layoutFunction) {
		super(LayoutProperty.class, COMPARATOR);
		this.layoutId = layoutId;
		this.layoutFunction = layoutFunction;
	}




	public interface LayoutFunction {


		/**
		 * Layout the given nodes.
		 *
		 * @param parent the parent pane containing the given nodes
		 * @param nodes  the unmodifiable list of child nodes of the parent pane
		 */
		void layout(Pane parent, List<Node> nodes);

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<LayoutProperty, Node> {


		@Override
		public void build(final SuiNode node,
						  final LayoutProperty property,
						  final Node fxNode) {
			// do nothing, layout was added to jfx-node during during creation of the jfx-node.
		}




		@Override
		public MutationResult update(final LayoutProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final LayoutProperty property,
									 final SuiNode node,
									 final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}


}



