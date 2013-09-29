/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.bridge.Generator;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.util.ComponentTool;
import com.google.common.base.Strings;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author thiago
 */
public final class MainComponentsBuilder {

    private MainComponentsBuilder() {
    }

    public static Button buildViewAccess(final String view, final String icon,
            final CssLayout menu, final LocalNavigator nav) {
        
        String localICon = icon;
        
        if (Strings.isNullOrEmpty(localICon)) {
            localICon = new StringBuilder().append("icon-").append(view).toString();
        }
        
        return buildViewAccessButton(view, localICon, menu, nav);
    }

    private static Button buildViewAccessButton(final String view, final String icon,
            final CssLayout menu, final LocalNavigator nav) {

        Button localButton = new NativeButton(view.substring(0, 1).toUpperCase()
                + view.substring(1).replace('-', ' '));
        localButton.addStyleName(icon);

        localButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                ComponentTool.removeChildrenComponentStyleName(menu, "selected");
                event.getButton().addStyleName("selected");
                //if (!nav.getState().equals(new StringBuilder().append("/").append(view).toString())) {
                nav.navigateTo(view);
                //}
            }
        });
        return localButton;
    }

    public static CssLayout buildBrandingComponent() {
        return new CssLayout() {
            {
                addStyleName("branding");
                Label logo;
                logo = new Label(
                        "<span>bebopX</span> Coltrane",
                        ContentMode.HTML);
                logo.setSizeUndefined();
                addComponent(logo);
            }
        };
    }

    public static VerticalLayout buildSidebarLayer(final CssLayout menu) {


        return new VerticalLayout() {
            // Sidebar
            {
                addStyleName("sidebar");
                setWidth(null);
                setHeight("100%");

                // Branding element
                addComponent(MainComponentsBuilder.buildBrandingComponent());

                // Main menu
                addComponent(menu);
                setExpandRatio(menu, 1);

                // User menu
                addComponent(buildUserComponent());
                menu.addStyleName("menu");
                menu.setHeight("100%");
            }
        };
    }

    public static VerticalLayout buildUserComponent() {

        return new VerticalLayout() {
            {
                setSizeUndefined();
                addStyleName("user");
                Image profilePic = new Image(null, new ThemeResource("img/profile-pic.png"));
                profilePic.setWidth("34px");
                addComponent(profilePic);
                Label userName = new Label(new StringBuilder().append(Generator.randomFirstName()).append(" ").append(Generator.randomLastName()).toString());

                userName.setSizeUndefined();

                addComponent(userName);
                MenuBar.Command cmd = new MenuBar.Command() {
                    @Override
                    public void menuSelected(
                            MenuBar.MenuItem selectedItem) {
                        Notification
                                .show("Not implemented in this demo");
                    }
                };
                MenuBar settings = new MenuBar();
                MenuBar.MenuItem settingsMenu = settings.addItem("",
                        null);

                settingsMenu.setStyleName("icon-cog");
                settingsMenu.addItem("Settings", cmd);
                settingsMenu.addItem("Preferences", cmd);
                settingsMenu.addSeparator();

                settingsMenu.addItem("My Account", cmd);
                addComponent(settings);
                Button exit = new NativeButton("Exit");

                exit.addStyleName("icon-cancel");
                exit.setDescription("Sign Out");
                addComponent(exit);

                exit.addClickListener(
                        new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        // buildLoginView(true);
                    }
                });
            }
        };
    }

    public static HorizontalLayout buildMainLayer(final CssLayout menu, final CssLayout content) {

        return new HorizontalLayout() {
            {
                setSizeFull();
                addStyleName("main-view");
                addComponent(buildSidebarLayer(menu));
                addComponent(content);
                content.setSizeFull();
                content.addStyleName("view-content");
                setExpandRatio(content, 1);
            }
        };
    }
}
