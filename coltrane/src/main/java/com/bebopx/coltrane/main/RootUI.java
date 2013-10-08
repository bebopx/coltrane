package com.bebopx.coltrane.main;

import com.bebopx.coltrane.main.uibridge.UiBridge;
import com.bebopx.coltrane.sys.LocalErrorHandler;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.view.main.LoginSpecialView;
import com.bebopx.coltrane.view.main.MainSpecialView;
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
public class RootUI extends UI {

    private static final Logger logger = LoggerFactory.getLogger(RootUI.class);

    @Autowired
    private transient UiBridge uiBridge;

    @Override
    protected void init(final VaadinRequest request) {
        setLocale(Locale.US);

        CssLayout root;
        root = uiBridge.getMainRoot();



        setContent(root);
        root.addStyleName("root");
        root.setSizeFull();

        VaadinSession.getCurrent().setErrorHandler(new LocalErrorHandler());

        LoginSpecialView.build(root);

    }

    public void buildMainView() {
        LoginSpecialView.destroy(uiBridge.getMainRoot());
        LocalNavigator localNavigator;
        localNavigator = new LocalNavigator(this, uiBridge);
        uiBridge.setNav(localNavigator);
        MainSpecialView.build(uiBridge);
    }
}
