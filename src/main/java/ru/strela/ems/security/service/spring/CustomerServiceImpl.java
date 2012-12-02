package ru.strela.ems.security.service.spring;


import ru.strela.ems.security.dao.CustomerDao;
import ru.strela.ems.security.model.Customer;
import ru.strela.ems.security.service.CustomerService;

import java.util.List;


//public class CustomerServiceImpl  extends EmsObjectServiceImpl implements CustomerService {
//public class CustomerServiceImpl extends TypifiedObjectServiceImpl implements CustomerService {
public class CustomerServiceImpl implements CustomerService {

    private CustomerDao customerDao;
    Customer customer = new Customer();
    Class entityClass  = customer.getClass();

    public int getChildrenCount(int id) {
        return customerDao.getChildrenCount(entityClass, id);
    }

    public List<Customer> getChildren(int parentId) {
        return getChildren(parentId, 0, 0, "", false);
    }

    public List<Customer> getChildren(int parentId, int start, int quantity, String sortName, boolean desc) {
        return customerDao.getObjects(entityClass, start, quantity, sortName, desc);
    }

    public CustomerDao getCustomerDao() {
        return customerDao;
    }


    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getEntities() {
     return customerDao.getCustomers();
    }


    public List<Customer> getCustomers() {
     return customerDao.getCustomers();
    }

    public Customer getCustomer(int id) {
        return customerDao.getCustomer(id);
    }

    public  Customer getCustomerByLogin(String login) {
        return customerDao.getCustomerByLogin(login);
    }


    public Customer saveCustomer(Customer customer) {
        return customerDao.saveCustomer(customer);
    }



    public void deleteCustomer(Customer document) {
        customerDao.deleteCustomer(document);
    }


    public List findCustomers(String[] descriptions) {
        return customerDao.findCustomers(descriptions);
    }


    
}
