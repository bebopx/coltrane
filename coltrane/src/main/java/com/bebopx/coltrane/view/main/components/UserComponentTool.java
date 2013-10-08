package com.bebopx.coltrane.view.main.components;

import com.bebopx.coltrane.bridge.LocalAuthenticator;
import com.bebopx.coltrane.util.UserTool;
import com.bebopx.common.security.User;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Button;
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
public class UserComponentTool {
    private UserComponentTool() { }
    
    public static VerticalLayout buildUserComponent() {

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

        MenuBar.MenuItem settingsMenu;
        settingsMenu = settings.addItem("", null);
        settingsMenu.setStyleName("icon-cog");
        settingsMenu.addItem("Settings", buildSettingsCommand());
        settingsMenu.addItem("Preferences", buildSettingsCommand());
        settingsMenu.addSeparator();
        settingsMenu.addItem("My Account", buildSettingsCommand());
    }

    private static MenuBar.Command buildSettingsCommand() {
        MenuBar.Command cmd;
        cmd = new MenuBar.Command() {
            @Override
            public void menuSelected(final MenuBar.MenuItem selectedItem) {
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
                LocalAuthenticator.doLogout();
            }
        });
        return localButton;
    }
}
