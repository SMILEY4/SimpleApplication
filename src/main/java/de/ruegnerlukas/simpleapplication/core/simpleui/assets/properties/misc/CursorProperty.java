package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Cursor;
import javafx.scene.Node;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class CursorProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<CursorProperty, CursorProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getCursor(), b.getCursor());


	/**
	 * The cursor.
	 */
	@Getter
	private final Cursor cursor;




	/**
	 * @param cursor the cursor
	 */
	public CursorProperty(final Cursor cursor) {
		super(CursorProperty.class, COMPARATOR);
		Validations.INPUT.notNull(cursor).exception("The cursor may not be null,");
		this.cursor = cursor;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param cursor the cursor
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T cursor(final Cursor cursor) {
			getBuilderProperties().add(new CursorProperty(cursor));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<CursorProperty, Node> {


		@Override
		public void build(final SuiNode node, final CursorProperty property, final Node fxNode) {
			fxNode.setCursor(property.getCursor());
		}




		@Override
		public MutationResult update(final CursorProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setCursor(property.getCursor());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final CursorProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setCursor(Cursor.DEFAULT);
			return MutationResult.MUTATED;
		}

	}


}



