package de.ruegnerlukas.simpleapplication.simpleui.builders;


import de.ruegnerlukas.simpleapplication.simpleui.elements.basenode.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.SuiSceneContext;
import de.ruegnerlukas.simpleapplication.simpleui.registry.RegistryEntry;
import de.ruegnerlukas.simpleapplication.simpleui.registry.SuiRegistry;
import javafx.scene.Node;

public class MasterFxNodeBuilder {


	/**
	 * The scene context.
	 */
	private final SuiSceneContext context;




	/**
	 * @param context the scene context
	 */
	public MasterFxNodeBuilder(final SuiSceneContext context) {
		this.context = context;
	}




	/**
	 * Builds the fx-node of the given node and attaches it to that node.
	 *
	 * @param node the node to build the fx-node for
	 * @return the created fx-node
	 */
	public Node build(final SuiNode node) {
		return this.build(node, this.context.getMasterNodeHandlers());
	}




	/**
	 * Builds the fx-node of the given node and links it to this node.
	 *
	 * @param node         the node to build the fx-node for
	 * @param nodeHandlers the primary node handlers
	 * @return the created fx-node
	 */
	public Node build(final SuiNode node, final MasterNodeHandlers nodeHandlers) {
		final RegistryEntry registryEntry = SuiRegistry.get().getEntry(node.getNodeType());
		final Node fxNode = registryEntry.getBaseFxNodeBuilder().build(nodeHandlers, node);
		node.getProperties().forEach((propType, property) -> {
			PropFxNodeBuilder propBuilder = registryEntry.getPropFxNodeBuilders().get(propType);
			propBuilder.build(nodeHandlers, node, property, fxNode);
		});
		node.setFxNode(fxNode);
		return fxNode;
	}


}
