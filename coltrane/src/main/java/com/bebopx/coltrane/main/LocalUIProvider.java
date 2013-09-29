package com.bebopx.coltrane.main;

import com.vaadin.server.UIClassSelectionEvent;
import com.vaadin.server.UICreateEvent;
import com.vaadin.server.UIProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import ru.xpoft.vaadin.SpringApplicationContext;

/**
 * Detects the device that the user is browsing the system.
 *
 * @author thiago
 */
public class LocalUIProvider extends UIProvider {

    private static Logger logger = LoggerFactory.getLogger(LocalUIProvider.class);
    /**
     * Servlet parameter name for UI bean
     */
    protected static final String BEAN_NAME_PARAMETER = "beanName";

    public LocalUIProvider() {
    }

    @Override
    public UI createInstance(UICreateEvent event) {
        return (UI) SpringApplicationContext.getApplicationContext().getBean(getUIBeanName(event.getRequest()));
    }

    @Override
    public Class<? extends UI> getUIClass(UIClassSelectionEvent event) {
        // Add session-scoped UI to current window
        if (this.isSessionScopedUI(event.getRequest())) {
            String windowName = event.getRequest().getParameter("v-wn");

            Map<String, Integer> retainOnRefreshUIs = VaadinSession.getCurrent().getPreserveOnRefreshUIs();
            if (windowName != null && !retainOnRefreshUIs.isEmpty() && retainOnRefreshUIs.get(windowName) == null) {
                // Check for session-scope-UI
                for (Map.Entry<String, Integer> entry : retainOnRefreshUIs.entrySet()) {
                    UI ui = VaadinSession.getCurrent().getUIById(entry.getValue());
                    VaadinSession.getCurrent().getPreserveOnRefreshUIs().put(windowName, ui.getUIId());
                    break;
                }
            }
        }

        return (Class<? extends UI>) SpringApplicationContext.getApplicationContext().getType(getUIBeanName(event.getRequest()));
    }

    @Override
    public final boolean isPreservedOnRefresh(final UICreateEvent event) {
        boolean localRefresh;
        if (isSessionScopedUI(event.getRequest())) {
            localRefresh = true;
        } else {
            localRefresh = super.isPreservedOnRefresh(event);
        }

        return localRefresh;
    }

    public final boolean isSessionScopedUI(final VaadinRequest request) {
        return !SpringApplicationContext.getApplicationContext().isPrototype(
                getUIBeanName(request));
    }

    /**
     * Returns the bean name to be retrieved from the application bean context
     * and used as the UI. The default implementation uses the servlet init
     * property {@link #BEAN_NAME_PARAMETER} or "ui" if not defined.
     *
     * @param request the current Vaadin request
     * @return the UI bean name in the application context
     */
    protected final String getUIBeanName(final VaadinRequest request) {

        Class<? extends UI> chosenUI;
        Device currentDevice;
        currentDevice = DeviceUtils.getCurrentDevice(
                (HttpServletRequest) request);

        if (currentDevice.isMobile()) {
            chosenUI = MobileCheckUI.class;
        } else {
            chosenUI = RootUI.class;
        }

        return chosenUI.getSimpleName();
    }
}
