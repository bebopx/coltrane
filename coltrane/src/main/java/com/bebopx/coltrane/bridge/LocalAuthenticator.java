package com.bebopx.coltrane.bridge;

import com.vaadin.ui.UI;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author thiago
 */
public class LocalAuthenticator {

    private final transient Logger logger = LoggerFactory.getLogger(
            LocalAuthenticator.class);
    private final Subject currentUser;
    private final UsernamePasswordToken token;


    /**
     *
     * @param username
     * @param password
     */
    public LocalAuthenticator(final String username, final String password) {
        token = new UsernamePasswordToken(username, password);
        token.setRememberMe(false);
        currentUser = SecurityUtils.getSubject();
    }

    /**
     *
     * @return
     */
    public boolean doAuthenticate() {
        try {
            logger.info("auth inicio");
            currentUser.login(token);
            logger.info("ok");
            return true;
        }
        catch (UnknownAccountException uae) {
            logger.error("uae");
        }
        catch (IncorrectCredentialsException ice) {
            logger.error("ice");
        }
        catch (LockedAccountException lae) {
            logger.error("lae");
        }
        catch (ExcessiveAttemptsException eae) {
            logger.error("eae");
        }
        catch (AuthenticationException ae) {
            logger.error("ae");
        }
        finally {
        }

        return false;
    }

    /**
     * Executes a logout from Shiro's SecurityUtils.
     */
    public static void doLogout() {
        SecurityUtils.getSubject().logout();
        UI.getCurrent().close();
    }
}
