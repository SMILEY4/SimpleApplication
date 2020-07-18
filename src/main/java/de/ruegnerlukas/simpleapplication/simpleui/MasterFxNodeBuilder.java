package de.ruegnerlukas.simpleapplication.simpleui;


import de.ruegnerlukas.simpleapplication.simpleui.builders.PropFxNodeBuilder;
import javafx.scene.Node;

public class MasterFxNodeBuilder {


	private final SceneContext context;




	public MasterFxNodeBuilder(SceneContext context) {
		this.context = context;
	}




	public Node build(final SNode node) {
		return this.build(node, this.context);
	}




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
