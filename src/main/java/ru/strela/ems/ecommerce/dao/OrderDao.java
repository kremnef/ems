package ru.strela.ems.ecommerce.dao;


import ru.strela.ems.core.dao.TypifiedObjectDao;
import ru.strela.ems.ecommerce.model.Order;

import java.util.List;


//public interface LanguageDao extends EmsObjectDao {
public interface OrderDao extends TypifiedObjectDao {


    Order getOrder(int id);
    Order getOrderByName(String orderName);
    List getOrders();
//    List<Order> getVisibleOrders();
    void deleteOrder(Order order);
    List findOrders(String[] descriptions);

}