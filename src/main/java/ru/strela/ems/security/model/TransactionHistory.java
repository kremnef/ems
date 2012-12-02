package ru.strela.ems.security.model;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.Date;


/**
 * User: andrejkremnev
 * Date: 12.12.11
 * Time: 20:40
 */
public class TransactionHistory {
    private final static Logger log = LoggerFactory.getLogger(TransactionHistory.class);

    private int id;
    private int objectId;
    private int userId;
    private String userAction;
    private Date date;
    private String ipAddress;
    private Customer customer;
    private TypifiedObject object;


    public TransactionHistory(TypifiedObject object, Customer user, String userAction, Date date) {
        //log.info(" ##New Class of TransHistory");
        this.userAction = userAction;
        this.date = date;
        setObject(object);
        setCustomer(user);
    }


    public TransactionHistory() {
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public int getObjectId() {
        return objectId;
    }


    public void setObjectId(int objectId) {
        this.objectId = objectId;
    }


    public int getUserId() {
        return userId;
    }


    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getUserAction() {
        return userAction;
    }


    public void setUserAction(String userAction) {
        this.userAction = userAction;
    }


    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }


    public String getIpAddress() {
      String remoteAddress = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest().getRemoteAddr();

        this.ipAddress = remoteAddress;
        return ipAddress;
    }


    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }


    public Customer getCustomer() {
        return customer;
    }


    public void setCustomer(Customer customer) {
        this.customer = customer;
        this.userId = customer.getId();
    }


    public TypifiedObject getObject() {
        return object;
    }


    public void setObject(TypifiedObject object) {
        this.object = object;
        this.objectId = object.getId();
    }

}
