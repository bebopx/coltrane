package com.bebopx.coltrane.view.main;

import com.bebopx.coltrane.bridge.LocalAuthenticator;
import com.bebopx.coltrane.main.RootUI;
import com.bebopx.coltrane.util.ComponentTool;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import com.google.common.base.Strings;

/**
 *
 * @author thiago
 */
public final class LoginSpecialView {

    /**
     * Utility class, can't be instantiated, so private constructor.
     */
    private LoginSpecialView() {
    }

    /**
     * Build a Login screen, directly attached to the UI.
     *
     * @param root Root CssLayout.
     */
    public static void build(final CssLayout root) {
        createUIComponents(root);
    }

    /**
     * Destroy this login window.
     *
     * @param root Root CssLayout.
     */
    public static void destroy(final CssLayout root) {
        ComponentTool.removeChildComponentbyDescription(root, "login-view");
    }

    /**
     * Login was successful.
     *
     * @param root Root CssLayout.
     */
    private static void doLogin(final CssLayout root) {
        ((RootUI) root.getUI()).buildMainView();
    }

    /**
     * Generate a login error message Label. Attention: It's just the Label
     * component, which must be added to the login panel component.
     *
     * @param message The error message.
     * @return Label describing login error.
     */
    private static Label showLoginError(final String message) {
        final Label error = new Label(
                message,
                ContentMode.HTML);
        error.addStyleName("error");
        error.setDescription("login-error");
        error.setSizeUndefined();
        error.addStyleName("light");
        error.addStyleName("v-animate-reveal");
        return error;
    }

    /**
     * Creates the Login screen structure and functionality.
     *
     * @param root Root CssLayout.
     */
    private static void createUIComponents(final CssLayout root) {


        // There's gotta be a way to break this gigantic UI definition.

        final Label background = new Label();
        background.setSizeUndefined();
        background.addStyleName("login-bg");
        root.addComponent(background);
        root.getUI().addStyleName("login");

        VerticalLayout loginLayout;
        loginLayout = new VerticalLayout();
        loginLayout.setSizeFull();
        loginLayout.addStyleName("login-layout");
        loginLayout.setDescription("login-view");
        root.addComponent(loginLayout);

        final CssLayout loginPanel = new CssLayout();
        loginPanel.addStyleName("login-panel");

        final HorizontalLayout labels = new HorizontalLayout();
        labels.setWidth("100%");
        labels.setMargin(true);
        labels.addStyleName("labels");
        loginPanel.addComponent(labels);

        final Label welcome = new Label("Welcome");
        welcome.setSizeUndefined();
        welcome.addStyleName("h4");
        labels.addComponent(welcome);
        labels.setComponentAlignment(welcome, Alignment.MIDDLE_LEFT);

        final Label title = new Label("bebopX Coltrane");
        title.setSizeUndefined();
        title.addStyleName("h2");
        title.addStyleName("light");
        labels.addComponent(title);
        labels.setComponentAlignment(title, Alignment.MIDDLE_RIGHT);

        final HorizontalLayout fields = new HorizontalLayout();
        fields.setSpacing(true);
        fields.setMargin(true);
        fields.addStyleName("fields");

        final TextField username = new TextField("Username");
        username.focus();
        fields.addComponent(username);

        final PasswordField password = new PasswordField("Password");
        fields.addComponent(password);

        final Button signin = new Button("Sign In");
        signin.addStyleName("default");
        signin.setClickShortcut(KeyCode.ENTER);
        fields.addComponent(signin);
        fields.setComponentAlignment(signin, Alignment.BOTTOM_LEFT);

        loginPanel.addComponent(fields);
        loginLayout.addComponent(loginPanel);
        loginLayout.setComponentAlignment(loginPanel, Alignment.MIDDLE_CENTER);

        signin.addClickListener(new ClickListener() {
            @Override
            public void buttonClick(final ClickEvent event) {
                if (Strings.isNullOrEmpty(username.getValue())
                        || Strings.isNullOrEmpty(password.getValue())) {

                    ComponentTool.removeChildComponentbyDescription(
                            loginPanel, "login-error");

                    loginPanel.addComponent(
                            showLoginError(
                            "Please fill username and password."));

                    username.focus();
                    username.selectAll();
                } else {
                    LocalAuthenticator auth;
                    // We probably should do something better here
                    auth = new LocalAuthenticator(
                            username.getValue(), password.getValue());
                    if (auth.doAuthenticate()) {
                        signin.removeClickShortcut();
                        doLogin(root);
                    } else {
                        ComponentTool.removeChildComponentbyDescription(
                                loginPanel, "login-error");

                        loginPanel.addComponent(
                                showLoginError(
                                "Wrong username or password."));

                        username.focus();
                        username.selectAll();
                    }
                }
            }
        });
    }
}
