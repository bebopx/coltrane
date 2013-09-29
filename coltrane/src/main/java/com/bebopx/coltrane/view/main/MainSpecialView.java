package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.main.uibridge.UiBridge;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.view.home.HomeView;
import com.bebopx.coltrane.view.util.Icon;
import com.bebopx.coltrane.view.util.ViewManager;

import com.google.common.base.Strings;

import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xpoft.vaadin.VaadinView;

/**
 *
 * @author thiago
 */
public final class MainSpecialView {

    /**
     * Good ole' logger. Lowercase because we find it cozier this way, and
     * to distinguish it from true constants. So we put a NOPMD on it.
     * Our logger, our rules.
     */
    private static final Logger logger = LoggerFactory.getLogger( //NOPMD
            MainSpecialView.class); //NOPMD
    
    /**
     * UiBridge's components. Can't believe we actually use 'em all.
     */
    private static transient CssLayout menu;
    private static transient CssLayout content;
    private static transient CssLayout root;
    private static transient LocalNavigator nav;
    private static transient ViewManager viewManager;

    /**
     * This constructor is private because this is a utility, non-instanceable
     * class. The one who tries to instance this shall be banned from the
     * project.
     */
    private MainSpecialView() {
    }

    /**
     * Builds the Main Special View. This is an static method, because the
     * Main Special View couples itself on the Root UI and won't let it go.
     * Ever.
     * Mostly because we want to control everything using stuff like UiBridge
     * and views.
     * @param localUiBridge UiBridge injected to current UI.
     */
    public static void build(final UiBridge localUiBridge) {

        /**
         * Goes without saying, but the next two blocks are related to
         * distributing UiBridges parts in variables, so we can work with'em.
         */
        content = localUiBridge.getContent();
        root = localUiBridge.getMainRoot();
        menu = localUiBridge.getMainMenu();
        nav = localUiBridge.getNav();
        viewManager = localUiBridge.getViewManager();

        /**
         * So we build the basis of our UI. A root container has two containers,
         * menu (that nice sidebar on your left) and content (which we replace
         * with the views' contents).
         */
        root.addComponent(MainComponentsBuilder.buildMainLayer(menu, content));

        /**
         * Shall we build our sidebar with our parent views?
         */
        buildViewButtons(viewManager.getParentViews());

        // Fake notifications, since we don't have a notification manager yet.
        viewManager.getViewButton(HomeView.class).setCaption(
                "Home<span class=\"badge\">2</span>");

        /**
         * Going home.
         */
        viewManager.getViewButton(HomeView.class).addStyleName("selected");
        nav.navigateTo("home");
    }

    private static void buildViewButtons(final Iterator<Class<?>> iterate) {
        /**
         * This gets a bit messy. We get all the available views from the view
         * manager, get their name, their icon and add the fellas to the our
         * shiny sidebar.
         */
        while (iterate.hasNext()) {
            Class<?> localObj = iterate.next(); //NOPMD - iterator
            if (localObj.isAnnotationPresent(VaadinView.class)) {

                String view;
                view = localObj.getAnnotation(VaadinView.class).value();

                /**
                 * If our dear developer did not manually set an icon, the
                 * annotation will be exploding null, so we set it as empty
                 * and let the component builder handle this.
                 */
                String icon;
                icon = Strings.nullToEmpty(
                        localObj.getAnnotation(Icon.class).value());

                /**
                 * So this is kinda inverted behaviour. If condition true, then
                 * error, but I thought this was better than putting an almost
                 * unnoticeable ! in front of the checker. Sorry, mateys.
                 */
                if (Strings.isNullOrEmpty(view)) {
                    logger.error(localObj.getCanonicalName().concat(
                            ":Nameless view. Please inform view's name."));
                } else {
                    Button generatedButton;
                    generatedButton = MainComponentsBuilder.buildViewAccess(
                            view, icon, menu, nav);
                    generatedButton.setHtmlContentAllowed(true);
                    menu.addComponent(generatedButton);
                    viewManager.addButtonToView(localObj, generatedButton);
                }

            } else {
                // Bizarre issue. The viewManager is populated by annotation.
                // Find out what the hell happened, if this comes up, man.
                logger.error("Non-annotated view got into ViewManager.");
            }
        }
    }
}
