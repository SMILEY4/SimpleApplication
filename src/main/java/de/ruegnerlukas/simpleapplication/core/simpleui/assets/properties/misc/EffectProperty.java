package de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;


import de.ruegnerlukas.simpleapplication.common.utils.Pair;
import de.ruegnerlukas.simpleapplication.common.utils.Triplet;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.builders.PropFxNodeUpdatingBuilder;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.MutationResult;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.SuiProperty;
import de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders.FactoryExtension;
import javafx.scene.Node;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.function.BiFunction;

@Slf4j
public class EffectProperty extends SuiProperty {


	/**
	 * The comparator function for this property type.
	 */
	private static final BiFunction<EffectProperty, EffectProperty, Boolean> COMPARATOR =
			(a, b) -> Arrays.equals(a.getEffects(), b.getEffects());

	/**
	 * The effect or null
	 */
	@Getter
	private final Effect[] effects;




	/**
	 * @param effects the effects.
	 */
	public EffectProperty(final Effect... effects) {
		super(EffectProperty.class, COMPARATOR);
		this.effects = effects;
	}




	/**
	 * @param threshold the threshold value controls the minimum luminosity value of the pixels that will be made to glow. Between 0 and 1.
	 * @return the created effect
	 */
	public static Effect bloom(final double threshold) {
		final Bloom effect = new Bloom();
		effect.setThreshold(Math.max(0.0, Math.min(threshold, 1.0)));
		return effect;
	}




	/**
	 * @param width  the horizontal size of the blur effect. Between 0 and 255.
	 * @param height the vertical size of the blur effect. Between 0 and 255.
	 * @return the created effect
	 */
	public static Effect boxBlur(final Number width, final Number height) {
		return boxBlur(width, height, 1);
	}




	/**
	 * @param width      the horizontal size of the blur effect. Between 0 and 255.
	 * @param height     the vertical size of the blur effect. Between 0 and 255.
	 * @param iterations number of iterations. More iterations improve the quality. Between 0 and 3
	 * @return the created effect
	 */
	public static Effect boxBlur(final Number width, final Number height, final int iterations) {
		Validations.INPUT.notNull(width).exception("The blur width may not be null");
		Validations.INPUT.notNull(width).exception("The blur height may not be null");
		final BoxBlur effect = new BoxBlur();
		effect.setWidth(width.doubleValue());
		effect.setHeight(height.doubleValue());
		effect.setIterations(iterations);
		return effect;
	}




	/**
	 * @param angle  the angle of the blur
	 * @param radius the size of the blur
	 * @return the created effect
	 */
	public static Effect motionBlur(final double angle, final double radius) {
		return new MotionBlur(angle, radius);
	}




	/**
	 * @param hue        the hue adjustment value. Between -1 and +1.
	 * @param saturation the saturation adjustment value. Between -1 and +1.
	 * @param brightness the brightness adjustment value. Between -1 and +1.
	 * @param contrast   the contrast adjustment value. Between -1 and +1.
	 * @return the created effect
	 */
	public static Effect colorAdjust(final double hue, final double saturation, final double brightness, final double contrast) {
		return new ColorAdjust(hue, saturation, brightness, contrast);
	}




	/**
	 * @param radius the size of the drop shadow. (Between 0 and 127)
	 * @param color  the color of the shadow
	 * @return the created effect
	 */
	public static Effect dropShadow(final double radius, final Color color) {
		return new DropShadow(radius, color);
	}




	/**
	 * @param radius the size of the drop shadow. (Between 0 and 127)
	 * @param offX   the horizontal offset
	 * @param offY   the vertical offset
	 * @param color  the color of the shadow
	 * @return the created effect
	 */
	public static Effect dropShadow(final double radius, final double offX, final double offY, final Color color) {
		return new DropShadow(radius, offX, offY, color);
	}




	/**
	 * @param radius   the size of the drop shadow. (Between 0 and 127)
	 * @param offX     the horizontal offset
	 * @param offY     the vertical offset
	 * @param spread   the portion of the radius where the contribution of the source material will be 100%
	 * @param color    the color of the shadow
	 * @param blurType the used blur algorithm
	 * @return the created effect
	 */
	public static Effect dropShadow(final double radius,
									final double offX,
									final double offY,
									final double spread,
									final Color color,
									final BlurType blurType) {
		return new DropShadow(blurType, color, radius, spread, offX, offY);
	}




	/**
	 * @param level the intensity of the glow effect
	 * @return the created effect
	 */
	public static Effect glow(final double level) {
		return new Glow(level);
	}




	/**
	 * @param radius the size of the inner shadow. (Between 0 and 127)
	 * @param color  the color of the shadow
	 * @return the created effect
	 */
	public static Effect innerShadow(final double radius, final Color color) {
		return new InnerShadow(radius, color);
	}




	/**
	 * @param radius the size of the inner shadow. (Between 0 and 127)
	 * @param offX   the horizontal offset
	 * @param offY   the vertical offset
	 * @param color  the color of the shadow
	 * @return the created effect
	 */
	public static Effect innerShadow(final double radius, final double offX, final double offY, final Color color) {
		return new InnerShadow(radius, offX, offY, color);
	}




	/**
	 * @param radius   the size of the inner shadow. (Between 0 and 127)
	 * @param offX     the horizontal offset
	 * @param offY     the vertical offset
	 * @param choke    the portion of the radius where the contribution of the source material will be 100%
	 * @param color    the color of the shadow
	 * @param blurType the used blur algorithm
	 * @return the created effect
	 */
	public static Effect innerShadow(final double radius,
									 final double offX,
									 final double offY,
									 final double choke,
									 final Color color,
									 final BlurType blurType) {
		return new InnerShadow(blurType, color, radius, choke, offX, offY);
	}




