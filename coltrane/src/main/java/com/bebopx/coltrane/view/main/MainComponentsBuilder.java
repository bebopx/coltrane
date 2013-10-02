package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.bridge.LocalAuthenticator;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.util.ComponentTool;
import com.bebopx.common.security.LocalPrincipal;
import com.bebopx.common.security.User;
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
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author thiago
 */
public final class MainComponentsBuilder {

    private MainComponentsBuilder() {
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

    public static VerticalLayout buildSidebarLayer(final CssLayout menu) {

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

    public static VerticalLayout buildUserComponent() {

        return new VerticalLayout() {
            {
                setSizeUndefined();
                addStyleName("user");
                Image profilePic = new Image(null, new ThemeResource("img/profile-pic.png"));
                profilePic.setWidth("34px");
                addComponent(profilePic);


                PrincipalCollection currentCollection;
                currentCollection = SecurityUtils.getSubject().getPrincipals();

                LocalPrincipal currentPrincipal;
                currentPrincipal = (LocalPrincipal) currentCollection.getPrimaryPrincipal();

                User currentUser;
                currentUser = currentPrincipal.getUser();

                Label userName = new Label(currentUser.fullname);

                userName.setSizeUndefined();

                addComponent(userName);
                MenuBar.Command cmd = new MenuBar.Command() {
                    @Override
                    public void menuSelected(
                            MenuBar.MenuItem selectedItem) {
                        Notification
                                .show("Hold there, cowboy.");
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
                        LocalAuthenticator.Logout();
                        UI.getCurrent().close();
                    }
                });
            }
        };
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
}
