package com.bebopx.coltrane.bridge;

import java.util.HashSet;
import java.util.Set;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bebopx.common.security.LocalPrincipal;
import com.bebopx.common.security.User;
import java.io.Serializable;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thiago
 */
@Component
//@Loggable
public class LocalRealm extends AuthorizingRealm implements Serializable {
    
    /**
     * Class version, since this is a serializable object.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Autowired logger.
     */
    private transient Logger logger = LoggerFactory.getLogger(LocalRealm.class); //NOPMD - IoC logger

    /**
     *
     * @param token Shiro's token.
     * @return AuthenticationInfo for Shiro procedures.
     */
    @Override
    protected final AuthenticationInfo doGetAuthenticationInfo(
            final AuthenticationToken token) {

        User localUser = new User(); // NOPMD - gc calling
        SimpleAuthenticationInfo localAuthInfo;
        LocalPrincipal localPrincipal;

        logger.info("entrou");
        final UsernamePasswordToken userPass = (UsernamePasswordToken) token;
        if ("user".equals(userPass.getUsername())) {
            logger.info("achou");
            final Set<String> roles = new HashSet<String>() {
            };
            roles.add("user");

            localUser.username = "user";
            localPrincipal = new LocalPrincipal(localUser, roles);

            localAuthInfo = new SimpleAuthenticationInfo(
                    localPrincipal, userPass.getCredentials(), this.getName());
        } else {
            localAuthInfo = null; //NOPMD - shiro expects null
        }

        localPrincipal = null; // NOPMD - gc calling
        localUser = null; // NOPMD - gc calling
        return localAuthInfo;
    }

    /**
     *
     * @param principals PrincipalCollection of user's.
     * @return Shiro authorization information.
     */
    @Override
    protected final AuthorizationInfo doGetAuthorizationInfo(
            final PrincipalCollection principals) {
        return new SimpleAuthorizationInfo(
                ((LocalPrincipal) principals.getPrimaryPrincipal()).getRoles());
    }
}
