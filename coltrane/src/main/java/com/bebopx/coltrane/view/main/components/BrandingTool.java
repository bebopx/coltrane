/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bebopx.coltrane.view.main.components;

import com.bebopx.common.definitions.CommonStaticDefinitions;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;

/**
 *
 * @author thiago
 */
public class BrandingTool {
    private BrandingTool() { }
    
        public static CssLayout buildBrandingComponent() {
        return new CssLayout() {
            {
                addStyleName("branding");
                Label logo;
                logo = new Label(
                        CommonStaticDefinitions.getFullApplicationName(),
                        ContentMode.HTML);
                logo.setSizeUndefined();
                addComponent(logo);
            }
        };
    }
}
