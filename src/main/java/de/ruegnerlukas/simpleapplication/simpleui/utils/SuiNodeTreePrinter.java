package de.ruegnerlukas.simpleapplication.simpleui.utils;


import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

public final class SuiNodeTreePrinter {


	/**
	 * The indentation string for each level.
	 */
	private static final String IDENT = "   ";




	/**
	 * Hidden constructor for utility class.
	 */
	private SuiNodeTreePrinter() {
		// do nothing
	}




	/**
	 * Prints nodes and properties of the subtree with the given root node.
	 *
	 * @param root the root node
	 */
	public static void print(final SuiNode root) {
		print(root, 0);
	}




	/**
	 * Prints nodes and properties of the subtree with the given root node.
	 *
	 * @param node  the root node
	 * @param level the current depth in the tree
	 */
	private static void print(final SuiNode node, final int level) {
		final String baseIdent = IDENT.repeat(level);
		System.out.println(baseIdent + node.getNodeType().getSimpleName() + ":");
		if (!node.getProperties().isEmpty()) {
			System.out.println(baseIdent + IDENT + "properties:");
			for (Property prop : node.getProperties().values()) {
				System.out.println(baseIdent + IDENT + IDENT + prop.getKey().getSimpleName() + ": " + prop.printValue());
			}
		}
		if (node.hasChildren()) {
			System.out.println(baseIdent + IDENT + "children:");
			for (SuiNode child : node.getChildrenUnmodifiable()) {
				print(child, level + 2);
			}
		}
	}


}
