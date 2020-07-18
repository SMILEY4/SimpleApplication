package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.RegistryEntry;
import de.ruegnerlukas.simpleapplication.simpleui.SNode;
import de.ruegnerlukas.simpleapplication.simpleui.SceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.SimpleUIRegistry;
import javafx.scene.Node;

public class MasterFxNodeBuilder {


	/**
	 * The scene context.
	 */
	private final SceneContext context;




	/**
	 * @param context the scene context
	 */
	public MasterFxNodeBuilder(final SceneContext context) {
		this.context = context;
	}




	/**
	 * Builds the fx-node of the given node and attaches it to that node.
	 *
	 * @param node the node to build the fx-node for
	 * @return the created fx-node
	 */
	public Node build(final SNode node) {
		return this.build(node, this.context);
	}




	/**
	 * Builds the fx-node of the given node and links it to this node.
	 *
	 * @param node    the node to build the fx-node for
	 * @param context the scene context
	 * @return the created fx-node
	 */
	public Node build(final SNode node, final SceneContext context) {
		final RegistryEntry registryEntry = SimpleUIRegistry.get().getEntry(node.getNodeType());
		final Node fxNode = registryEntry.getBaseFxNodeBuilder().build(context, node);
		node.getProperties().forEach((propType, property) -> {
			PropFxNodeBuilder propBuilder = registryEntry.getPropFxNodeBuilders().get(propType);
			propBuilder.build(context, node, property, fxNode);
		});
		node.setFxNode(fxNode);
		return fxNode;
	}


}
