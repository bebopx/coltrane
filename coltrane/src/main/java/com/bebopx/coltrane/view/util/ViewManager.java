package com.bebopx.coltrane.view.util;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.TreeMultimap;
import com.vaadin.ui.Button;
import java.util.Iterator;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 *
 * @author thiago
 */
@Component("viewManager")
@Scope("prototype")
public class ViewManager {

    private final HashMultimap<String, Class<?>> viewMap; //NOPMD indirect
    private final BiMap<Class<?>, Button> buttonMap; //NOPMD indirect
    
    public static final int PARENT_VIEW_LIMIT = 16;
    public static final int CHILD_VIEW_LIMIT = 32;
    
    public ViewManager() {
        viewMap = HashMultimap.create(PARENT_VIEW_LIMIT, CHILD_VIEW_LIMIT);
        buttonMap = HashBiMap.create(PARENT_VIEW_LIMIT);
    }
        

    public final void addButtonToView(final Class<?> view, final Button button) {
        buttonMap.put(view, button);
    }
    
    public final Button getViewButton(final Class<?> view)
    {
        return buttonMap.get(view);
    }

    public final void addView(final Class<?> view) {
        viewMap.put(findParentView(view), view);
    }
    
    public final void removeView(final Class<?> view) {
        viewMap.remove(findParentView(view), view);
    }

    
    public final Iterator<Class<?>> getChildViews(final String parent) {
        return viewMap.get(parent).iterator();
    }
    
    public final Iterator<Class<?>> getParentViews() {
        final TreeMultimap<String, Class<?>> tmSorter = TreeMultimap.create(
                Ordering.usingToString(), Ordering.usingToString());
        tmSorter.putAll(viewMap);
        return tmSorter.get("ColtraneMain").iterator();
    }
    
    private static String findParentView(final Class<?> view) {
        String parentView;

        if (view.isAnnotationPresent(ChildView.class)) {
            Class annotationValue;
            annotationValue = view.getAnnotation(ChildView.class).value();
            parentView = annotationValue.getSimpleName();
        } else {
            parentView = "ColtraneMain";
        }
        return parentView;
    }
}
