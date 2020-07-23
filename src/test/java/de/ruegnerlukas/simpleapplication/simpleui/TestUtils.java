package de.ruegnerlukas.simpleapplication.simpleui;

import static org.assertj.core.api.Assertions.assertThat;

public class TestUtils {

	public static void assertNode(SUINode node, Class<?> nodeType) {
		assertThat(node).isNotNull();
		assertThat(node.getNodeType()).isEqualTo(nodeType);
	}


}
