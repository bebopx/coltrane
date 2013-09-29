package com.bebopx.coltrane.view.main;

import java.awt.Color;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;
import org.springframework.stereotype.Component;
import org.springframework.context.annotation.Scope;
import ru.xpoft.vaadin.VaadinView;

@Component
@Scope("prototype")
@VaadinView(EmptyView.NAME)
public class EmptyView extends VerticalLayout implements View {

    public static final String NAME = "";

    @Override
    public void enter(ViewChangeEvent event) {
        setSizeFull();
        addStyleName("timeline");
    }
}