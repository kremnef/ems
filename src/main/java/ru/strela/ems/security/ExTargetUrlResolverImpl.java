package ru.strela.ems.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.security.web.PortResolverImpl;


import javax.servlet.http.HttpServletRequest;

/**
 * Created with IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 26.07.13
 * Time: 17:11
 * To change this template use File | Settings | File Templates.
 */
public class ExTargetUrlResolverImpl extends PortResolverImpl {

    private String url;

//    @Override
    public String determineTargetUrl(SavedRequest savedRequest, HttpServletRequest request,
                                     Authentication auth) {
        String sessionId = request.getSession().getId();
        return url + ";jsessionid=" + sessionId;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}