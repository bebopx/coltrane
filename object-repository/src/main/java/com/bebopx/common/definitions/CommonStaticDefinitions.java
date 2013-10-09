package com.bebopx.common.definitions;

/**
 *
 * @author thiago
 */
public final class CommonStaticDefinitions {

    private CommonStaticDefinitions() {
    }

    public final static String ApplicationName = "bebopX Coltrane";
    private final static String StyledApplicationName = "<span>bebopX</span> Coltrane";

    public static String getSimpleApplicationName() {

        String simpleApplicationName;
        simpleApplicationName = StyledApplicationName.replaceAll("\\<.*?\\>", "");
        return simpleApplicationName;
    }
    
    public static String getFullApplicationName() {
        return StyledApplicationName;
    }
}
