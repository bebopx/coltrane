/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bebopx.coltrane.sys;

import com.vaadin.server.DefaultErrorHandler;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.Sizeable;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

/**
 *
 * @author thiago
 */
public class LocalErrorHandler implements ErrorHandler {
    
    
    @Override
    public void error(com.vaadin.server.ErrorEvent event) {
        // connector event
        if (event.getThrowable().getCause() instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) event.getThrowable().getCause();
            Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);

            // Cleanup view. Now Vaadin ignores errors and always shows the view.  :-(
            // since beta10
            //setContent(null);
            return;
        }

        // Error on page load. Now it doesn't work. User sees standard error page.
        if (event.getThrowable() instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) event.getThrowable();

            Label label = new Label(exception.getMessage());
            label.setWidth(-1, Sizeable.Unit.PERCENTAGE);

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

            //setContent(mainLayout);
            Notification.show(exception.getMessage(), Notification.Type.ERROR_MESSAGE);
            return;
        }

        DefaultErrorHandler.doDefault(event);
    }
}
