package de.ruegnerlukas.simpleapplication.simpleui.properties;


import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;

import java.util.List;

public class LayoutProperty extends Property {


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
		super(LayoutProperty.class);
		this.layoutId = layoutId;
		this.layoutFunction = layoutFunction;
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return getLayoutId().equals(((LayoutProperty) other).getLayoutId());
	}




	@Override
	public String printValue() {
		return "layoutId=" + getLayoutId();
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






	public static class LayoutUpdatingBuilder implements PropFxNodeUpdatingBuilder<LayoutProperty, Node> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers, final SuiNode node, final LayoutProperty property,
						  final Node fxNode) {
			// do nothing, layout was added to jfx-node during during creation of the jfx-node.
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers, final LayoutProperty property,
									 final SuiNode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers, final LayoutProperty property,
									 final SuiNode node, final Node fxNode) {
			return MutationResult.REQUIRES_REBUILD;
		}

	}


}