	/**
	 * Simulates lighting from a distant light source
	 *
	 * @param azimuth   the azimuth of the light
	 * @param elevation the elevation of the light
	 * @param color     the color of the light
	 * @return the created effect
	 */
	public static Effect lightingDistant(final double azimuth, final double elevation, final Color color) {
		return new Lighting(new Light.Distant(azimuth, elevation, color));
	}




	/**
	 * Simulates lighting from a point light source
	 *
	 * @param x     the x position of the light
	 * @param y     the y position of the light
	 * @param z     the z position of the light
	 * @param color the color of the light
	 * @return the created effect
	 */
	public static Effect lightingPoint(final double x, final double y, final double z, final Color color) {
		return new Lighting(new Light.Point(x, y, z, color));
	}




	/**
	 * Simulates lighting from a spot light source
	 *
	 * @param x                the x position of the light
	 * @param y                the y position of the light
	 * @param z                the z position of the light
	 * @param specularExponent the value controlling the focus of the light source
	 * @param color            the color of the light
	 * @return the created effect
	 */
	public static Effect lightingSpot(final double x, final double y, final double z, final double specularExponent, final Color color) {
		return new Lighting(new Light.Spot(x, y, z, specularExponent, color));
	}




	/**
	 * Simulates lighting from a spot light source
	 *
	 * @param position         the x, y, and z coordinates of the light
	 * @param direction        the direction of the light (x,y,z)
	 * @param specularExponent the value controlling the focus of the light source
	 * @param color            the color of the light
	 * @return the created effect
	 */
	public static Effect lightingSpot(final Triplet<Double, Double, Double> position,
									  final Triplet<Double, Double, Double> direction,
									  final double specularExponent,
									  final Color color) {
		final Light.Spot light = new Light.Spot(position.getLeft(), position.getMiddle(), position.getRight(), specularExponent, color);
		light.setPointsAtX(direction.getLeft());
		light.setPointsAtX(direction.getMiddle());
		light.setPointsAtX(direction.getRight());
		return new Lighting(light);
	}




	/**
	 * @param ul the x and y coordinates of upper left corner
	 * @param ur the x and y coordinates of upper right corner
	 * @param lr the x and y coordinates of lower right corner
	 * @param ll the x and y coordinates of lower left corner
	 * @return the created effect
	 */
	public static Effect perspectiveTransform(final Pair<Double, Double> ul,
											  final Pair<Double, Double> ur,
											  final Pair<Double, Double> ll,
											  final Pair<Double, Double> lr) {
		return new PerspectiveTransform(
				ul.getLeft(), ul.getRight(),
				ur.getLeft(), ur.getRight(),
				ll.getLeft(), ll.getRight(),
				lr.getLeft(), lr.getRight());
	}




	/**
	 * @param topOffset     the distance between the bottom of the input and the top of the reflection
	 * @param fraction      the fraction of the input that is visible in the reflection
	 * @param topOpacity    the opacity of the reflection at its top extreme
	 * @param bottomOpacity the opacity of the reflection at its bottom extreme
	 * @return the created effect
	 */
	public static Effect reflection(final double topOffset, final double fraction, final double topOpacity, final double bottomOpacity) {
		return new Reflection(topOffset, fraction, topOpacity, bottomOpacity);
	}




	public interface PropertyBuilderExtension<T extends FactoryExtension> extends FactoryExtension {


		/**
		 * @param effects the effects. See the helper methods in {@link EffectProperty} or all effects in {@link javafx.scene.effect}
		 */
		@SuppressWarnings ("unchecked")
		default T effect(final Effect... effects) {
			if (effects != null) {
				Validations.INPUT.containsNoNull(effects).exception("The effects may not contain null elements.");
			}
			getBuilderProperties().add(new EffectProperty(effects));
			return (T) this;
		}

	}






	public static class UpdatingBuilder implements PropFxNodeUpdatingBuilder<EffectProperty, Node> {


		@Override
		public void build(final SuiNode node, final EffectProperty property, final Node fxNode) {
			setEffect(fxNode, property.getEffects());
		}




		@Override
		public MutationResult update(final EffectProperty property, final SuiNode node, final Node fxNode) {
			setEffect(fxNode, property.getEffects());
			return MutationResult.MUTATED;
		}




		@Override
		public MutationResult remove(final EffectProperty property, final SuiNode node, final Node fxNode) {
			fxNode.setEffect(null);
			return MutationResult.MUTATED;
		}




		/**
		 * Sets the effect of the given node
		 *
		 * @param node    the javafx node
		 * @param effects the effect-chain. The order matters here.
		 */
		private void setEffect(final Node node, final Effect[] effects) {
			if (effects == null || effects.length == 0) {
				node.setEffect(null);

			} else {
				for (int i = 0; i < effects.length - 1; i++) {
					if (!linkEffects(effects[i], effects[i + 1])) {
						log.warn("Could not link effects: {}, {}", effects[i], effects[i + 1]);
					}
				}
				node.setEffect(effects[0]);
			}

		}




		/**
		 * Links the two effects together. The "next" effect will be the input of the "current" effect.
		 *
		 * @param current the current effect
		 * @param next    the next/input effect
		 * @return whether it was successful
		 */
		private boolean linkEffects(final Effect current, final Effect next) {
			if (current instanceof Bloom) {
				((Bloom) current).setInput(next);
			} else if (current instanceof BoxBlur) {
				((BoxBlur) current).setInput(next);
			} else if (current instanceof ColorAdjust) {
				((ColorAdjust) current).setInput(next);
			} else {
				return false;
			}
			return true;
		}

	}


}
