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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testfx.framework.junit.ApplicationTest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Slf4j
@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class ChildrenPerformanceBenchmark extends ApplicationTest {


	@Override
	public void start(Stage stage) {
		SUIRegistry.initialize();
	}




//	@Test
//	public void testA() {
//		final int N = 50_000 / 2;
//
//		NodeFactory vboxFactory = SUIVBox.vbox(
//				Properties.id("myVBox"),
//				Properties.items(buildComplexFormItems(50_000, false, 1234))
//		);
//
//		final ElementTestState state = new ElementTestState();
//		final SUISceneContext context = new SUISceneContext(state, vboxFactory);
//
//		final VBox vbox = (VBox) context.getRootNode().getFxNode();
//
//		final Random random = new Random(1234);
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		for (int i = 0; i < N; i++) {
//			int index = random.nextInt(vbox.getChildren().size());
//			vbox.getChildren().remove(index);
//		}
//		stopWatch.stop();
//
//		log.info("removing {} nodes individually took {}ms", N, stopWatch.getTime());
//
//	}
//
//
//
//
//	@Test
//	public void testB() {
//		final int N = 50_000 / 2;
//
//		NodeFactory vboxFactory = SUIVBox.vbox(
//				Properties.id("myVBox"),
//				Properties.items(buildComplexFormItems(50_000, false, 1234))
//		);
//
//		final ElementTestState state = new ElementTestState();
//		final SUISceneContext context = new SUISceneContext(state, vboxFactory);
//
//		final VBox vbox = (VBox) context.getRootNode().getFxNode();
//
//
//		final List<Node> nodes = new ArrayList<>();
//		nodes.addAll(vbox.getChildren());
//
//		final Random random = new Random(1234);
//		for (int i = 0; i < N; i++) {
//			int index = random.nextInt(nodes.size());
//			nodes.remove(index);
//		}
//
//
//		StopWatch stopWatch = new StopWatch();
//		stopWatch.start();
//		vbox.getChildren().setAll(nodes);
//		stopWatch.stop();
//
//		log.info("removing {} nodes in batch took {}ms", N, stopWatch.getTime());
//	}



//	@Test
//	public void testCreateComplex() {
//
//		for (int i = 0; i <= 1000; i += 100) {
//			final int n = Math.max(i, 1) * 100;
//
//			NodeFactory vbox = SUIVBox.vbox(
//					Properties.id("myVBox"),
//					Properties.items(buildComplexFormItems(n, false, 1234))
//			);
//
//			final ElementTestState state = new ElementTestState();
//			final SUISceneContext context = new SUISceneContext(state, vbox);
//			StopWatch stopWatch = new StopWatch();
//			stopWatch.start();
//			final SUINode original = context.getRootNode();
//			stopWatch.stop();
//
//			log.info("{};{}", n, stopWatch.getTime());
//		}
//
//
//	}



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
	public void addFewChildrenPropertyComplex() {
		log.info("addFewChildrenPropertyComplex");
		for (int i = 0; i <= 1000; i += 100) {
			final int n = Math.max(i, 1) * 100;
			doTest(n, "n = " + n,
					SUIVBox.vbox(
							Properties.id("myVBox"),
							Properties.items(buildComplexFormItems(n, n, true, 1234))
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
//		log.info("build {} simple items", items.size());
		return items;
	}




	private List<NodeFactory> buildComplexFormItems(int n, boolean skipUneven, long seed) {
		return buildComplexFormItems(n, skipUneven ? 2 : 1, skipUneven ? true : false, seed);
	}




	private List<NodeFactory> buildComplexFormItems(int n, int skipMod, boolean modIsZero, long seed) {
		final Random random = new Random(seed);
		final List<NodeFactory> items = new ArrayList<>(n);
		for (int i = 0; i < n; i++) {
			final int numberA = random.nextInt(10000);
			final int numberB = random.nextInt(10000);
			if ((i % skipMod == 0) == modIsZero) {
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
//		log.info("build {} complex items", items.size());
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
		SUIVBox.invalidations.set(0);
		final SUINode mutatedNode = context.getMasterNodeHandlers().getMutator().mutate(original, target);
		watchMutate.stop();

		printResult(index, description, watchBuildOriginal.getTime(), watchBuildTarget.getTime(), watchMutate.getTime());
//		log.info("vbox invalidations: {}", SUIVBox.invalidations.getAndSet(0));
	}




	private static void printResult(int index, String description, final long millisBuildOriginal, final long millisBuildTarget,
									final long millisMutate) {
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
