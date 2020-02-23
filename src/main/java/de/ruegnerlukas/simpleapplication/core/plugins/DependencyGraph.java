package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class DependencyGraph {


	/**
	 * All vertices of this graph. The key is the id.
	 */
	private Map<String, Vertex> vertices = new HashMap<>();


	/**
	 * The graph. The key is the vertex and the value is a list of vertices the (key-)vertex depends on.
	 */
	private final Map<Vertex, Set<Vertex>> graph = new HashMap<>();




	/**
	 * Insert a new unloaded id into the graph.
	 *
	 * @param id the id of the node (plugin or component).
	 */
	public void insert(final String id) {
		Validations.PRESENCE.containsNotKey(vertices, id).exception("The vertex with the id {} already exists.", id);
		final Vertex vertex = new Vertex(id);
		vertices.put(vertex.getId(), vertex);
		graph.put(vertex, new HashSet<>());
	}




	/**
	 * Remove the node with the given id from the graph.
	 *
	 * @param id the id of the node to remove
	 */
	public void remove(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		final Vertex toRemove = vertices.get(id);
		vertices.remove(toRemove.getId());
		graph.remove(toRemove);
		graph.values().forEach(edge -> edge.remove(toRemove));
	}




	/**
	 * Adds the dependencies: the vertex with the id 'idSource' depends on the vertices with the ids 'idTargets' (source -> target).
	 *
	 * @param idSource  the id of the source vertex
	 * @param idTargets the ids of the target vertices
	 */
	public void addDependency(final String idSource, final String... idTargets) {
		for (String idTarget : idTargets) {
			addDependency(idSource, idTarget);
		}
	}




	/**
	 * Adds a the dependency: the vertex with the id 'idSource' depends on the vertex with the id 'idTarget' (source -> target).
	 *
	 * @param idSource the id of the source vertex
	 * @param idTarget the id of the target vertex
	 */
	public void addDependency(final String idSource, final String idTarget) {
		Validations.PRESENCE.containsKey(vertices, idSource).exception("The source id does not exists: {}.", idSource);
		Validations.PRESENCE.containsKey(vertices, idTarget).exception("The target id does not exists: {}.", idTarget);
		final Vertex sourceVertex = vertices.get(idSource);
		final Vertex targetVertex = vertices.get(idTarget);
		graph.get(sourceVertex).add(targetVertex);
		if (isCycle(sourceVertex.getId())) {
			Validations.STATE.fail()
					.exception("Cannot add dependency: {} -> {}. Dependency introduces cycle.", idSource, idTarget);
			graph.get(sourceVertex).remove(targetVertex);
		}
	}




	/**
	 * Removes a the dependency: the vertex with the id 'idSource' depends on the vertex with the id 'idTarget' (source -> target).
	 *
	 * @param idSource the id of the source vertex
	 * @param idTarget the id of the target vertex
	 */
	public void removeDependency(final String idSource, final String idTarget) {
		Validations.PRESENCE.containsKey(vertices, idSource).exception("The source id does not exists: {}.", idSource);
		Validations.PRESENCE.containsKey(vertices, idTarget).exception("The target id does not exists: {}.", idTarget);
		final Vertex sourceVertex = vertices.get(idSource);
		final Vertex targetVertex = vertices.get(idTarget);
		graph.get(sourceVertex).remove(targetVertex);
	}




	/**
	 * Sets the given id to the state 'loaded'.
	 *
	 * @param id the id to load.
	 * @return a list of ids that were dependent on the given id and can now be loaded too.
	 */
	public List<String> setLoaded(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return setLoadedState(id, true);
	}




	/**
	 * Sets the given id to the state 'unloaded'.
	 *
	 * @param id the id to unload.
	 * @return a list of ids that were dependent on the given id and should now be unloaded too.
	 */
	public List<String> setUnloaded(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return setLoadedState(id, false);
	}




	/**
	 * Sets the given id to the given stage.
	 *
	 * @param id     the id to load/unload.
	 * @param loaded whether to load or unload the given id
	 * @return a list of ids that were dependent on the given id and can/should now be loaded/unloaded too.
	 */
	private List<String> setLoadedState(final String id, final boolean loaded) {
		final Vertex vertex = vertices.get(id);
		if (vertex.isLoaded() != loaded) {
			vertex.setLoaded(loaded);
			return getDependsOnIndirect(id);
		} else {
			return List.of();
		}
	}




	/**
	 * @param id the id to check
	 * @return whether the given id is loaded
	 */
	public boolean isLoaded(final String id) {
		boolean isLoaded = false;
		if (exists(id)) {
			isLoaded = vertices.get(id).isLoaded();
		}
		return isLoaded;
	}




	/**
	 * Finds all ids the given id depends on and the ids these depend on (id -> ? -> ?).
	 * The order is breadth-first, starting at the given id.
	 *
	 * @param id the id we want to get the dependencies from
	 * @return a list of all ids the given id depends on and the ids these depend on (id -> ? -> ?)
	 */
	public List<String> getDependenciesIndirect(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return getDependenciesIndirectVertices(id)
				.stream()
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * Finds all vertices the given id depends on and the ids these depend on (id -> ? -> ?).
	 * The order is breadth-first, starting at the given id.
	 *
	 * @param id the id we want to get the dependencies from
	 * @return a list of all vertices the given id depends on and the vertices these depend on (id -> ? -> ?)
	 */
	private List<Vertex> getDependenciesIndirectVertices(final String id) {
		final Vertex vertexStart = vertices.get(id);
		final List<Vertex> result = new ArrayList<>();
		if (vertexStart != null && graph.get(vertexStart) != null && !graph.get(vertexStart).isEmpty()) {
			final List<Vertex> queue = new ArrayList<>(); // FIFO-queue
			final Set<Vertex> discovered = new HashSet<>();
			discovered.add(vertexStart);
			queue.add(vertexStart);
			while (!queue.isEmpty()) {
				final Vertex v = queue.remove(0);
				for (Vertex w : getDependenciesVertices(v.getId())) {
					if (!discovered.contains(w)) {
						discovered.add(w);
						queue.add(w);
						result.add(w);
					}
				}
			}
		}
		return result;
	}




	/**
	 * Finds all ids the given id depends on (id -> ?).
	 *
	 * @param id the id we want to get the dependencies from
	 * @return a list of all ids the given id depends on (id -> ?)
	 */
	public List<String> getDependencies(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return getDependenciesVertices(id)
				.stream()
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * Finds all vertices the given id depends on (id -> ?).
	 *
	 * @param id the id we want to get the dependencies from
	 * @return a list of all vertices the given id depends on (id -> ?)
	 */
	private List<Vertex> getDependenciesVertices(final String id) {
		final List<Vertex> result = new ArrayList<>();
		final Vertex vertex = vertices.get(id);
		if (vertex != null && graph.containsKey(vertex)) {
			result.addAll(graph.get(vertex));
		}
		return result;
	}




	/**
	 * Finds all ids that depend on the given id and the ids that depend on those (? -> ? -> id).
	 * The order is breadth-first, starting at the given id.
	 *
	 * @param id the id
	 * @return a list of all ids that depend on the given id and the ids that depend on those (? -> ? -> id)
	 */
	public List<String> getDependsOnIndirect(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return getDependsOnIndirectVertices(id)
				.stream()
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * Finds all vertices that depend on the given id and the vertices that depend on those (? -> ? -> id).
	 * The order is breadth-first, starting at the given id.
	 *
	 * @param id the id
	 * @return a list of all vertices that depend on the given id and the vertices that depend on those (? -> ? -> id)
	 */
	private List<Vertex> getDependsOnIndirectVertices(final String id) {
		final Vertex vertexStart = vertices.get(id);
		final List<Vertex> result = new ArrayList<>();
		if (vertexStart != null && graph.get(vertexStart) != null) {
			final List<Vertex> queue = new ArrayList<>(); // FIFO-queue
			final Set<Vertex> discovered = new HashSet<>();
			discovered.add(vertexStart);
			queue.add(vertexStart);
			while (!queue.isEmpty()) {
				final Vertex v = queue.remove(0);
				for (Vertex w : getDependsOnVertices(v.getId())) {
					if (!discovered.contains(w)) {
						discovered.add(w);
						queue.add(w);
						result.add(w);
					}
				}
			}
		}
		return result;
	}




	/**
	 * @param id the id
	 * @return a list of all ids that depend on the given id (? -> id)
	 */
	public List<String> getDependsOn(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return getDependsOnVertices(id)
				.stream()
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * @param id the id
	 * @return a list of all vertices that depend on the given id (? -> id)
	 */
	private List<Vertex> getDependsOnVertices(final String id) {
		final Vertex vertex = vertices.get(id);
		final Set<Vertex> result = new HashSet<>();
		graph.forEach((k, v) -> {
			if (v.contains(vertex)) {
				result.add(k);
			}
		});
		return new ArrayList<>(result);
	}




	/**
	 * Checks if the (sub-)graph contains a cycle, starting at the given id.
	 *
	 * @param id the starting point for the check
	 * @return whether the (sub-)graph contains a cycle, starting at the given id.
	 */
	private boolean isCycle(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		return isCycle(id, new HashSet<>());
	}




	/**
	 * Checks if the (sub-)graph contains a cycle, starting at the given id.
	 *
	 * @param id      the starting point for the check
	 * @param visited the set of already visited ids
	 * @return whether the (sub-)graph contains a cycle, starting at the given id.
	 */
	private boolean isCycle(final String id, final Set<String> visited) {
		if (visited.contains(id)) {
			return true;
		}
		visited.add(id);
		final Vertex vertex = vertices.get(id);
		boolean isCycle = false;
		for (Vertex depencency : graph.get(vertex)) {
			if (isCycle(depencency.getId(), new HashSet<>(visited))) {
				isCycle = true;
				break;
			}
		}
		return isCycle;
	}




	/**
	 * @return a list of all ids that depend on no other id
	 */
	public List<String> getRoots() {
		return getRootVertices()
				.stream()
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * @return a list of all vertices that depend on no other vertex
	 */
	private List<Vertex> getRootVertices() {
		return graph.keySet()
				.stream()
				.filter(vertex -> graph.get(vertex).isEmpty())
				.collect(Collectors.toList());
	}




	/**
	 * @return a list of all ids stored in this graph.
	 */
	public List<String> getIds() {
		return new ArrayList<>(vertices.keySet());
	}




	/**
	 * Checks if the given id exists in this graph.
	 *
	 * @param id the id to check
	 * @return whether the id exists
	 */
	public boolean exists(final String id) {
		return vertices.containsKey(id);
	}




	@Getter
	@EqualsAndHashCode (exclude = "isLoaded")
	private static class Vertex {


		/**
		 * The id of this vertex.
		 */
		private final String id;


		/**
		 * Whether the id of this vertex is currently loaded.
		 */
		@Setter
		private boolean isLoaded = false;




		/**
		 * @param id the id of this vertex
		 */
		Vertex(final String id) {
			this.id = id;
		}

	}


}
