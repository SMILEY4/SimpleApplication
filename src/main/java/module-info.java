open module SimpleApplication {

	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.annotation;
	requires static lombok;
	requires java.desktop;

	exports de.ruegnerlukas.simpleapplication.common.events;
	exports de.ruegnerlukas.simpleapplication.common.events.specializedevents;
	exports de.ruegnerlukas.simpleapplication.common.eventbus;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;
	exports de.ruegnerlukas.simpleapplication.common.resources;
	exports de.ruegnerlukas.simpleapplication.common.tags;
	exports de.ruegnerlukas.simpleapplication.common.utils;
	exports de.ruegnerlukas.simpleapplication.common.validation;

	exports de.ruegnerlukas.simpleapplication.core.application;
	exports de.ruegnerlukas.simpleapplication.core.application.jfx;
	exports de.ruegnerlukas.simpleapplication.core.plugins;

	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.jfxelements;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.elements.suimenu;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.events;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.events;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.properties.misc;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.sources;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.assets.streams.operations;

	exports de.ruegnerlukas.simpleapplication.core.simpleui.core;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.builders;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.events;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.stategies;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.mutation.operations;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.node;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.node.builders;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.profiler;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.registry;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.state;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.core.style;
	exports de.ruegnerlukas.simpleapplication.core.simpleui.utils;


}