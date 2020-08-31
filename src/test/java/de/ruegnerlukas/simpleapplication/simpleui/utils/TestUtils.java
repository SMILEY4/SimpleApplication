package de.ruegnerlukas.simpleapplication.simpleui.utils;

import de.ruegnerlukas.simpleapplication.simpleui.elements.SuiNode;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

	public static void assertNode(SuiNode node, Class<?> nodeType) {
		assertThat(node).isNotNull();
		assertThat(node.getNodeType()).isEqualTo(nodeType);
	}


}
