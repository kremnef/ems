package ru.strela.ems.security.controller;

import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.StringWriter;
import java.security.Principal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 06.09.11
 * Time: 7:29
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class LoginLogoutController extends BaseController {
    private SessionRegistry sessionRegistry;

    @RequestMapping(method = RequestMethod.GET, value = "/accessDenied.do")
    public void accessDenied(ModelMap model, HttpServletRequest request) {
//        AccessDeniedException ex = (AccessDeniedException) request.getAttribute(AccessDeniedHandlerImpl.SPRING_SECURITY_ACCESS_DENIED_EXCEPTION_KEY);
        StringWriter sw = new StringWriter();
//        model.addAttribute("errorDetails", ex.getMessage());
//        ex.printStackTrace(new PrintWriter(sw));
        model.addAttribute("errorTrace", sw.toString());
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String printWelcome(ModelMap model, Principal principal) {

        String name = principal.getName(); //get logged in username
        model.addAttribute("username", name);
        return "hello";

    }

    @RequestMapping("/account/listActiveUsers.do")
    public void listActiveUsers(Model model) {
        Map<Object, Date> lastActivityDates = new HashMap<Object, Date>();
        for (Object principal : sessionRegistry.getAllPrincipals()) {
         // a principal may have multiple active sessions
         for(SessionInformation session :
            sessionRegistry.getAllSessions(principal, false)){ // no last activity stored

                if (lastActivityDates.get(principal) == null) {
                    lastActivityDates.put(principal, session.getLastRequest());
                } else { // check to see if this session is newer than the last stored
                    Date prevLastRequest = lastActivityDates.get(principal);
                    if (session.getLastRequest().after(prevLastRequest)) {
// update if so lastActivityDates.put(principal, session.getLastRequest());
                    }
                }
                model.addAttribute("activeUsers", lastActivityDates);
            }
        }
    }
}
