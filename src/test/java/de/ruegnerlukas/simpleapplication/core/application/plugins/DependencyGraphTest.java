package de.ruegnerlukas.simpleapplication.core.application.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.ValidateStateException;
import de.ruegnerlukas.simpleapplication.core.plugins.DependencyGraph;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DependencyGraphTest {


	@Test
	public void testGraph() {
		final DependencyGraph graph = buildTestGraph();

		assertThat(graph.getIds()).containsExactlyInAnyOrder("A", "B", "C", "D", "E", "F");
		assertThat(graph.getRoots()).containsExactlyInAnyOrder("A", "F");

		graph.remove("F");
		assertThat(graphContainsIds(graph, "A", "B", "C", "D", "E")).isTrue();
		assertThat(graphContainsIds(graph, "F")).isFalse();

		graph.remove("B");
		assertThat(graphContainsIds(graph, "A", "C", "D", "E")).isTrue();
		assertThat(graphContainsIds(graph, "F", "B")).isFalse();
		assertThat(graph.getDependencies("D")).containsExactlyInAnyOrder("C");
		assertThat(graph.getDependencies("E")).isEmpty();

		graph.remove("C");
		assertThat(graphContainsIds(graph, "A", "D", "E")).isTrue();
		assertThat(graphContainsIds(graph, "B", "C", "F")).isFalse();
		assertThat(graph.getDependencies("D")).isEmpty();
	}




	@Test
	public void testDependencies() {
		final DependencyGraph graph = buildTestGraph();
		assertThat(graph.getDependencies("A")).isEmpty();
		assertThat(graph.getDependencies("B")).containsExactlyInAnyOrder("A");
		assertThat(graph.getDependencies("C")).containsExactlyInAnyOrder("A");
		assertThat(graph.getDependencies("D")).containsExactlyInAnyOrder("B", "C");
		assertThat(graph.getDependencies("E")).containsExactlyInAnyOrder("B");
		assertThat(graph.getDependencies("F")).isEmpty();
	}




	@Test
	public void testDependenciesIndirect() {
		final DependencyGraph graph = buildTestGraph();
		assertThat(graph.getDependenciesIndirect("A")).isEmpty();
		assertThat(graph.getDependenciesIndirect("B")).containsExactly("A");
		assertThat(graph.getDependenciesIndirect("C")).containsExactly("A");
		assertThat(graph.getDependenciesIndirect("D")).containsExactly("B", "C", "A");
		assertThat(graph.getDependenciesIndirect("E")).containsExactly("B", "A");
		assertThat(graph.getDependenciesIndirect("F")).isEmpty();
	}




	@Test
	public void testDependsOn() {
		final DependencyGraph graph = buildTestGraph();
		assertThat(graph.getDependsOn("A")).containsExactlyInAnyOrder("B", "C");
		assertThat(graph.getDependsOn("B")).containsExactlyInAnyOrder("E", "D");
		assertThat(graph.getDependsOn("C")).containsExactlyInAnyOrder("D");
		assertThat(graph.getDependsOn("D")).isEmpty();
		assertThat(graph.getDependsOn("E")).isEmpty();
		assertThat(graph.getDependsOn("F")).isEmpty();
	}




	@Test
	public void testDependsOnIndirect() {
		final DependencyGraph graph = buildTestGraph();
		assertThat(graph.getDependsOnIndirect("A")).containsExactly("B", "C", "E", "D");
		assertThat(graph.getDependsOnIndirect("B")).containsExactly("E", "D");
		assertThat(graph.getDependsOnIndirect("C")).containsExactly("D");
		assertThat(graph.getDependsOnIndirect("D")).isEmpty();
		assertThat(graph.getDependsOnIndirect("E")).isEmpty();
		assertThat(graph.getDependsOnIndirect("F")).isEmpty();
	}




	@Test
	public void testCyclesWith() {
		final DependencyGraph graph = buildTestGraph();
		graph.addDependency("A", "F");
		Exception exception = null;
		try {
			graph.addDependency("F", "D");
		} catch (Exception e) {
			exception = e;
		}
		assertThat(exception).isNotNull();
		assertThat(exception instanceof ValidateStateException).isTrue();
	}




	@Test
	public void testCyclesBoth() {
		final DependencyGraph graph = buildTestGraph();
		graph.insert("G");
		graph.insert("H");
		graph.insert("I");
		graph.addDependency("A", "F");
		Exception exception = null;
		try {
			graph.addDependency("F", "D");
		} catch (Exception e) {
			exception = e;
		}
		assertThat(exception).isNotNull();
		assertThat(exception instanceof ValidateStateException).isTrue();
		graph.addDependency("G", "H", "I");
	}




	/**
	 * @param graph the {@link DependencyGraph}
	 * @param ids   the ids to check
	 * @return whether the given graph contains all given ids.
	 */
	private static boolean graphContainsIds(final DependencyGraph graph, final String... ids) {
		for (String id : ids) {
			if (!graph.exists(id)) {
				return false;
			}
		}
		return true;
	}




	/**
	 * Builds the default test graph:
	 *
	 * @return the graph
	 */
	private static DependencyGraph buildTestGraph() {
		/*
		digraph G {
			 B -> A;
			 C -> A;
			 D -> B, C;
			 E -> B;
			 F;
		}
		 */
		final DependencyGraph graph = new DependencyGraph();
		graph.insert("A");
		graph.insert("B");
		graph.insert("C");
		graph.insert("D");
		graph.insert("E");
		graph.insert("F");
		graph.addDependency("B", "A");
		graph.addDependency("C", "A");
		graph.addDependency("D", "B", "C");
		graph.addDependency("E", "B");
		return graph;
	}


}
