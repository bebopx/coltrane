package com.bebopx.coltrane.main;

import com.bebopx.common.definitions.CommonStaticDefinitions;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author thiago
 */
@Theme(Reindeer.THEME_NAME)
@Title(CommonStaticDefinitions.ApplicationName)
@Component("MobileCheckUI")
@Scope("prototype")
public class MobileCheckUI extends UI {

    @Override
    protected final void init(final VaadinRequest request) {

        // Create Vaadin label with the message.
        // Should be getting this text from somewhere, not here.
        Label localText;
        localText = new Label(
                "<h1>bebopX Coltrane</h1>"
                + "<h3>This app is not designed for mobile devices.</h3>"
                + "<p>If you wish, you can continue to <a href=\""
                + request.getContextPath()
                + request.getPathInfo()
                + "?mobile=false\">load it anyway</a>.</p>",
                ContentMode.HTML);

        // Create Vaadin layout to insert into the UI.
        VerticalLayout localContent;
        localContent = new VerticalLayout();
        localContent.setMargin(true);
        localContent.addComponent(localText);

        // Configure the UI.
        setWidth("400px");
        setContent(localContent);
    }
}
