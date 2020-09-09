open module SimpleApplication {

	requires transitive javafx.controls;
	requires transitive javafx.fxml;
	requires transitive org.slf4j;
	requires transitive com.fasterxml.jackson.core;
	requires transitive com.fasterxml.jackson.databind;
	requires transitive com.fasterxml.jackson.annotation;
	requires sysout.over.slf4j;
	requires static lombok;

	exports de.ruegnerlukas.simpleapplication.common.callbacks;
	exports de.ruegnerlukas.simpleapplication.common.events;
	exports de.ruegnerlukas.simpleapplication.common.events.specializedevents;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders.factories;
	exports de.ruegnerlukas.simpleapplication.common.instanceproviders.providers;
	exports de.ruegnerlukas.simpleapplication.common.resources;
	exports de.ruegnerlukas.simpleapplication.common.validation;
	exports de.ruegnerlukas.simpleapplication.common.utils;

	exports de.ruegnerlukas.simpleapplication.core.application;
	exports de.ruegnerlukas.simpleapplication.core.events;
	exports de.ruegnerlukas.simpleapplication.core.extensions;
	exports de.ruegnerlukas.simpleapplication.core.plugins;
	exports de.ruegnerlukas.simpleapplication.core.presentation;
	exports de.ruegnerlukas.simpleapplication.core.presentation.module;
	exports de.ruegnerlukas.simpleapplication.core.presentation.views;
	exports de.ruegnerlukas.simpleapplication.core.presentation.style;
	exports de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

	exports de.ruegnerlukas.simpleapplication.simpleui.assets.elements;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.events;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.properties;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.properties.events;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.streams;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.streams.sources;
	exports de.ruegnerlukas.simpleapplication.simpleui.assets.streams.operations;

	exports de.ruegnerlukas.simpleapplication.simpleui.core;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.builders;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.mutation;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.mutation.stategies;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.mutation.operations;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.node;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.registry;
	exports de.ruegnerlukas.simpleapplication.simpleui.core.state;
}