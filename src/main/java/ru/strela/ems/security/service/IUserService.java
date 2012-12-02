package ru.strela.ems.security.service;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 28.09.11
 * Time: 12:43
 * To change this template use File | Settings | File Templates.
 */
public interface IUserService {
//    @PreAuthorize("hasRole('ROLE_USER')")
public void changePassword(String username, String password);
}
