package ru.strela.ems.ecommerce.service;


import ru.strela.ems.core.service.TypifiedObjectService;
import ru.strela.ems.ecommerce.dao.OrderDao;
import ru.strela.ems.ecommerce.model.Order;

import java.util.List;


public interface OrderService extends TypifiedObjectService {


    OrderDao getOrderDao();
    void setOrderDao(OrderDao orderDao);
    Order getOrder(int id);
    List getOrders();

    void deleteOrder(Order order);
    List findOrders(String[] descriptions);
    
}