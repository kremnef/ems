package ru.strela.ems.auth;


import org.apache.cocoon.auth.ApplicationStore;
import org.apache.cocoon.auth.SecurityHandler;
import org.apache.cocoon.auth.User;
import org.apache.cocoon.auth.impl.StandardApplication;
import org.apache.commons.logging.Log;

import java.util.Map;


/**
 * User: hobal
 * Date: 31.05.2010
 * Time: 1:33:52
 */
public class EmsApplication extends StandardApplication {


    public EmsApplication() {
        super();
        ////log.info("EmsApplication.EmsApplication");
    }


    @Override
    public Log getLogger() {
        ////log.info("EmsApplication.getLogger");
        return super.getLogger();
    }


    @Override
    public void setLogger(Log l) {
        ////log.info("EmsApplication.setLogger");
        super.setLogger(l);
    }


    @Override
    public void setSecurityHandler(SecurityHandler h) {
        ////log.info("EmsApplication.setSecurityHandler");
        super.setSecurityHandler(h);
    }


    @Override
    public void setApplicationStore(ApplicationStore s) {
        ////log.info("EmsApplication.setApplicationStore");
        super.setApplicationStore(s);
    }


    @Override
    public void setAttributes(Map map) {
        ////log.info("EmsApplication.setAttributes");
        super.setAttributes(map);
    }


    @Override
    public SecurityHandler getSecurityHandler() {
        ////log.info("EmsApplication.getSecurityHandler");
        return super.getSecurityHandler();
    }


    @Override
    public ApplicationStore getApplicationStore() {
        ////log.info("EmsApplication.getApplicationStore");
        return super.getApplicationStore();
    }


    @Override
    public void setAttribute(String key, Object value) {
        ////log.info("EmsApplication.setAttribute");
        super.setAttribute(key, value);
    }


    @Override
    public void removeAttribute(String key) {
        ////log.info("EmsApplication.removeAttribute");
        super.removeAttribute(key);
    }


    @Override
    public Object getAttribute(String key) {
        ////log.info("EmsApplication.getAttribute");
        return super.getAttribute(key);
    }


    @Override
    public void userDidLogin(User user, Map context) {
        ////log.info("EmsApplication.userDidLogin");
        super.userDidLogin(user, context);
    }


    @Override
    public void userWillLogout(User user, Map context) {
        ////log.info("EmsApplication.userWillLogout");
        super.userWillLogout(user, context);
    }


    @Override
    public void userIsAccessing(User user) {
        ////log.info("EmsApplication.userIsAccessing");
        super.userIsAccessing(user);
    }
}
