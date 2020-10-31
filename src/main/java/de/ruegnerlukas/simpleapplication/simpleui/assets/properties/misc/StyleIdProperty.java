package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

public class StyleIdProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<StyleIdProperty, StyleIdProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getStyleId(), b.getStyleId());


	/**
	 * The style id.
	 */
	@Getter
	private final String styleId;




	/**
	 * @param styleId the style id. Set to null to use the id-property
	 */
	public StyleIdProperty(final String styleId) {
		super(StyleIdProperty.class, COMPARATOR);
		this.styleId = styleId;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param styleId the style id
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T styleId(final String styleId) {
			Validations.INPUT.notNull(styleId).exception("The style-id may not be null.");
			getBuilderProperties().add(new StyleIdProperty(styleId));
			return (T) this;
		}

		/**
		 * Use the id from the id-property as style-id
		 *
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T styleIdFromId() {
			getBuilderProperties().add(new StyleIdProperty(null));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<StyleIdProperty, Node> {


		@Override
		public void build(final SuiNode node, final StyleIdProperty property, final Node fxNode) {
			if (property.getStyleId() != null) {
				fxNode.setId(property.getStyleId());
			} else {
				node.getPropertyStore().getSafe(IdProperty.class).ifPresent(idProperty -> fxNode.setId(idProperty.getId()));
			}
		}




		@Override
		public MutationResult update(final StyleIdProperty property, final SuiNode node, final Node fxNode) {
			if (property.getStyleId() != null) {
				fxNode.setId(property.getStyleId());
			} else {
				node.getPropertyStore().getSafe(IdProperty.class).ifPresent(idProperty -> fxNode.setId(idProperty.getId()));
			}
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final StyleIdProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setId(null);
			return MutationResult.MUTATED;
		}

	}


}



