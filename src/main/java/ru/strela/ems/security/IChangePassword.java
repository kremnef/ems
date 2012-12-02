package ru.strela.ems.security;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 02.09.11
 * Time: 23:33
 * To change this template use File | Settings | File Templates.
 */
public interface IChangePassword extends UserDetailsService {
    void changePassword(String username, String password);
}