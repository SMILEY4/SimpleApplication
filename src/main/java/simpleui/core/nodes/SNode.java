package simpleui.core.nodes;

import javafx.scene.Node;
import lombok.Getter;
import lombok.Setter;
import simpleui.core.properties.Properties;
import simpleui.core.properties.Property;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class SNode {


	@Getter
	@Setter
	private Node linkedFxNode;

	private final Map<Class<? extends Property>, Property> properties = new HashMap<>();




	public SNode(final Property... properties) {
		for (Property prop : properties) {
			this.properties.put(prop.getKey(), prop);
		}
	}




	public <T extends Property> T getProperty(final Class<T> key) {
		return (T) properties.get(key);
	}




	public List<Property> getProperties() {
		return List.copyOf(properties.values());
	}




	public Node createLinkedFxNode() {
		Node fxNode = buildFxNode();
		setLinkedFxNode(fxNode);
		return fxNode;
	}




	protected abstract Node buildFxNode();




	public MutationResult mutate(final SNode other) {
		if (getLinkedFxNode() == null) {
			return MutationResult.REQUIRES_REBUILD;
		}

		if (other instanceof SButton) {
			final Set<Class<? extends Property>> commonProps = Properties.getCommonProperties(this, other);

			final AtomicBoolean requiresRebuild = new AtomicBoolean(false);
			commonProps.forEach(propKey -> {
				final Property propThis = getProperty(propKey);
				final Property propOther = other.getProperty(propKey);
				MutationResult result = MutationResult.MUTATED;
				if (propThis == null) {
					result = mutateAddProperty(propKey, propOther);
				} else if (propOther == null) {
					result = mutateRemoveProperty(propKey, propThis);
				} else {
					if (propThis.compare(propOther)) {
						result = mutateUpdateProperty(propKey, propThis, propOther);
					}
				}
				if (result == MutationResult.REQUIRES_REBUILD) {
					requiresRebuild.set(true);
				}
			});

			if (requiresRebuild.get()) {
				return MutationResult.REQUIRES_REBUILD;
			} else {
				return MutationResult.MUTATED;
			}

		} else {
			return MutationResult.REQUIRES_REBUILD;
		}
	}




	protected abstract MutationResult mutateUpdateProperty(Class<? extends Property> propKey, Property propThis, Property propOther);

	protected abstract MutationResult mutateRemoveProperty(Class<? extends Property> propKey, Property propThis);

	protected abstract MutationResult mutateAddProperty(Class<? extends Property> propKey, Property propOther);

	public abstract void print(int level);

}
