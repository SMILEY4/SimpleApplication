package de.ruegnerlukas.simpleapplication.simpleui;

import de.ruegnerlukas.simpleapplication.simpleui.builders.NodeFactory;
import de.ruegnerlukas.simpleapplication.simpleui.elements.ElementTestState;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIButton;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIHBox;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUILabel;
import de.ruegnerlukas.simpleapplication.simpleui.elements.SUIVBox;
import de.ruegnerlukas.simpleapplication.simpleui.properties.Properties;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SUIRegistry;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
public class ChildrenPerformanceBenchmark extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




	@Test
	public void addChildrenPropertySimple() {
		log.info("addChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox")
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void addChildrenPropertyComplex() {
		log.info("addChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox")
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void addSomeChildrenPropertySimple() {
		log.info("addSomeChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, true, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void addSomeChildrenPropertyComplex() {
		log.info("addSomeChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, true, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void clearChildrenPropertySimple() {
		log.info("clearChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox")
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void clearChildrenPropertyComplex() {
		log.info("clearChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox")
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void removeSomeChildrenPropertySimple() {
		log.info("removeSomeChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, true, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void removeSomeChildrenPropertyComplex() {
		log.info("removeSomeChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, true, 1234))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void shuffleChildrenPropertySimple() {
		log.info("shuffleChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(shuffle(buildButtonItems(n, false, 1234)))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void shuffleSomeChildrenPropertyComplex() {
		log.info("shuffleSomeChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(shuffle(buildComplexFormItems(n, false, 1234)))
					)
			);
		}
		log.info("-".repeat(50));
	}



	@Test
	public void mutateChildrenPropertySimple() {
		log.info("mutateChildrenPropertySimple");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildButtonItems(n, false, 6789))
					)
			);
		}
		log.info("-".repeat(50));
	}




	@Test
	public void mutateSomeChildrenPropertyComplex() {
		log.info("mutateSomeChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 1234))
					),
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, false, 5678))
					)
			);
		}
		log.info("-".repeat(50));
	}





	private List<NodeFactory> shuffle(List<NodeFactory> list) {
		Collections.shuffle(list, new Random(123));
		return list;
	}




	private List<NodeFactory> buildButtonItems(int n, boolean skipUneven, long seed) {
		final Random random = new Random(seed);
		final List<NodeFactory> items = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			final int number = random.nextInt(10000);
			if (skipUneven && i % 2 == 0) {
				continue;
			}
			items.add(SUIButton.button(
					Properties.id("btn" + i),
					Properties.textContent("Button " + i + "(" + number + ")")
			));
		}
		return items;
	}




	private List<NodeFactory> buildComplexFormItems(int n, boolean skipUneven, long seed) {
		final Random random = new Random(seed);
		final List<NodeFactory> items = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			final int numberA = random.nextInt(10000);
			final int numberB = random.nextInt(10000);
			if (skipUneven && i % 2 == 0) {
				continue;
			}
			items.add(SUIVBox.vbox(
					Properties.id("vbox" + i),
					Properties.minSize(400.0, 60.0),
					Properties.preferredSize(400.0, 60.0),
					Properties.maxSize(10000.0, 1000.0),
					Properties.spacing(5.0),
					Properties.fitToWidth(true),
					Properties.alignment(Pos.CENTER),
					Properties.items(
							SUIHBox.hbox(
									Properties.id("hbox" + i),
									Properties.minSize(50.0, 20.0),
									Properties.maxSize(1000.0, 20.0),
									Properties.items(
											SUILabel.label(
													Properties.id("label" + i),
													Properties.minSize(50.0, 20.0),
													Properties.maxSize(1000.0, 20.0),
													Properties.textContent("Label " + i + "(" + numberA + ")")
											),
											SUIButton.button(
													Properties.id("button" + i),
													Properties.minSize(50.0, 20.0),
													Properties.maxSize(1000.0, 20.0),
													Properties.textContent("Button " + i + "(" + numberB + ")")
											)
									)
							)
					)
			));
		}
		return items;
	}




	private void doTest(int index, String description, NodeFactory factoryOriginal, NodeFactory factoryTarget) {
		final ElementTestState state = new ElementTestState();
		final SUISceneContext context = new SUISceneContext(state, factoryOriginal);

		StopWatch watchBuildOriginal = new StopWatch();
		StopWatch watchBuildTarget = new StopWatch();
		StopWatch watchMutate = new StopWatch();

		watchBuildOriginal.start();
		final SUINode original = context.getRootNode();
		watchBuildOriginal.stop();

		watchBuildTarget.start();
		final SUINode target = factoryTarget.create(state);
		watchBuildTarget.stop();

		watchMutate.start();
		final SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		watchMutate.stop();

		printResult(index, description, watchBuildOriginal.getTime(), watchBuildTarget.getTime(), watchMutate.getTime());
	}




	private static void printResult(int index, String description, final long millisBuildOriginal, final long millisBuildTarget, final long millisMutate) {
//		final String message = System.lineSeparator()
//				+ "=".repeat(30) + System.lineSeparator()
//				+ description + System.lineSeparator()
//				+ "build original: {}ms" + System.lineSeparator()
//				+ "  build target: {}ms" + System.lineSeparator()
//				+ "        mutate: {}ms" + System.lineSeparator()
//				+ "=".repeat(30);
//		log.info(message, millisBuildOriginal, millisBuildTarget, millisMutate);
		log.info("{};{};{};{}", index, millisBuildOriginal, millisBuildTarget, millisMutate);
	}


}
