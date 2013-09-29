package com.bebopx.coltrane.util;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

import java.util.Iterator;

import com.google.common.base.Strings;

/**
 *
 * @author thiago
 */
public final class ComponentTool {

    /**
     * Utility class, can't be instantiated, so private constructor.
     */
    private ComponentTool() {
    }

    /**
     * Removes a child component using its description.
     *
     * @param source The CssLayout container.
     * @param description Component's description.
     */
    public static void removeChildComponentbyDescription(
            final CssLayout source, final String description) {

        runCssLayoutIterator(new CssLayoutIterableRunnable(source) {
            @Override
            public void run(final Component localComponent) {
                if (description.equals(localComponent.getDescription())) {
                    source.removeComponent(localComponent);
                }
            }
        });
    }

    /**
     * Removes a StyleName from all children components.
     *
     * @param source The CssLayout container.
     * @param stylename StyleName descriptor to be removed.
     */
    public static void removeChildrenComponentStyleName(
            final CssLayout source, final String stylename) {

        runCssLayoutIterator(new CssLayoutIterableRunnable(source) {
            @Override
            public void run(final Component localComponent) {
                if (!Strings.isNullOrEmpty(localComponent.getStyleName())) {
                    localComponent.removeStyleName(stylename);
                }
            }
        });
    }

    /**
     * Runs an specific action inside a CssLayout iterator.
     *
     * @param action CssLayoutIterableRunnable containing the action.
     */
    private static void runCssLayoutIterator(
            final CssLayoutIterableRunnable action) {

        final Iterator<Component> iterate = action.getLocalSource().iterator();
        while (iterate.hasNext()) {
            action.run(iterate.next());

        }
    }
}
