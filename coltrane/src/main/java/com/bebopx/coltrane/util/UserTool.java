package com.bebopx.coltrane.util;

import com.bebopx.common.security.LocalPrincipal;
import com.bebopx.common.security.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author thiago
 */
public class UserTool {

    private UserTool() {
    }

    public static User getCurrentUser() {
        PrincipalCollection currentCollection;
        currentCollection = SecurityUtils.getSubject().getPrincipals();

        LocalPrincipal currentPrincipal;
        currentPrincipal = (LocalPrincipal) currentCollection.getPrimaryPrincipal();

        return currentPrincipal.getUser();
    }
}
