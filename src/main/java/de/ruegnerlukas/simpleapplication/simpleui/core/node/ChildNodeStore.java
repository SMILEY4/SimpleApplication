package de.ruegnerlukas.simpleapplication.simpleui.core.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public class ChildNodeStore {


	/**
	 * The child nodes of a node.
	 */
	private final List<SuiBaseNode> children = new ArrayList<>(0);

	/**
	 * The child nodes of a node with their id as a key.
	 */
	private final Map<String, SuiBaseNode> childMap = new HashMap<>();




	/**
	 * @param children the child nodes
	 */
	public ChildNodeStore(final List<SuiBaseNode> children) {
		this.children.addAll(children);
		children.forEach(node -> {
			final String id = node.getPropertyStore().getIdUnsafe();
			if (id != null) {
				childMap.put(id, node);
			}
		});
	}




	/**
	 * @return whether this nodes has child nodes
	 */
	public boolean hasChildren() {
		return !this.children.isEmpty();
	}




	/**
	 * @return the number of child nodes of this node
	 */
	public int count() {
		return this.children.size();
	}




	/**
	 * @param index the index of the child
	 * @return the child at the given index
	 * @throws IndexOutOfBoundsException if the given index is out of range
	 */
	public SuiBaseNode get(final int index) {
		return this.children.get(index);
	}




	/**
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id (if one exists)
	 */
	public Optional<SuiBaseNode> find(final String id) {
		return Optional.ofNullable(childMap.get(id));
	}




	/**
	 * Finds the child node with the given id
	 *
	 * @param id the id of the requested child node
	 * @return the child node with the given id or null
	 */
	public SuiBaseNode findUnsafe(final String id) {
		return childMap.get(id);
	}




	/**
	 * @return an unmodifiable list of children
	 */
	public List<SuiBaseNode> getUnmodifiable() {
		return Collections.unmodifiableList(this.children);
	}




	/**
	 * @return the set ids of all child nodes (that have the id property)
	 */
	public Set<String> getIds() {
		return childMap.keySet();
	}




	/**
	 * @return the children of this node as a stream
	 */
	public Stream<SuiBaseNode> stream() {
		return this.children.stream();
	}


}