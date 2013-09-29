package com.bebopx.coltrane.main.uibridge;

import com.bebopx.coltrane.main.RootUI;
import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.view.util.ViewManager;
import com.vaadin.ui.CssLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("uiBridge")
@Scope("prototype")
public class UiBridge {

    private transient Logger logger = LoggerFactory.getLogger(RootUI.class); //NOPMD - IoC logger
    
    @Autowired
    private transient ViewManager viewManager;
    
    private final CssLayout content = new CssLayout();
    private final CssLayout root = new CssLayout();
    private final CssLayout menu = new CssLayout();
    private LocalNavigator nav;

    public LocalNavigator getNav() {
        logger.info("requesting nav");
        return this.nav;
    }

    public void setNav(LocalNavigator navigator) {
        logger.info("setting nav");
        this.nav = navigator;
    }
    
    
    public ViewManager getViewManager() {
        logger.info("requesting viewmanager");
        return this.viewManager;
    }
    public CssLayout getContent() {
        logger.info("requesting content");
        return this.content;
    }

    public CssLayout getMainRoot() {
        logger.info("requesting root");
        return this.root;
    }

    public CssLayout getMainMenu() {
        logger.info("requesting menu");
        return this.menu;
    }
}
