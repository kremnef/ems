package ru.strela.ems.security.service;


import ru.strela.ems.security.dao.CustomerDao;
import ru.strela.ems.security.model.Customer;

import java.util.List;


//public interface CustomerService extends TypifiedObjectService {
public interface CustomerService {


    CustomerDao getCustomerDao();
    void setCustomerDao(CustomerDao customerDao);
    Customer getCustomer(int id);
    Customer getCustomerByLogin(String login);
    List<Customer> getEntities();
    List<Customer> getCustomers();

    int getChildrenCount(int id);
    List<Customer> getChildren(int parentId);
    List<Customer> getChildren(int parentId, int start, int quantity, String sortName, boolean desc);

    void deleteCustomer(Customer customer);
    List findCustomers(String[] descriptions);
    
}