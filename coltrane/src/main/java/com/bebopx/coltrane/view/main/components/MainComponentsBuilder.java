package com.bebopx.coltrane.view.main.components;

import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

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

    private static VerticalLayout buildSidebarLayer(final CssLayout menu) {

        return new VerticalLayout() {
            {
                addStyleName("sidebar");
                setWidth(null);
                setHeight("100%");

                // Branding element
                addComponent(BrandingTool.buildBrandingComponent());

                // Main menu
                addComponent(menu);
                setExpandRatio(menu, 1);

                // User menu
                addComponent(UserComponentTool.buildUserComponent());
                menu.addStyleName("menu");
                menu.setHeight("100%");
            }
        };
    }
}
