package com.bebopx.common.definitions;

/**
 *
 * @author thiago
 */
public final class CommonStaticDefinitions {

    private CommonStaticDefinitions() {
    }

    public final static String ApplicationName = "bebopX Coltrane";
    private final static String StyledApplicationName = "<span>bebopX</span><br/>Coltrane";

    public static String getSimpleApplicationName() {

        return ApplicationName;
    }
    
    public static String getFullApplicationName() {
        return StyledApplicationName;
    }
}
