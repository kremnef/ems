package ru.strela.ems.ecommerce.service.spring;


import ru.strela.ems.core.service.spring.TypifiedObjectServiceImpl;
import ru.strela.ems.ecommerce.dao.OrderDao;
import ru.strela.ems.ecommerce.model.Order;
import ru.strela.ems.ecommerce.service.OrderService;

import java.util.List;


//public class ProductServiceImpl  extends EmsObjectServiceImpl implements ProductService {
public class OrderServiceImpl extends TypifiedObjectServiceImpl implements OrderService {


    public OrderDao getOrderDao() {
        return (OrderDao) typifiedObjectDao;
    }


    public void setOrderDao(OrderDao orderDao) {
        setTypifiedObjectDao(orderDao);
    }


    public Order getOrder(int id) {
        return getOrderDao().getOrder(id);
    }



    public List getOrders() {
        return getOrderDao().getOrders();
    }



    public void deleteOrder(Order order) {
        getOrderDao().deleteOrder(order);
    }


    public List findOrders(String[] descriptions) {
        return getOrderDao().findOrders(descriptions);
    }

}
