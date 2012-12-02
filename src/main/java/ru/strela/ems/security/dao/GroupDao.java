package ru.strela.ems.security.dao;


//import org.springframework.security.core.userdetails.Group;

import ru.strela.ems.security.model.Group;
import ru.strela.ems.security.model.Role;

import java.util.List;


//public interface GroupDao extends TypifiedObjectDao {
public interface GroupDao extends CommonObjectDao{


    Group getGroupByName(String name);
    Group getGroup(String login);
    List<Group> getAllGroups();
    List<Role> getRoles();
    void deleteGroup(Group group);
    List findGroups(String[] descriptions);

}
