package com.bebopx.coltrane.view.sales;

import com.bebopx.coltrane.bridge.LocalAuthenticator;
import com.bebopx.coltrane.view.util.Icon;
import java.awt.Color;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.context.annotation.Scope;
import ru.xpoft.vaadin.VaadinView;

@org.springframework.stereotype.Component
@Scope("prototype")
@VaadinView(SalesView.NAME)
@Icon("icon-reports")
@RequiresRoles("user")
public class SalesView extends VerticalLayout implements View {

    public static final String NAME = "sales";

    Color[] colors = new Color[] { new Color(52, 154, 255),
            new Color(242, 81, 57), new Color(255, 201, 35),
            new Color(83, 220, 164) };
    int colorIndex = -1;

    @Override
    public void enter(ViewChangeEvent event) {
        setSizeFull();
        addStyleName("timeline");
    }
}