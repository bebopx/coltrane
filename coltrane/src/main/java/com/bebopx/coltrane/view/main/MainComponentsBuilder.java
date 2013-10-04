package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.bridge.LocalAuthenticator;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.util.ComponentTool;
import com.bebopx.coltrane.util.UserTool;
import com.bebopx.common.security.User;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author thiago
 */
public final class MainComponentsBuilder {

    private MainComponentsBuilder() {
    }
    
        public static Button buildViewAccessButton(final String view, final String icon,
            final CssLayout menu, final LocalNavigator nav) {


        Button localButton;
        localButton = new NativeButton(view); //constructor takes caption as arg
        localButton.addStyleName(icon);

        localButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {

                String navView;
                navView = "/".concat(view);

                ComponentTool.removeChildrenComponentStyleName(
                        menu, "selected");
                event.getButton().addStyleName("selected");

                if (!nav.getState().equals(navView)) {
                    nav.navigateTo(view);
                }
            }
        });

        return localButton;
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

    private static VerticalLayout buildSidebarLayer(final CssLayout menu) {

        return new VerticalLayout() {
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

    private static CssLayout buildBrandingComponent() {
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

    private static VerticalLayout buildUserComponent() {

        return new VerticalLayout() {
            {
                setSizeUndefined();
                addStyleName("user");

                addComponent(buildUserPictureSticker());
                addComponent(buildUserNameSticker());
                addComponent(buildSettingsMenu());
                addComponent(buildLogoutButton());

            }
        };
    }

    private static Image buildUserPictureSticker() {
        Image profilePic;
        profilePic = new Image(null, new ThemeResource("img/profile-pic.png"));
        profilePic.setWidth("34px");
        return profilePic;
    }

    private static Label buildUserNameSticker() {

        User currentUser;
        currentUser = UserTool.getCurrentUser();

        Label userName;
        userName = new Label(currentUser.fullname);

        userName.setSizeUndefined();
        return userName;
    }

    private static MenuBar buildSettingsMenu() {

        MenuBar settings;
        settings = new MenuBar();

        //lots of stuff get built right here
        buildSettingsMenuButtons(settings);

        return settings;
    }

    private static void buildSettingsMenuButtons(final MenuBar settings) {


        MenuItem settingsMenu;
        settingsMenu = settings.addItem("", null);
        settingsMenu.setStyleName("icon-cog");
        settingsMenu.addItem("Settings", buildSettingsCommand());
        settingsMenu.addItem("Preferences", buildSettingsCommand());
        settingsMenu.addSeparator();
        settingsMenu.addItem("My Account", buildSettingsCommand());
    }

    private static Command buildSettingsCommand() {
        Command cmd;
        cmd = new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuItem selectedItem) {
                Notification.show("Hold there, cowboy.");
                Notification.show("Hold there, cowboy.");
            }
        };
        return cmd;
    }

    private static Button buildLogoutButton() {
        Button localButton;

        localButton = new NativeButton("Logout");

        localButton.addStyleName("icon-cancel");
        localButton.setDescription("Sign Out");


        localButton.addClickListener(
                new Button.ClickListener() {
            @Override
            public void buttonClick(final Button.ClickEvent event) {
                LocalAuthenticator.Logout();
                UI.getCurrent().close();
            }
        });
        return localButton;
    }
}
