package de.ruegnerlukas.simpleapplication.core.application.plugins;

import de.ruegnerlukas.simpleapplication.core.plugins.DependencyGraph;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DependencyGraphTest {


	@Test
	public void testGraph() {

		final DependencyGraph graph = new DependencyGraph();

		/*
		digraph G {
			B -> A;
			C -> A;
			D -> B, C;
			E -> B;
			F;
		}
		 */

		graph.insert("A", List.of());
		graph.insert("B", List.of("A"));
		graph.insert("C", List.of("A"));
		graph.insert("D", List.of("B", "C"));
		graph.insert("E", List.of("B"));

		assertThat(graph.exists("F")).isFalse();
		graph.insert("F", List.of());
		assertThat(graph.exists("F")).isTrue();

		assertThat(graph.getIds()).containsExactlyInAnyOrder("A", "B", "C", "D", "E", "F");
		assertThat(graph.getRoots()).containsExactlyInAnyOrder("A", "F");

		assertThat(graph.getDependencies("A")).isEmpty();
		assertThat(graph.getDependencies("B")).containsExactlyInAnyOrder("A");
		assertThat(graph.getDependencies("C")).containsExactlyInAnyOrder("A");
		assertThat(graph.getDependencies("D")).containsExactlyInAnyOrder("B", "C");
		assertThat(graph.getDependencies("E")).containsExactlyInAnyOrder("B");
		assertThat(graph.getDependencies("F")).isEmpty();

		assertThat(graph.getDependsOn("A")).containsExactlyInAnyOrder("B", "C");
		assertThat(graph.getDependsOn("B")).containsExactlyInAnyOrder("E", "D");
		assertThat(graph.getDependsOn("C")).containsExactlyInAnyOrder("D");
		assertThat(graph.getDependsOn("D")).isEmpty();
		assertThat(graph.getDependsOn("E")).isEmpty();
		assertThat(graph.getDependsOn("F")).isEmpty();

		assertThat(graph.getDependsOnIndirect("A")).containsExactlyInAnyOrder("B", "C", "D", "E");
		assertThat(graph.getDependsOnIndirect("B")).containsExactlyInAnyOrder("E", "D");
		assertThat(graph.getDependsOnIndirect("C")).containsExactlyInAnyOrder("D");
		assertThat(graph.getDependsOnIndirect("D")).isEmpty();
		assertThat(graph.getDependsOnIndirect("E")).isEmpty();
		assertThat(graph.getDependsOnIndirect("F")).isEmpty();

		final List<String> removedF = graph.remove("F");
		assertThat(removedF).isEmpty();
		assertThat(graphContainsIds(graph, "A", "B", "C", "D", "E")).isTrue();
		assertThat(graphContainsIds(graph, "F")).isFalse();

		final List<String> removedB = graph.remove("B");
		assertThat(removedB).containsExactlyInAnyOrder("E", "D");
		assertThat(graphContainsIds(graph, "A", "C")).isTrue();
		assertThat(graphContainsIds(graph, "B", "D", "E", "F")).isFalse();

		final List<String> removedC = graph.remove("C");
		assertThat(removedC).isEmpty();
		assertThat(graphContainsIds(graph, "A")).isTrue();
		assertThat(graphContainsIds(graph, "B", "C", "D", "E", "F")).isFalse();

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


}
