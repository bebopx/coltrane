package com.bebopx.common.security;

import java.io.Serializable;

/**
 *
 * @author thiago
 */
public class User implements Serializable { //NOPMD - let's keep it simple

    /**
     * Class version, since this is a shared object.
     */
    private static final long serialVersionUID = 2L;

    /**
     * Login/username.
     */
    public String username;

    /**
     * Domain/instance.
     */
    public String domain;

    /**
     * Generic placeholder to be replaced.
     */
    public String credential;
    
    /**
     * User's fullname. Shall be replaced with something better.
     */
    public String fullname;
}
