package ru.strela.ems.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 11.09.11
 * Time: 13:29
 * To change this template use File | Settings | File Templates.
 */
public class BaseController {
    private final static Logger log = LoggerFactory.getLogger(BaseController.class);
    protected Authentication getAuthentication() {
        //log.info("BaseController-getAuthentication()");
        return SecurityContextHolder.getContext().getAuthentication();
    }
    @ModelAttribute("showLoginLink")
    public boolean getShowLoginLink() {
        for (GrantedAuthority authority : getAuthentication().getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_USER")) {
                return false;
            }
        }
        return true;
    }


}
