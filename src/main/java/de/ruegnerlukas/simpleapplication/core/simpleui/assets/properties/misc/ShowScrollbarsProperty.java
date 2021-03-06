package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.ScrollPane;
import lombok.Getter;

import java.util.Optional;
import java.util.function.BiFunction;

public class ShowScrollbarsProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ShowScrollbarsProperty, ShowScrollbarsProperty, Boolean> COMPARATOR =
			(a, b) -> a.getHorizontal() == b.getHorizontal() && a.getVertical() == b.getVertical();

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
		super(ShowScrollbarsProperty.class, COMPARATOR);
		Validations.INPUT.notNull(horizontal).exception("The horizontal scrollbar-policy may not be null.");
		Validations.INPUT.notNull(vertical).exception("The vertical scrollbar-policy may not be null.");
		this.horizontal = Optional.ofNullable(horizontal).orElse(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		this.vertical = Optional.ofNullable(vertical).orElse(ScrollPane.ScrollBarPolicy.AS_NEEDED);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param horizontal the policy of the horizontal scrollbar
		 * @param vertical   the policy of the vertical scrollbar
		 * @return
		 */
		@SuppressWarnings ("unchecked")
		default T showScrollbars(final ScrollPane.ScrollBarPolicy horizontal, final ScrollPane.ScrollBarPolicy vertical) {
			getBuilderProperties().add(new ShowScrollbarsProperty(horizontal, vertical));
			return (T) this;
		}

	}






	public static class ScrollPaneUpdatingBuilder implements PropFxNodeUpdatingBuilder<ShowScrollbarsProperty, ScrollPane> {


		@Override
		public void build(final SuiNode node,
						  final ShowScrollbarsProperty property,
						  final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
		}




		@Override
		public MutationResult update(final ShowScrollbarsProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setHbarPolicy(property.getHorizontal());
			fxNode.setVbarPolicy(property.getVertical());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ShowScrollbarsProperty property,
									 final SuiNode node,
									 final ScrollPane fxNode) {
			fxNode.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			fxNode.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
			return MutationResult.MUTATED;
		}

	}


}



