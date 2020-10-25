package de.ruegnerlukas.simpleapplication.core.presentation.simpleui.windowmanager;

import de.ruegnerlukas.simpleapplication.common.instanceproviders.providers.Provider;
import de.ruegnerlukas.simpleapplication.common.validation.Validations;
import de.ruegnerlukas.simpleapplication.core.presentation.views.ViewService;
import de.ruegnerlukas.simpleapplication.core.presentation.views.WindowHandle;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowCloseData;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowManager;
import de.ruegnerlukas.simpleapplication.simpleui.core.windows.WindowOpenData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SAppWindowManager implements WindowManager {


	/**
	 * The provider for the {@link ViewService}.
	 */
	private final Provider<ViewService> viewServiceProvider = new Provider<>(ViewService.class);




	@Override
	public void openNew(final WindowOpenData windowOpenData) {
		Validations.INPUT.notNull(windowOpenData).exception("The window data may not be null.");
		Validations.INPUT.typeOf(windowOpenData, SAppWindowOpenData.class)
				.exception("Window-open-data not of type {}", SAppWindowOpenData.class);
		openNew((SAppWindowOpenData) windowOpenData);
	}




	/**
	 * Opens the new window
	 *
	 * @param windowOpenData information about the window
	 */
	private void openNew(final SAppWindowOpenData windowOpenData) {
		Validations.INPUT.notEmpty(windowOpenData.getViewId()).exception("The view id may not be null or empty.");
		Validations.INPUT.notNull(windowOpenData.getPopupConfig()).exception("The popup-config may not be null.");

		log.debug("Opening new window using the view-service: viewId={}", windowOpenData.getViewId());
		final WindowHandle handle = viewServiceProvider.get().popupView(windowOpenData.getViewId(), windowOpenData.getPopupConfig());
		if (windowOpenData.getOnOpen() != null) {
			windowOpenData.getOnOpen().accept(handle);
		}
	}




	@Override
	public void close(final WindowCloseData windowCloseData) {
		Validations.INPUT.notNull(windowCloseData).exception("The window data may not be null.");
		Validations.INPUT.typeOf(windowCloseData, SAppWindowCloseData.class)
				.exception("Window-open-data not of type {}", SAppWindowCloseData.class);
		close((SAppWindowCloseData) windowCloseData);
	}




	/**
	 * Closes an open window
	 *
	 * @param windowCloseData info about the window to close.
	 */
	private void close(final SAppWindowCloseData windowCloseData) {
		Validations.INPUT.isFalse(windowCloseData.getViewId() == null && windowCloseData.getWindowHandle() != null)
				.exception("The window handle and the view-id can not both be null.");
		final ViewService viewService = viewServiceProvider.get();
		if (windowCloseData.getWindowHandle() != null) {
			close(viewService, windowCloseData.getWindowHandle());
		} else {
			viewService.getWindowHandles(windowCloseData.getViewId()).forEach(handle -> close(viewService, handle));
		}

	}




	/**
	 * Closes the given handle.
	 *
	 * @param viewService the view service managing the handle
	 * @param handle      the window handle to close
	 */
	private void close(final ViewService viewService, final WindowHandle handle) {
		log.debug("Closing window: viewId={}, handleId={}.", handle.getViewId(), handle.getHandleId());
		viewService.closePopup(handle);
	}

}
