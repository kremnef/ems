package ru.strela.ems.ecommerce.model;


import ru.strela.ems.security.model.Customer;
import ru.strela.ems.core.model.TypifiedObject;

import java.util.Date;
import java.util.Iterator;
import java.util.List;


/**
 * The Order Business Object, which represents a purchase order that a customer
 * has or is about to place.
 */
public class Order extends TypifiedObject implements java.io.Serializable {


    //public class Order extends TypifiedObject implements java.io.Serializable {
//    private Integer id;
    // A list of line items for the order
//    private List OrderItem;
    private List OrderItem;
    // The customer who placed the order
    private Customer customer;
    // The current price of the order
    private double totalPrice;
    // The id of the customer
    private int customerId;
    // Whether the order is inprocess, shipped, canceled, etc...
    private String orderStatus;

    // The date and time that the order was received
    private Date submittedDate;

    //private String orderNumber;
    private String orderNumber = "Z-1/";
    private String orderType = "product";

//    private org.jdom.Document item;

    // Whether the order is inprocess, shipped, canceled, etc...
    private String orderInvoice;


    /**
     * Default NoArg Constructor
     */
    public Order() {
        super();
        // Initialize the line items as a linked list to keep them in order
//        OrderItem = new LinkedList();
//        java.util.Vector iii = new java.util.Vector();
//        this.OrderItem = new java.util.Vector();


    }


    /**
     * Additional constructor that takes the neccessary arguments to initialize
     */
//    public Order(int custId, String orderStatus,
//                   Timestamp submittedDate, double price) {
////    this.setId(id);
//        this.setCustomerId(custId);
//        this.setOrderStatus(orderStatus);
//        this.setSubmittedDate(submittedDate);
//        this.setTotalPrice(price);
//    }


//    public void setCustomer(Customer owner) {
//        this.customer = owner;
//    }

//    public Customer getCustomer() {
//        return this.customer;
//    }
    public double getTotalPrice() {
        return this.totalPrice;
    }


    public void setTotalPrice(double price) {
        this.totalPrice = price;
    }


    //    public void setOrderItem(List OrderItem) {
    public void setOrderItem(List orderItem) {
        this.OrderItem = orderItem;
    }

//    public List getOrderItem() {
//        return this.OrderItem;
//    }


    /**
     * getOrderItem
     *
     * @return
     */
    public List getOrderItem() {
        return this.OrderItem;
    }


    /**
     * addLineItem
     *
     * @param orderItem
     */
    public void addOrderItem(OrderItem orderItem) {
        this.OrderItem.add(orderItem);
    }


    /**
     * removeLineItem
     *
     * @param orderItem
     */
    public void removeOrderItem(OrderItem orderItem) {
        this.OrderItem.remove(orderItem);
    }


    /**
     * setCustomerId
     *
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }


    public int getCustomerId() {
        return this.customerId;
    }


    /**
     * setOrderStatus
     *
     * @param orderStatus
     */
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }


    /**
     * getOrderStatus
     *
     * @return
     */
    public String getOrderStatus() {
        return this.orderStatus;
    }


    /**
     * getOrderInvoice
     *
     * @return
     */
    public String getOrderInvoice() {
        return this.orderInvoice;
    }


    /**
     * setOrderInvoice
     *
     * @param orderInvoice
     */
    public void setOrderInvoice(String orderInvoice) {
        this.orderInvoice = orderInvoice;
    }


    /**
     * setOrderNumber
     *
     * @param orderNumber
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }


    public String getOrderNumber() {
        return this.orderNumber;
    }


    /**
     * setOrderType
     *
     * @param orderType
     */
    public void setOrderType(String orderType) {
        this.orderType = orderType;

    }


    public String getOrderType() {
        return this.orderType;
    }


    public void setSubmittedDate(Date submittedDate) {
        this.submittedDate = submittedDate;
    }


    public Date getSubmittedDate() {
        return this.submittedDate;
    }


    private void recalculatePrice() {
        double totalPrice = 0.0;

        if (getOrderItem() != null) {
            Iterator iter = getOrderItem().iterator();
            while (iter.hasNext()) {
                // Get the price for the next line item and make sure it's not null
                double lineItemPrice = ((OrderItem) iter.next()).getUnitPrice();
                // Check for an invalid lineItem. If found, return null right here
                if (lineItemPrice > 0) {
                    totalPrice += lineItemPrice;
                }
            }
            // Set the price for the order from the calcualted value
            setTotalPrice(totalPrice);
        }
    }


}