package ru.strela.ems.security.model;

//import ru.strela.ems.cms.entities.management.SystemUser;

import org.springframework.security.core.userdetails.User;

import java.util.List;
import java.util.ArrayList;

//public class Customer extends BaseBusinessObject {
public class Customer {
//public class Customer extends TypifiedObject implements java.io.Serializable {

    private int id;
    private String creditStatus;
    private String accountStatus;
    private String firstName;
    private String lastName;
    private String email;
    private String login;

    private List submittedOrders = null;

    private User user;


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Customer() {

        super();
        submittedOrders = new ArrayList();
    }


//  public void setPassword(String password) {
//    this.password = password;
//  }

//    public String getPassword() {
//        return this.getPassword();
//    }

    public void setCreditStatus(String creditStatus) {
        this.creditStatus = creditStatus;
    }

    public String getCreditStatus() {
        return creditStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getAccountStatus() {
        return accountStatus;
    }


    /*public String getSystemUserName() {
        return this.su.getUserName();
    }

      public SystemUser getSystemUser() {
        return this.su;
    }*/

    /**
     * set systemUserName for this Customer
     */
    /*public void setSystemUserName(String systemUserName) {
        this.systemUserName = systemUserName;
    }*/
    public void setSubmittedOrders(List aList) {
        this.submittedOrders = aList;
    }

    public List getSubmittedOrders() {
        return submittedOrders;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append("First: " + getFirstName());
        buf.append(" Last: " + getLastName());
        return buf.toString();
    }


}