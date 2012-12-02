package ru.strela.ems.security.model;

/**
 * Created by IntelliJ IDEA.
 * User: andrejkremnev
 * Date: 28.11.11
 * Time: 14:34
 * To change this template use File | Settings | File Templates.
 */
public class Group {


    int groupId;
    String groupName;


    public Group(String groupName) {
        this.groupName = groupName;

    }

    public Group() {
    }


    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
