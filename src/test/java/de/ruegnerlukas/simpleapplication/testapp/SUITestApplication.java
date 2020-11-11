//package de.ruegnerlukas.simpleapplication.testapp;
//
//import de.ruegnerlukas.simpleapplication.common.events.Channel;
//import de.ruegnerlukas.simpleapplication.common.instanceproviders.factories.StringFactory;
//import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
//import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.StringProvider;
//import de.ruegnerlukas.simpleapplication.common.resources.Resource;
//import de.ruegnerlukas.simpleapplication.common.utils.Pair;
//import de.ruegnerlukas.simpleapplication.core.application.Application;
//import de.ruegnerlukas.simpleapplication.core.application.ApplicationConfiguration;
//import de.ruegnerlukas.simpleapplication.core.application.EventPresentationInitialized;
//import de.ruegnerlukas.simpleapplication.core.events.EventService;
//import de.ruegnerlukas.simpleapplication.core.plugins.Plugin;
//import de.ruegnerlukas.simpleapplication.core.plugins.PluginInformation;
//import de.ruegnerlukas.simpleapplication.core.presentation.simpleui.SUIWindowHandleDataFactory;
//import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
//import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
//import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
//import de.ruegnerlukas.simpleapplication.simpleui.assets.properties.misc.InjectionIndexMarker;
//import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
//import de.ruegnerlukas.simpleapplication.simpleui.core.node.NodeFactory;
//import de.ruegnerlukas.simpleapplication.simpleui.core.registry.SuiRegistry;
//import de.ruegnerlukas.simpleapplication.simpleui.core.state.SuiState;
//import javafx.geometry.Dimension2D;
//import javafx.geometry.Pos;
//import javafx.scene.layout.Priority;
//import javafx.stage.Stage;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.extern.slf4j.Slf4j;
//
//import java.awt.Toolkit;
//import java.awt.datatransfer.Clipboard;
//import java.awt.datatransfer.DataFlavor;
//import java.awt.datatransfer.StringSelection;
//import java.awt.datatransfer.UnsupportedFlavorException;
//import java.io.IOException;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
//import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.button;
//import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.hBox;
//import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.label;
//import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.scrollPane;
//import static de.ruegnerlukas.simpleapplication.simpleui.assets.SuiElements.vBox;
//
//@Slf4j
//public class SUITestApplication {
//
//
//	public static final TestUIState testUIState = new TestUIState();
//
//
//
//
//	public static void main(String[] args) {
//		SuiRegistry.initialize();
//
//		final ApplicationConfiguration configuration = new ApplicationConfiguration();
//		configuration.getPlugins().add(new UIPlugin());
//		configuration.getProviderFactories().add(new StringFactory("application_name", "SUI Test App"));
//		configuration.setShowViewAtStartup(false);
//		new Application(configuration).run();
//	}
//
//
//
//
//	@Getter
//	@Setter
//	private static class TestUIState extends SuiState {
//
//
//		private List<Pair<String, Long>> content = new ArrayList<>();
//
//	}
//
//
//
//
//
//
//	/**
//	 * responsible for displaying the ui
//	 */
//	@Slf4j
//	static class UIPlugin extends Plugin {
//
//
//		public UIPlugin() {
//			super(new PluginInformation("plugin.ui", "UI Plugin", "0.1", false));
//		}
//
//
//
//
//		@Override
//		public void onLoad() {
//			final EventService eventService = new Provider<>(EventService.class).get();
//			eventService.subscribe(Channel.type(EventPresentationInitialized.class), e -> createViews());
//		}
//
//
//
//
//		private void createViews() {
//			log.info("{} creating views.", this.getId());
//
//			log.info("Main State: {}", testUIState);
//
//			final View viewParent = View.builder()
//					.id("sui.test.view")
//					.size(new Dimension2D(600, 500))
//					.title(new StringProvider("application_name").get())
//					.icon(Resource.internal("testResources/icon.png"))
//					.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(testUIState, TestUIState.class, SUITestApplication::createUI)))
//					.build();
//
//			SuiRegistry.get().inject("ij-point.toolbar",
//					button()
//							.id("btn.enable-auto")
//							.sizeMax(150, 30)
//							.hGrow(Priority.ALWAYS)
//							.textContent("Enable Autosave")
//							.eventAction(".", e -> System.out.println("Enable Autosave")));
//
//			SuiRegistry.get().inject("ij-point.toolbar",
//					button()
//							.id("btn.always-on-top")
//							.sizeMax(150, 30)
//							.hGrow(Priority.ALWAYS)
//							.textContent("Always On Top")
//							.eventAction(".", e -> System.out.println("Always On Top")));
//
//
//			final ViewService viewService = new Provider<>(ViewService.class).get();
//			viewService.registerView(viewParent);
//			viewService.showView(viewParent.getId());
//
//		}
//
//
//
//
//		@Override
//		public void onUnload() {
//		}
//
//	}
//
//
//
//
//	private static Stage forceExtractPrimaryStage() throws Exception {
//		final ViewService viewService = new Provider<>(ViewService.class).get();
//		WindowHandle primaryWindowHandle = viewService.getPrimaryWindowHandle();
//		Field field = WindowHandle.class.getDeclaredField("stage");
//		field.setAccessible(true);
//		return (Stage) field.get(primaryWindowHandle);
//	}
//
//
//
//
//	private static NodeFactory createUI(final TestUIState state) {
//		return vBox()
//				.id("root")
//				.fitToWidth()
//				.items(
//						hBox()
//								.id("toolbar")
//								.spacing(5)
//								.sizeMin(0, 40)
//								.sizeMax(100000, 40)
//								.alignment(Pos.CENTER)
//								.itemsInjectable(
//										"ij-point.toolbar",
//										InjectionIndexMarker.injectLast(),
//										button()
//												.id("btn.save")
//												.sizeMax(150, 30)
//												.hGrow(Priority.ALWAYS)
//												.textContent("Save Clipboard")
//												.eventAction(".", e -> saveClipboard(state))
//										),
//						scrollPane()
//								.id("content.scroll")
//								.sizePreferred(1000000, 1000000)
//								.fitToWidth()
//								.fitToHeight()
//								.item(
//										vBox()
//												.id("content.box")
//												.items(state.getContent().stream().map(e -> buildContentItem(state, e)))
//								)
//				);
//	}
//
//
//
//
//	private static NodeFactory buildContentItem(final TestUIState state, final Pair<String, Long> content) {
//		return hBox()
//				.id("item-" + content.getLeft().hashCode() + "-" + content.getRight())
//				.sizeMax(1000000, 50)
//				.sizeMin(0, 50)
//				.alignment(Pos.CENTER_LEFT)
//				.spacing(5)
//				.items(
//						label()
//								.id("content.label")
//								.sizePreferred(1000000, 45)
//								.textContent(content.getLeft()),
//						button()
//								.id("content.copy")
//								.textContent("C")
//								.sizeMin(35, 35)
//								.sizeMax(35, 35)
//								.eventAction(".", e -> copyToClipboard(content.getLeft())),
//						button()
//								.id("content.remove")
//								.textContent("X")
//								.sizeMin(35, 35)
//								.sizeMax(35, 35)
//								.eventAction(".", e -> removeClipboardItem(state, content))
//				);
//	}
//
//
//
//
//	private static void saveClipboard(final TestUIState state) {
//		state.update(TestUIState.class, s -> {
//			try {
//				final String data = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
//				s.getContent().add(0, Pair.of(data, System.currentTimeMillis()));
//				log.info("Clipboard saved: " + data.substring(0, Math.min(data.length(), 20)));
//			} catch (UnsupportedFlavorException | IOException e) {
//				log.warn("Error saving clipboard: {}", e.getMessage());
//			}
//		});
//	}
//
//
//
//
//	private static void copyToClipboard(final String strContent) {
//		final StringSelection selection = new StringSelection(strContent);
//		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//		clipboard.setContents(selection, selection);
//		log.info("Content copied: " + strContent.substring(0, Math.min(strContent.length(), 20)));
//	}
//
//
//
//
//	private static void removeClipboardItem(final TestUIState state, final Pair<String, Long> content) {
//		state.update(TestUIState.class, s -> {
//			s.getContent().removeIf(
//					e -> e.getLeft().equals(content.getLeft()) && e.getRight().longValue() == content.getRight().longValue());
//			log.info("Content removed: " + content.getLeft().substring(0, Math.min(content.getLeft().length(), 20)));
//		});
//	}
//
//
//}
