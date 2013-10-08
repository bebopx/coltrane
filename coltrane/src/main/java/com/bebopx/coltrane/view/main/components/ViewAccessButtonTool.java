package com.bebopx.coltrane.view.main.components;

import com.bebopx.coltrane.sys.LocalNavigator;
import com.bebopx.coltrane.util.ComponentTool;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.NativeButton;

/**
 *
 * @author thiago
 */
public final class ViewAccessButtonTool {

    private ViewAccessButtonTool() {
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
