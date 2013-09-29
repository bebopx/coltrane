package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.main.uibridge.UiBridge;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.view.home.HomeView;
import com.bebopx.coltrane.view.util.Icon;
import com.bebopx.coltrane.view.util.ViewManager;

import com.google.common.base.Strings;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;

import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xpoft.vaadin.VaadinView;

public final class MainSpecialView {

    private static final Logger logger = LoggerFactory.getLogger( //NOPMD
            MainSpecialView.class); // NOPMD - our logger, our rules
    private static transient CssLayout menu;
    private static transient CssLayout content;
    private static transient CssLayout root;
    private static transient LocalNavigator nav;
    private static transient ViewManager viewManager;

    private MainSpecialView() {
    }

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


        buildViewButtons(viewManager.getParentViews());


        viewManager.getViewButton(HomeView.class).setCaption(
                "Home<span class=\"badge\">2</span>");
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

                String icon;
                if (localObj.isAnnotationPresent(Icon.class)) {
                    icon = localObj.getAnnotation(Icon.class).value();
                } else {
                    icon = "";
                }
                if (!Strings.isNullOrEmpty(view)) {
                    Button generatedButton;
                    generatedButton = MainComponentsBuilder.buildViewAccess(view, icon, menu, nav);
                    generatedButton.setHtmlContentAllowed(true);
                    menu.addComponent(generatedButton);
                    viewManager.addButtonToView(localObj, generatedButton);
                }
            } else {
                // Bizarre issue. The viewManager is populated by annotation.
                // Find out what the hell happened, man.
                logger.error("Non-annotated view got into ViewManager.");
            }
        }
    }
}
