package de.ruegnerlukas.simpleapplication.simpleui;


import de.ruegnerlukas.simpleapplication.simpleui.properties.Property;

public class SNodeTreePrinter {


	private static final String IDENT = "   ";




	public static void print(SNode root) {
		print(root, 0);
	}




	public static void print(SNode node, int level) {
		final String BASE_IDENT = IDENT.repeat(level);
		System.out.println(BASE_IDENT + node.getNodeType().getSimpleName() + ":");
		if (!node.getProperties().isEmpty()) {
			System.out.println(BASE_IDENT + IDENT + "properties:");
			for (Property prop : node.getProperties().values()) {
				System.out.println(BASE_IDENT + IDENT + IDENT + prop.getKey().getSimpleName() + ": " + prop.printValue());
			}
		}
		if (!node.getChildren().isEmpty()) {
			System.out.println(BASE_IDENT + IDENT + "children:");
			for (SNode child : node.getChildren()) {
				print(child, level + 2);
			}
		}
	}


}
