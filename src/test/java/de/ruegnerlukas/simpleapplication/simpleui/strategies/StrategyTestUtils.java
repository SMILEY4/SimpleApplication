package de.ruegnerlukas.simpleapplication.simpleui.strategies;

import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.simpleui.SUINode;
import de.ruegnerlukas.simpleapplication.simpleui.SUISceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.ElementTestState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.properties.TextContentProperty;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class StrategyTestUtils {


	public static final int SEED = 1234;




	public static void assertChildren(SUINode expected, SUINode actual) {
		assertThat(actual.childCount()).isEqualTo(expected.childCount());
		for (int i = 0; i < expected.childCount(); i++) {
			final SUINode childActual = actual.getChild(i);
			final SUINode childExpected = expected.getChild(i);
			assertThat(childActual.getProperty(TextContentProperty.class).getText())
					.isEqualTo(childExpected.getProperty(TextContentProperty.class).getText());
		}
	}




	public static Triplet<SUISceneContext, SUINode, SUINode> buildTest(final NodeFactory factoryOriginal, final NodeFactory factoryTarget) {
		ElementTestState state = new ElementTestState();
		SUISceneContext context = new SUISceneContext(state, factoryOriginal);
		SUINode original = context.getRootNode();
		SUINode target = factoryTarget.create(state);
		return Triplet.of(context, original, target);
	}




	public static void removeChildNodes(final SUINode node, final int n) {
		Random random = new Random(SEED);
		List<SUINode> children = new ArrayList<>(node.getChildrenUnmodifiable());
		for (int i = 0; i < n; i++) {
			children.remove(random.nextInt(children.size()));
		}
		node.setChildren(children, true);
	}




	public static void shuffleChildNodes(final SUINode node, final int n) {
		Random random = new Random(SEED);
		List<SUINode> children = new ArrayList<>(node.getChildrenUnmodifiable());
		if (n == node.childCount()) {
			Collections.shuffle(children, random);
		} else {
			for (int i = 0; i < n; i++) {
				int indexA = random.nextInt(children.size());
				int indexB = random.nextInt(children.size());
				if (indexA == indexB) {
					i--;
				} else {
					Collections.swap(children, indexA, indexB);
				}
			}
		}
		node.setChildren(children, true);
	}




	public static void printChildButtons(String nodeName, SUINode node) {
		log.info("{}-{}: childCount={}, {}",
				nodeName,
				Integer.toHexString(node.hashCode()),
				node.childCount(),
				node.getChildrenUnmodifiable().stream()
						.map(c -> c.getProperty(TextContentProperty.class).getText())
						.map(str -> "\"" + str + "\"")
						.collect(Collectors.toList())
		);

	}




	public static NodeFactory buildVBox(int nChildren, String buttonPrefix) {
		return buildVBox(nChildren, buttonPrefix, false);
	}

	public static NodeFactory buildVBox(int nChildren, String buttonPrefix, boolean withIds) {
		return SUIVBox.vbox(
				Properties.items(buildButtons(nChildren, buttonPrefix, withIds))
		);
	}




	public static List<NodeFactory> buildButtons(final int n, final String namePrefix) {
		return buildButtons(n, namePrefix, false);
	}




	public static List<NodeFactory> buildButtons(final int n, final String namePrefix, boolean withId) {
		final List<NodeFactory> items = new ArrayList<>(n);
		if (withId) {
			for (int i = 0; i < n; i++) {
				items.add(SUIButton.button(
						Properties.id("btn" + i),
						Properties.textContent(namePrefix + " " + i)
				));
			}
		} else {
			for (int i = 0; i < n; i++) {
				items.add(SUIButton.button(
						Properties.textContent(namePrefix + " " + i)
				));
			}
		}
		return items;
	}


}
