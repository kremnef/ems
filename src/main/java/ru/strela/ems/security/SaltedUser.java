package ru.strela.ems.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 28.09.11
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class SaltedUser extends User {

    private String salt;

    public SaltedUser(String username, String password,

                      boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired,
                      boolean accountNonLocked, List<GrantedAuthority> authorities, String salt) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired,
                accountNonLocked, authorities);
        this.salt = salt;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

}