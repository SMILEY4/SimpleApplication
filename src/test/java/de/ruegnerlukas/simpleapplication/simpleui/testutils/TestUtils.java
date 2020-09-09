package de.ruegnerlukas.simpleapplication.simpleui.testutils;


import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

	public static void assertNode(SuiNode node, Class<?> nodeType) {
		assertThat(node).isNotNull();
		assertThat(node.getNodeType()).isEqualTo(nodeType);
	}


}
