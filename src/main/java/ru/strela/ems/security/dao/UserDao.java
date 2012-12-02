package ru.strela.ems.security.dao;


import org.springframework.security.core.userdetails.User;

import java.util.List;


//public interface UserDao extends TypifiedObjectDao {
public interface UserDao extends CommonObjectDao{


//    User getUser(Integer userId);
    User getUserByName(String name);
    User getUser(String login);
    List getUsers();
    List getGroups();
    List getRoles();
//    int getIdByLogin(String login);
    void deleteUser(User user);
    List findUsers(String[] descriptions);

}
