package com.bebopx.coltrane.util;

import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;

/**
 *
 * @author thiago
 */
public class CssLayoutIterableRunnable {

    /**
     * CssLayout that will be worked on.
     */
    private final transient CssLayout localSource;

    /**
     *
     * @param source CssLayout that will be worked on.
     */
    public CssLayoutIterableRunnable(final CssLayout source) {
        this.localSource = source;
    }

    /**
     * Method for extension.
     * @param localComponent Local component that will be worked on.
     */
    public void run(final Component localComponent) { //NOPMD - See above.
    }

    /**
     *
     * @return CssLayout that will be worked on.
     */
    public final CssLayout getLocalSource() {
        return this.localSource;
    }
}
