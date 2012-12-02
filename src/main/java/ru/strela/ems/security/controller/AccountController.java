package ru.strela.ems.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 01.09.11
 * Time: 1:49
 * To change this template use File | Settings | File Templates.
 */
public class AccountController {

    /*@Autowired
    private IChangePassword changePasswordDao;*/
    @Autowired
    private UserDetailsManager userDetailsManager;

    @RequestMapping(value = "/ems/security/changePassword", method = RequestMethod.GET)
    public void showChangePasswordPage() {
    }

    @RequestMapping(value = "/ems/security/changePassword", method = RequestMethod.POST)
/*
    public String submitChangePasswordPage(@RequestParam("password") String newPassword) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.toString();
        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        }
        changePasswordDao.changePassword(username, newPassword);
        SecurityContextHolder.clearContext();
        return "redirect:login";
    }
*/
    public String submitChangePasswordPage(@RequestParam("oldpassword") String oldPassword,
                                           @RequestParam("password") String newPassword) {
        userDetailsManager.changePassword(oldPassword, newPassword);
        SecurityContextHolder.clearContext();
        return "redirect:/ems/security/User-list";
    }

}
