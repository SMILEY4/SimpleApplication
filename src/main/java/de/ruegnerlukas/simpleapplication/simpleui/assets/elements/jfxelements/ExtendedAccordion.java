package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.simpleui.assets.SuiListChangeListener;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedAccordion extends Accordion {


	/**
	 * The listener for expanding sections.
	 */
	private BiConsumer<String, Boolean> expandedSectionListener = null;

	/**
	 * The map of expanded change listener for each title pane
	 */
	private final Map<TitledPane, ChangeListener<Boolean>> expandedTitlePaneListeners = new HashMap<>();




	/**
	 * The default constructor
	 */
	public ExtendedAccordion() {
		getPanes().addListener(new SuiListChangeListener<>(
				addedElement -> Platform.runLater(() -> addedElement.expandedProperty().addListener(createTitlePaneExpandListener(addedElement))),
				removedElement -> removedElement.expandedProperty().removeListener(expandedTitlePaneListeners.remove(removedElement))
		));
	}




	/**
	 * Creates and registers a new listener for expanding/collapsing sections
	 *
	 * @param section the section
	 * @return the created and registered listener
	 */
	private ChangeListener<Boolean> createTitlePaneExpandListener(final TitledPane section) {
		ChangeListener<Boolean> listener = (v, p, n) -> onExpandedChanged(section, n);
		expandedTitlePaneListeners.put(section, listener);
		return listener;
	}




	/**
	 * Called when the given title-pane was expanded or collapsed
	 *
	 * @param section  the title-pane in question
	 * @param expanded whether the title-pane was expanded or collapsed
	 */
	private void onExpandedChanged(final TitledPane section, final boolean expanded) {
		final boolean allCollapsed = !expanded && getPanes().stream().filter(TitledPane::isExpanded).map(Labeled::getText).count() == 0;
		if (expandedSectionListener != null) {
			if (allCollapsed) {
				expandedSectionListener.accept(section.getText(), false);
			} else {
				if (expanded) {
					expandedSectionListener.accept(section.getText(), true);
				}
			}
		}
	}




	/**
	 * @param listener the listener for expanding and collapsing sections
	 */
	public void setExpandedSectionChangedListener(final BiConsumer<String, Boolean> listener) {
		this.expandedSectionListener = listener;
	}




	/**
	 * Set the sections of this accordion. each child node is one section. The {@link TitleProperty} of the child node defines its title.
	 *
	 * @param childNodes the child nodes
	 */
	public void setSections(final Stream<SuiNode> childNodes) {
		if (getPanes().isEmpty()) {
			getPanes().setAll(childNodes
					.map(this::createTitlePane)
					.collect(Collectors.toList())
			);
		} else {
			/*
			reuse titled panes to prevent javafx "issue":
			setting new child nodes causes accordion.expandedPane to be set to null for split-second,
			starting an animation that sets the content to invisible, even if we set a new expanded pane in the meantime.
			 */
			final List<TitledPane> prevTitlePanes = new ArrayList<>(this.getPanes());
			final List<TitledPane> titledPanes = childNodes
					.map(child -> {
						TitledPane childTitledPane = null;
						for (int i = 0; i < prevTitlePanes.size(); i++) {
							if (prevTitlePanes.get(i).getContent() == child.getFxNodeStore().get()) {
								childTitledPane = prevTitlePanes.remove(i);
								break;
							}
						}
						if (childTitledPane == null) {
							childTitledPane = createTitlePane(child);
						}
						return childTitledPane;
					})
					.collect(Collectors.toList());
			getPanes().setAll(titledPanes);
		}
	}




	/**
	 * Removes all sections
	 */
	public void clearSections() {
		getPanes().clear();
	}




	/**
	 * Creates a new title-pane from the given simpleui-node
	 *
	 * @param node the node
	 * @return the created title pane
	 */
	private TitledPane createTitlePane(final SuiNode node) {
		final String title = node.getPropertyStore().getSafe(TitleProperty.class)
				.map(TitleProperty::getTitle)
				.orElse("");
		return new TitledPane(title, node.getFxNodeStore().get());
	}


}
