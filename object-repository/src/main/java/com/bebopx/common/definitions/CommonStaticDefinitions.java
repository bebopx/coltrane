package com.bebopx.common.definitions;

/**
 *
 * @author thiago
 */
public final class CommonStaticDefinitions {

    private CommonStaticDefinitions() {
    }
    private final static String ApplicationName = "<span>bebopX</span> Coltrane";

    public static String getSimpleApplicationName() {

        String simpleApplicationName;
        simpleApplicationName = ApplicationName.replaceAll("\\<.*?\\>", "");
        return simpleApplicationName;
    }
    
    public static String getFullApplicationName() {
        return ApplicationName;
    }
}
