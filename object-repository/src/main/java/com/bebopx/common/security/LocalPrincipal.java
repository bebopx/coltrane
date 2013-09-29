package com.bebopx.common.security;

import java.io.Serializable;
import java.util.Set;

/**
 *
 * @author thiago
 */
public class LocalPrincipal implements Serializable {

    /**
     * Class version, since this is a shared object.
     */
    private static final long serialVersionUID = 1L;
    /**
     * User object contained in this principal.
     */
    private User localUser; //NOPMD - not public, but not transient nor static
    /**
     * Roles assigned for the local User object.
     */
    private Set<String> localRoles; //NOPMD - see above

    /**
     *
     * @param user User object of this principal.
     * @param roles Roles set of this principal.
     */
    public LocalPrincipal(final User user, final Set<String> roles) {
        this.localUser = user;
        this.localRoles = roles;
    }

    /**
     *
     * @return Returns a Set with the user's roles.
     */
    public final Set<String> getRoles() {
        return localRoles;
    }
}
