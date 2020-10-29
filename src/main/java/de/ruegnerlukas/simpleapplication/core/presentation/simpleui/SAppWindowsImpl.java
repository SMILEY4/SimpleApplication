package de.ruegnerlukas.simpleapplication.core.presentation.simpleui;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.views.PopupConfiguration;
import de.ruegnerlukas.simpleapplication.core.presentation.views.View;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.core.SuiSceneController;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.SuiWindows;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowConfig;
import javafx.stage.Window;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class SAppWindowsImpl implements SuiWindows {


	/**
	 * The provider for the view service
	 */
	private final Provider<ViewService> viewServiceProvider = new Provider<>(ViewService.class);

	/**
	 * The used window handles
	 */
	private final Map<String, WindowHandle> windowHandles = new HashMap<>();




	@Override
	public void openWindow(final WindowConfig windowConfig) {
		if (!windowHandles.containsKey(windowConfig.getWindowId())) {

			Validations.INPUT.typeOf(windowConfig, SAppWindowConfig.class)
					.exception("The window config must be of type {}.", SAppWindowConfig.class);

			final SAppWindowConfig config = (SAppWindowConfig) windowConfig;
			final ViewService viewService = viewServiceProvider.get();

			if (viewService.findView(config.getViewId()).isEmpty()) {
				registerView(viewService, config);
			}

			final WindowHandle handle = viewService.popupView(config.getViewId(), buildPopupConfig(config));
			if (config.getOnClose() != null) {
				Window window = handle.getCurrentRootNode().getScene().getWindow();
				window.setOnCloseRequest(e -> config.getOnClose().run());
			}
			windowHandles.put(config.getWindowId(), handle);

		} else {
			log.warn("Can not open window {}. Window with same id is already open.", windowConfig.getWindowId());
		}
	}




	/**
	 * Creates a new view and registers it at the given view service
	 *
	 * @param viewService the view service
	 * @param config      the config providing data for the view
	 */
	private void registerView(final ViewService viewService, final SAppWindowConfig config) {
		final View view = View.builder()
				.id(config.getViewId())
				.size(config.getSize())
				.minSize(config.getSizeMin())
				.maxSize(config.getSizeMax())
				.title(config.getTitle())
				.dataFactory(new SUIWindowHandleDataFactory(() -> new SuiSceneController(config.getState(), config.getNodeFactory())))
				.build();
		viewService.registerView(view);
	}




	/**
	 * Creates a new popup configuration
	 *
	 * @param config the config providing data data
	 * @return the created popup config
	 */
	private PopupConfiguration buildPopupConfig(final SAppWindowConfig config) {
		return PopupConfiguration.builder()
				.parent(windowHandles.get(config.getOwnerWindowId()))
				.modality(config.getModality())
				.style(config.getWindowStyle())
				.wait(config.isWait())
				.alwaysOnTop(config.isAlwaysOnTop())
				.build();
	}




	@Override
	public void closeWindow(final String windowId) {
		if (windowHandles.containsKey(windowId)) {
			final ViewService viewService = viewServiceProvider.get();
			final WindowHandle windowHandle = windowHandles.remove(windowId);
			if (viewService.isWindowHandleActive(windowHandle)) {
				viewServiceProvider.get().closePopup(windowHandle);
			}
			windowHandle.getCurrentRootNode().getScene().getWindow().setOnCloseRequest(null);
		} else {
			log.warn("Can not close window {}. Window was not found in open windows.", windowId);
		}
	}

}
