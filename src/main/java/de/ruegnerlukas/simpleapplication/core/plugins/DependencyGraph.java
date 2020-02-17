package de.ruegnerlukas.simpleapplication.core.plugins;

import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class DependencyGraph {


	/**
	 * All vertices of this graph.
	 */
	private Map<String, Vertex> vertices = new HashMap<>();




	/**
	 * Insert a new node with the given id and dependencies into the graph.
	 *
	 * @param id           the id of the node (plugin or component).
	 * @param dependencies the dependencies
	 */
	public void insert(final String id, final List<String> dependencies) {
		Validations.PRESENCE.containsNotKey(vertices, id).exception("The vertex with the id {} already exists.", id);
		final Vertex vertex = new Vertex(id);
		vertices.put(vertex.getId(), vertex);
		for (String dependency : dependencies) {
			addDependency(id, dependency);
		}
	}




	/**
	 * Adds a the dependency: the vertex with the id 'idSource' depends on the vertex with the id 'idTarget'.
	 *
	 * @param idSource the id of the source vertex
	 * @param idTarget the id of the target vertex
	 */
	private void addDependency(final String idSource, final String idTarget) {
		Validations.PRESENCE.containsKey(vertices, idSource).exception("The source id does not exists: {}.", idSource);
		Validations.PRESENCE.containsKey(vertices, idTarget).exception("The target id does not exists: {}.", idTarget);
		final Vertex sourceVertex = vertices.get(idSource);
		final Vertex targetVertex = vertices.get(idTarget);
		sourceVertex.getEdgesTo().add(targetVertex.getId());
	}




	/**
	 * Remove the node with the given id from the graph.
	 * All nodes dependent on this node (including nodes dependent in those) will be removed too.
	 *
	 * @param id the id of the node to remove
	 * @return the list of removed ids (excluding the given id)
	 */
	public List<String> remove(final String id) {
		Validations.PRESENCE.containsKey(vertices, id).exception("The id does not exists: {}.", id);
		final Vertex removedVertex = vertices.get(id);
		final List<String> removedVertices = getDependsOnIndirect(id);
		removedVertices.forEach(vertex -> vertices.remove(vertex));
		vertices.remove(removedVertex.getId());
		vertices.values().forEach(vertex -> vertex.getEdgesTo().remove(removedVertex.getId()));
		return removedVertices;
	}




	/**
	 * @param id the id we want to get the dependencies from
	 * @return a list of all ids the given id depends on (id -> ?)
	 */
	public List<String> getDependencies(final String id) {
		final List<String> ids = new ArrayList<>();
		if (vertices.containsKey(id)) {
			ids.addAll(vertices.get(id).getEdgesTo());
		}
		return ids;
	}




	/**
	 * @param id the id
	 * @return a list of all ids that depend on the given id (? -> id)
	 */
	public List<String> getDependsOn(final String id) {
		return vertices.values()
				.stream()
				.filter(vertex -> vertex.getEdgesTo().contains(id))
				.map(Vertex::getId)
				.collect(Collectors.toList());
	}




	/**
	 * @param id the id
	 * @return a list of all ids that depend on the given id and ids that depend on those (? -> ? -> id)
	 */
	public List<String> getDependsOnIndirect(final String id) {
		final Set<String> dependencies = new HashSet<>(getDependsOn(id));
		final List<String> addedLast = new ArrayList<>(dependencies);
		while (!addedLast.isEmpty()) {
			List<String> toCheck = new ArrayList<>(addedLast);
			addedLast.clear();
			for (String dep : toCheck) {
				final List<String> dependOn = getDependsOnIndirect(dep);
				dependencies.addAll(dependOn);
				addedLast.addAll(dependOn);
			}
		}
		return new ArrayList<>(dependencies);
	}




	/**
	 * @return a list of all ids that depend on no other id
	 */
	public List<String> getRoots() {
		return vertices.values()
				.stream()
				.filter(vertex -> vertex.getEdgesTo().isEmpty())
				.map(Vertex::getId)
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
	private static class Vertex {


		/**
		 * The id of this vertex.
		 */
		private final String id;

		/**
		 * The ids of other vertices this vertex points to.
		 */
		private final Set<String> edgesTo = new HashSet<>();




		/**
		 * @param id the id of this vertex
		 */
		Vertex(final String id) {
			this.id = id;
		}

	}


}
