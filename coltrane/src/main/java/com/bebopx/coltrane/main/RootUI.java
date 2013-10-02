package com.bebopx.coltrane.main;

import com.bebopx.coltrane.main.uibridge.UiBridge;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.view.main.LoginSpecialView;
import com.bebopx.coltrane.view.main.MainSpecialView;
import com.bebopx.coltrane.view.util.ViewManager;
import java.util.Locale;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.event.Transferable;
import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ExternalResource;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Theme("dashboard")
@Title("bebopx Coltrane")
@Scope("prototype") //prototyped because one session may have more than one UI
@Component("RootUI")
public class RootUI extends UI implements ErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(RootUI.class);

    @Autowired
    private transient UiBridge uiBridge;

    @Override
    protected void init(final VaadinRequest request) {
        
        logger.info("rootui");
        
        final CssLayout root;
        root = uiBridge.getMainRoot();
        
        

        setLocale(Locale.US);

        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        VaadinSession.getCurrent().setErrorHandler(this);

        LoginSpecialView.build(root);

    }

    public void buildMainView() {
        LoginSpecialView.destroy(uiBridge.getMainRoot());
        LocalNavigator localNavigator = new LocalNavigator(this, uiBridge);
        uiBridge.setNav(localNavigator);
        MainSpecialView.build(uiBridge);
    }
    private Transferable items;

    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        // connector event
        if (event.getThrowable().getCause() instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) event.getThrowable().getCause();
            Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);

            // Cleanup view. Now Vaadin ignores errors and always shows the view.  :-(
            // since beta10
            setContent(null);
            return;
        }

        // Error on page load. Now it doesn't work. User sees standard error page.
        if (event.getThrowable() instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) event.getThrowable();

            Label label = new Label(exception.getMessage());
            label.setWidth(-1, Unit.PERCENTAGE);

            Link goToMain = new Link("Go to main", new ExternalResource("/"));

            VerticalLayout layout = new VerticalLayout();
            layout.addComponent(label);
            layout.addComponent(goToMain);
            layout.setComponentAlignment(label, Alignment.MIDDLE_CENTER);
            layout.setComponentAlignment(goToMain, Alignment.MIDDLE_CENTER);

            VerticalLayout mainLayout = new VerticalLayout();
            mainLayout.setSizeFull();
            mainLayout.addComponent(layout);
            mainLayout.setComponentAlignment(layout, Alignment.MIDDLE_CENTER);

            setContent(mainLayout);
            Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);
            return;
        }

        DefaultErrorHandler.doDefault(event);
    }
}
