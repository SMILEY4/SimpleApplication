package de.ruegnerlukas.simpleapplication.core.simpleui.testutils;

import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.SuiElements;
import de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc.TextContentProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.SuiServices;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.NodeFactory;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.common.tags.Tags;
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




	public static void assertChildren(SuiNode expected, SuiNode actual) {
		assertThat(actual.getChildNodeStore().count()).isEqualTo(expected.getChildNodeStore().count());
		for (int i = 0; i < expected.getChildNodeStore().count(); i++) {
			final SuiNode childActual = actual.getChildNodeStore().get(i);
			final SuiNode childExpected = expected.getChildNodeStore().get(i);
			assertThat(childActual.getPropertyStore().get(TextContentProperty.class).getText())
					.isEqualTo(childExpected.getPropertyStore().get(TextContentProperty.class).getText());
		}
	}




	public static Pair<SuiNode, SuiNode> buildTest(final NodeFactory factoryOriginal, final NodeFactory factoryTarget) {
		TestState state = new TestState();
		SuiNode original = factoryOriginal.create(state, Tags.empty());
		SuiServices.get().enrichWithFxNodes(original);
		SuiNode target = factoryTarget.create(state, Tags.empty());
		return Pair.of(original, target);
	}




	public static void removeChildNodes(final SuiNode node, final int n) {
		Random random = new Random(SEED);
		List<SuiNode> children = new ArrayList<>(node.getChildNodeStore().getUnmodifiable());
		for (int i = 0; i < n; i++) {
			children.remove(random.nextInt(children.size()));
		}
		node.getChildNodeStore().setChildren(children);
	}




	public static void shuffleChildNodes(final SuiNode node, final int n) {
		Random random = new Random(SEED);
		List<SuiNode> children = new ArrayList<>(node.getChildNodeStore().getUnmodifiable());
		if (n == node.getChildNodeStore().count()) {
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
		node.getChildNodeStore().setChildren(children);
	}




	public static void printChildButtons(String nodeName, SuiNode node) {
		log.info("{}-{}: childCount={}, {}",
				nodeName,
				Integer.toHexString(node.hashCode()),
				node.getChildNodeStore().count(),
				node.getChildNodeStore().stream()
						.map(c -> c.getPropertyStore().get(TextContentProperty.class).getText())
						.map(str -> "\"" + str + "\"")
						.collect(Collectors.toList())
		);

	}




	public static NodeFactory buildVBox(int nChildren, String buttonPrefix) {
		return buildVBox(nChildren, buttonPrefix, false);
	}




	public static NodeFactory buildVBox(int nChildren, String buttonPrefix, boolean withIds) {
		return SuiElements.vBox().items(buildButtons(nChildren, buttonPrefix, withIds));
	}




	public static List<NodeFactory> buildButtons(final int n, final String namePrefix) {
		return buildButtons(n, namePrefix, false);
	}




	public static List<NodeFactory> buildButtons(final int n, final String namePrefix, boolean withId) {
		final List<NodeFactory> items = new ArrayList<>(n);
		if (withId) {
			for (int i = 0; i < n; i++) {
				items.add(SuiElements.button().id("btn" + i).textContent(namePrefix + " " + i));
			}
		} else {
			for (int i = 0; i < n; i++) {
				items.add(SuiElements.button().textContent(namePrefix + " " + i));
			}
		}
		return items;
	}


}
