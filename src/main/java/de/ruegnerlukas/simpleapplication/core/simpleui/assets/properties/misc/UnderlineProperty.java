package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.control.Labeled;
import lombok.Getter;

import java.util.function.BiFunction;

public class UnderlineProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<UnderlineProperty, UnderlineProperty, Boolean> COMPARATOR =
			(a, b) -> a.isUnderlined() == b.isUnderlined();

	/**
	 * Whether the text is underlined.
	 */
	@Getter
	private final boolean underlined;




	/**
	 * @param underlined whether the element is underlined
	 */
	public UnderlineProperty(final boolean underlined) {
		super(UnderlineProperty.class, COMPARATOR);
		this.underlined = underlined;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @return this builder for chaining
		 */
		default T underlined() {
			return underlined(true);
		}


		/**
		 * @param underlined whether the text is underlined
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T underlined(final boolean underlined) {
			getBuilderProperties().add(new UnderlineProperty(underlined));
			return (T) this;
		}

	}






	public static class LabeledUpdatingBuilder implements PropFxNodeUpdatingBuilder<UnderlineProperty, Labeled> {


		@Override
		public void build(final SuiNode node, final UnderlineProperty property, final Labeled fxNode) {
			fxNode.setUnderline(property.isUnderlined());
		}




		@Override
		public MutationResult update(final UnderlineProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setUnderline(property.isUnderlined());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final UnderlineProperty property, final SuiNode node, final Labeled fxNode) {
			fxNode.setUnderline(false);
			return MutationResult.MUTATED;
		}

	}


}



