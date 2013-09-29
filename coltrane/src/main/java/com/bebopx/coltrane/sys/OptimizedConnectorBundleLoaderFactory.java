package com.bebopx.coltrane.sys;

import java.util.HashSet;
import java.util.Set;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.vaadin.client.ui.button.ButtonConnector;
import com.vaadin.client.ui.csslayout.CssLayoutConnector;
import com.vaadin.client.ui.label.LabelConnector;
import com.vaadin.client.ui.orderedlayout.HorizontalLayoutConnector;
import com.vaadin.client.ui.orderedlayout.VerticalLayoutConnector;
import com.vaadin.client.ui.panel.PanelConnector;
import com.vaadin.client.ui.passwordfield.PasswordFieldConnector;
import com.vaadin.client.ui.textfield.TextFieldConnector;
import com.vaadin.client.ui.ui.UIConnector;
import com.vaadin.client.ui.window.WindowConnector;
import com.vaadin.server.widgetsetutils.ConnectorBundleLoaderFactory;
import com.vaadin.shared.ui.Connect.LoadStyle;

/**
 *
 * @author thiago
 */
public class OptimizedConnectorBundleLoaderFactory
        extends ConnectorBundleLoaderFactory {

    /**
     *
     */
    private final transient Set<String> eagerConnectors = new HashSet<String>();

    { //NOPMD - extended class that will call super()
        eagerConnectors.add(PasswordFieldConnector.class.getName());
        eagerConnectors.add(VerticalLayoutConnector.class.getName());
        eagerConnectors.add(HorizontalLayoutConnector.class.getName());
        eagerConnectors.add(ButtonConnector.class.getName());
        eagerConnectors.add(UIConnector.class.getName());
        eagerConnectors.add(CssLayoutConnector.class.getName());
        eagerConnectors.add(TextFieldConnector.class.getName());
        eagerConnectors.add(PanelConnector.class.getName());
        eagerConnectors.add(LabelConnector.class.getName());
        eagerConnectors.add(WindowConnector.class.getName());
    }

    /**
     *
     * @param connectorType Vaadin UI connector
     * @return The load style used by Vaadin server
     */
    @Override
    protected final LoadStyle getLoadStyle(final JClassType connectorType) {
        LoadStyle finalLoadStyle;

        if (eagerConnectors.contains(connectorType.getQualifiedBinaryName())) {
            finalLoadStyle = LoadStyle.EAGER;
        } else {
            // Loads all other connectors immediately after the initial view has
            // been rendered
            finalLoadStyle = LoadStyle.DEFERRED;
        }

        return finalLoadStyle;
    }
}