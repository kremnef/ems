package ru.strela.ems.security.dao;


//import org.springframework.security.core.userdetails.Role;

import ru.strela.ems.security.model.Role;

import java.util.List;


//public interface RoleDao extends TypifiedObjectDao {
public interface RoleDao extends CommonObjectDao{


    Role getRoleByName(String name);
    Role getRole(String login);
    List<Role> getAllRoles();
    void deleteRole(Role role);
    List findRoles(String[] descriptions);

}
