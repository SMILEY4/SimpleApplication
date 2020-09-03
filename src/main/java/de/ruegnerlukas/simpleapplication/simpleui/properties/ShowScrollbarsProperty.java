package de.ruegnerlukas.simpleapplication.simpleui.properties;

import de.ruegnerlukas.simpleapplication.simpleui.builders.MasterNodeHandlers;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.MutationResult;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

import java.util.Optional;

public class ShowScrollbarsProperty extends Property {


	/**
	 * The behaviour of the horizontal scrollbar.
	 */
	@Getter
	private final ScrollPane.ScrollBarPolicy horizontal;

	/**
	 * The behaviour of the vertical scrollbar.
	 */
	@Getter
	private final ScrollPane.ScrollBarPolicy vertical;




	/**
	 * @param horizontal the behaviour of the horizontal scrollbar.
	 * @param vertical   the behaviour of the vertical scrollbar.
	 */
	public ShowScrollbarsProperty(final ScrollPane.ScrollBarPolicy horizontal, final ScrollPane.ScrollBarPolicy vertical) {
		super(ShowScrollbarsProperty.class);
		this.horizontal = Optional.ofNullable(horizontal).orElse(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		this.vertical = Optional.ofNullable(vertical).orElse(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	}




	@Override
	protected boolean isPropertyEqual(final Property other) {
		return horizontal == ((ShowScrollbarsProperty) other).getHorizontal()
				&& vertical == ((ShowScrollbarsProperty) other).getVertical();
	}




	@Override
	public String printValue() {
		return "horz=" + getHorizontal() + " vert=" + getVertical();
	}




	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<ShowScrollbarsProperty, ScrollPane> {


		@Override
		public void build(final MasterNodeHandlers nodeHandlers,
						  final SuiNode node,
						  final ShowScrollbarsProperty property,
						  final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
		}




		@Override
		public MutationResult update(final MasterNodeHandlers nodeHandlers,
									 final ShowScrollbarsProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MasterNodeHandlers nodeHandlers,
									 final ShowScrollbarsProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			fxNode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			return MutationResult.MUTATED;
		}

	}


}



