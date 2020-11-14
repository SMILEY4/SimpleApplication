package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements.ExtendedAccordion;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import lombok.Getter;

import java.util.Objects;
import java.util.function.BiFunction;

@Getter
public class ExpandedSectionProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<ExpandedSectionProperty, ExpandedSectionProperty, Boolean> COMPARATOR =
			(a, b) -> Objects.equals(a.getTitle(), b.getTitle());

	/**
	 * The title of the expanded section or null
	 */
	private final String title;




	/**
	 * @param title the title of the expanded section or null
	 */
	public ExpandedSectionProperty(final String title) {
		super(ExpandedSectionProperty.class, COMPARATOR);
		this.title = title;
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param title the title of the expanded section or null
		 * @return this builder for chaining
		 */
		@SuppressWarnings ("unchecked")
		default T expandedSection(final String title) {
			getBuilderProperties().add(new ExpandedSectionProperty(title));
			return (T) this;
		}

	}






	public static class AccordionUpdatingBuilder implements PropFxNodeUpdatingBuilder<ExpandedSectionProperty, ExtendedAccordion> {


		@Override
		public void build(final SuiNode node, final ExpandedSectionProperty property, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSection(property.getTitle());
		}




		@Override
		public MutationResult update(final ExpandedSectionProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			fxNode.setExpandedSection(property.getTitle());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final ExpandedSectionProperty property, final SuiNode node, final ExtendedAccordion fxNode) {
			return MutationResult.MUTATED;
		}

	}


}
