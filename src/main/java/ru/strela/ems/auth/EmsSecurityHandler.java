package ru.strela.ems.auth;


import org.apache.cocoon.auth.impl.SimpleSecurityHandler;
import org.apache.cocoon.components.modules.input.InputModule;

/**
 * User: hobal
 * Date: 31.05.2010
 * Time: 1:55:19
 */
public class EmsSecurityHandler extends SimpleSecurityHandler {

/*
    public EmsSecurityHandler() {
        super();
        ////log.info("EmsSecurityHandler.EmsSecurityHandler");
    }


    @Override
    public Log getLogger() {
        ////log.info("EmsSecurityHandler.getLogger");
        return super.getLogger();
    }


    @Override
    public void setLogger(Log l) {
        ////log.info("EmsSecurityHandler.setLogger");
        super.setLogger(l);
    }


    @Override
    public void setAnonymousName(String anonName) {
        ////log.info("EmsSecurityHandler.setAnonymousName");
        super.setAnonymousName(anonName);
    }


    @Override
    public void setAnonymousPassword(String anonPass) {
        ////log.info("EmsSecurityHandler.setAnonymousPassword");
        super.setAnonymousPassword(anonPass);
    }


    @Override
    public void setSupportAnonymousUser(boolean supportAnonUser) {
        ////log.info("EmsSecurityHandler.setSupportAnonymousUser");
        super.setSupportAnonymousUser(supportAnonUser);
    }


    @Override
    public void setBeanName(String name) {
        ////log.info("EmsSecurityHandler.setBeanName");
        super.setBeanName(name);
    }


    @Override
    public String getId() {
        ////log.info("EmsSecurityHandler.getId");
        return super.getId();
    }


    @Override
    public void setCustomer(Properties p) {
        ////log.info("EmsSecurityHandler.setCustomer");
        super.setCustomer(p);
    }


    @Override
    public User login(Map loginContext) throws AuthenticationException {

        final String name = (String)loginContext.get(ApplicationManager.LOGIN_CONTEXT_USERNAME_KEY);
        if (name == null) {
            throw new AuthenticationException("Required user name property is missing for login.");
        }

        WebApplicationContext currentWebApplicationContext = WebAppContextUtils.getCurrentWebApplicationContext();
        UserService userService = (UserService) currentWebApplicationContext.getBean("userService");
        org.springframework.security.core.userdetails.User emsUser = userService.getUserByName(name);
        if (emsUser == null) {
            return null;
        }
        final String password = (String)loginContext.get(ApplicationManager.LOGIN_CONTEXT_PASSWORD_KEY);

        // compare password
        if ( !StringUtils.equals(password, emsUser.getPassword()) ) {
            return null;
        }

        final User user = new StandardUser(name);
        user.setAttribute("password", password);
//        user.setAttribute("id", emsUser.getId());
        return user;
    }


    @Override
    public void logout(Map logoutContext, User user) {
        ////log.info("EmsSecurityHandler.logout");
        super.logout(logoutContext, user);
    }*/
}
