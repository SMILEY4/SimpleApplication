package de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ExtendedPane extends Pane {


	/**
	 * The custom layout function or null.
	 */
	@Getter
	@Setter
	private LayoutFunction layoutFunction = null;




	@Override
	protected void layoutChildren() {
		if (layoutFunction != null) {
			layoutFunction.layout(this, this.getChildrenUnmodifiable());
		} else {
			super.layoutChildren();
		}
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


}
