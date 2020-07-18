package de.ruegnerlukas.simpleapplication.simpleui.properties;

import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.mutation.BaseNodeMutator;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

import java.util.Optional;

public class ShowScrollbarsProperty extends Property {


	@Getter
	private final ScrollPane.ScrollBarPolicy horizontal;

	@Getter
	private final ScrollPane.ScrollBarPolicy vertical;




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




	public static class ShowScrollbarUpdatingBuilder implements PropFxNodeUpdatingBuilder<ShowScrollbarsProperty, ScrollPane> {


		@Override
		public void build(final SceneContext context, final SNode node, final ShowScrollbarsProperty property, final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
		}




		@Override
		public BaseNodeMutator.MutationResult update(final SceneContext context, final ShowScrollbarsProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
			return BaseNodeMutator.MutationResult.MUTATED;
		}




		@Override
		public BaseNodeMutator.MutationResult remove(final SceneContext context, final ShowScrollbarsProperty property, final SNode node, final ScrollPane fxNode) {
			fxNode.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			fxNode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			return BaseNodeMutator.MutationResult.MUTATED;
		}

	}


}



