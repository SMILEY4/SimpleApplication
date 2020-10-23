package de.ruegnerlukas.simpleapplication.simpleui.assets.elements.jfxelements;

import de.ruegnerlukas.simpleapplication.simpleui.core.events.SuiListChangeListener;
import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.TitleProperty;
import de.ruegnerlukas.simpleapplication.simpleui.core.node.SuiNode;
import de.ruegnerlukas.simpleapplication.simpleui.utils.MutableBiConsumer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.Accordion;
import javafx.scene.control.Labeled;
import javafx.scene.control.TitledPane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExtendedAccordion extends Accordion {


	/**
	 * The mutable listener wrapper for expanding sections.
	 */
	private final MutableBiConsumer<String, Boolean> expandedSectionListener = new MutableBiConsumer<>();

	/**
	 * The map of expanded change listener for each title pane
	 */
	private final Map<TitledPane, ChangeListener<Boolean>> expandedTitlePaneListeners = new HashMap<>();

	/**
	 * Whether the expand and collapse of sections should be animated
	 */
	private boolean animate = true;




	/**
	 * The default constructor
	 */
	public ExtendedAccordion() {
		getPanes().addListener(new SuiListChangeListener<>(
				addedElement -> Platform.runLater(() ->
						addedElement.expandedProperty().addListener(createTitlePaneExpandListener(addedElement))),
				removedElement -> removedElement.expandedProperty().removeListener(expandedTitlePaneListeners.remove(removedElement))
		));
	}




	/**
	 * @param listener the listener for expanding and collapsing sections
	 */
	public void setExpandedSectionChangedListener(final BiConsumer<String, Boolean> listener) {
		this.expandedSectionListener.setConsumer(listener);
	}




	/**
	 * Expands the section with the given title. Collapses all if the section was not found or is null
	 *
	 * @param title the title of the section to expand or null
	 */
	public void setExpandedSection(final String title) {
		final Optional<TitledPane> titlePane = getPanes().stream()
				.filter(pane -> pane.getText().equals(title))
				.findFirst();
		expandedSectionListener.runMuted(() -> setExpandedPane(titlePane.orElse(null)));
	}




	/**
	 * Set the sections of this accordion. each child node is one section. The {@link TitleProperty} of the child node defines its title.
	 *
	 * @param childNodes the child nodes
	 */
	public void setSections(final Stream<SuiNode> childNodes) {
		expandedSectionListener.runMuted(() -> {
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
		});
	}




	/**
	 * Removes all sections
	 */
	public void clearSections() {
		expandedSectionListener.runMuted(() -> getPanes().clear());
	}




	/**
	 * Whether the expand and collapse of sections should be animated
	 *
	 * @param animate whether the animation should play
	 */
	public void setAnimated(final boolean animate) {
		this.animate = animate;
		getPanes().forEach(pane -> pane.setAnimated(animate));
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
	 * Creates a new title-pane from the given simpleui-node
	 *
	 * @param node the node
	 * @return the created title pane
	 */
	private TitledPane createTitlePane(final SuiNode node) {
		final String title = node.getPropertyStore().getSafe(TitleProperty.class)
				.map(TitleProperty::getTitle)
				.orElse("");
		TitledPane titledPane = new TitledPane(title, node.getFxNodeStore().get());
		titledPane.setAnimated(animate);
		return titledPane;
	}


}
