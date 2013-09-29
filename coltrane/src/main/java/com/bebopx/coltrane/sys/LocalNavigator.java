package com.bebopx.coltrane.sys;

import com.bebopx.coltrane.main.RootUI;
import com.bebopx.coltrane.main.uibridge.UiBridge;
import com.bebopx.coltrane.view.util.ViewManager;
import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.xpoft.vaadin.DiscoveryNavigator;

import java.util.Arrays;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import ru.xpoft.vaadin.security.ShiroSecurityNavigator;

/**
 * Uses
 *
 * @RequiresRoles,
 * @RequiresPermissions,
 * @RequiresAuthentication,
 * @RequiresGuest,
 * @RequiresUser annotations. You don't need AspectJ Exclude views, that user
 * doesn't have access
 *
 * @author xpoft
 */
public class LocalNavigator extends ShiroSecurityNavigator {

    private transient ViewManager viewManager;
    private static Logger logger = LoggerFactory.getLogger(LocalNavigator.class);

    public LocalNavigator(final UI source, final UiBridge bridge) {
        super(source, bridge.getContent());
        this.viewManager = bridge.getViewManager();
        logger.info("created");
        initLocalViews();
    }

    protected void initLocalViews()
    {
        super.initViews();
    }
    
    @Override
    protected void initViews()
    {
    }
    
    @Override
    protected void addCachedBeans() {
        for (ViewCache view : views) {
            // Only allowed beans
            if (hasAccess(view.getClazz())) {
                viewManager.addView(view.getClazz());
                logger.info(view.getBeanName());
                logger.debug("view name: \"{}\", class: {}, viewCached: {}", new Object[]{view.getName(), view.getClazz(), view.isCached()});
                addBeanView(view.getName(), view.getBeanName(), view.getClazz(), view.isCached());
            }
        }
    }

    @Override
    public void addBeanView(String viewName, Class<? extends View> viewClass, boolean cached) {
        if (!hasAccess(viewClass)) {
            return;
        }
        viewManager.addView(viewClass);
        logger.info(viewName);
        super.addBeanView(viewName, viewClass, cached);
    }
}
