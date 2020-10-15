package de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.simpleui.assets.elements.suimenu.SuiAbstractMenuItem;
import de.ruegnerlukas.simpleapplication.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiProperty;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import lombok.Getter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class MenuContentProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<MenuContentProperty, MenuContentProperty, Boolean> COMPARATOR = (a, b) -> {
		if (a.getMenuItems().size() != b.getMenuItems().size()) {
			return false;
		}
		for (int i = 0; i < a.getMenuItems().size(); i++) {
			SuiAbstractMenuItem itemA = a.getMenuItems().get(i);
			SuiAbstractMenuItem itemB = b.getMenuItems().get(i);
			if (!itemA.equals(itemB)) {
				return false;
			}
		}
		return true;
	};


	/**
	 * The alignment.
	 */
	@Getter
	private final List<SuiAbstractMenuItem> menuItems;




	/**
	 * @param menuItems the top level items of the menu
	 */
	public MenuContentProperty(final List<SuiAbstractMenuItem> menuItems) {
		super(MenuContentProperty.class, COMPARATOR);
		this.menuItems = menuItems;
	}




	public static class MenuBarUpdatingBuilder implements PropFxNodeUpdatingBuilder<MenuContentProperty, MenuBar> {


		@Override
		public void build(final SuiNode node, final MenuContentProperty property, final MenuBar fxNode) {
			fxNode.getMenus().setAll(buildMenus(property));
		}




		@Override
		public MutationResult update(final MenuContentProperty property, final SuiNode node, final MenuBar fxNode) {
			fxNode.getMenus().setAll(buildMenus(property));
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final MenuContentProperty property, final SuiNode node, final MenuBar fxNode) {
			fxNode.getMenus().clear();
			return MutationResult.MUTATED;
		}




		/**
		 * Builds the list of top level properties with their complete subtree.
		 *
		 * @param property the property
		 * @return the list of menus
		 */
		private List<Menu> buildMenus(final MenuContentProperty property) {
			return property.getMenuItems().stream()
					.map(SuiAbstractMenuItem::toFxMenuItem)
					.filter(item -> item instanceof Menu)
					.map(menu -> (Menu) menu)
					.collect(Collectors.toList());
		}


	}


}



