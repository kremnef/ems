package ru.strela.ems.ecommerce.dao.hibernate;


import org.apache.cocoon.environment.ObjectModelHelper;
import org.apache.cocoon.environment.Request;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.strela.ems.core.dao.hibernate.HibernateUtil;
import ru.strela.ems.core.dao.hibernate.TypifiedObjectDaoImpl;
import ru.strela.ems.core.model.TypifiedObject;
import ru.strela.ems.ecommerce.dao.OrderDao;
import ru.strela.ems.ecommerce.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


//public class CustomerDaoImpl extends EmsObjectDaoImpl implements CustomerDao {
public class OrderDaoImpl extends TypifiedObjectDaoImpl implements OrderDao {

   private final static Logger log = LoggerFactory.getLogger(OrderDaoImpl.class);
    public OrderDaoImpl() {
        super();
    }


    public Class getEntityClass() {
        return Order.class;
    }


    public Order getOrder(int id) {
        Session session = getCurrentSession();
        Order order = (Order) session.load(Order.class, id);
        closeSession();
        return order;
    }


    public Order getOrderByName(String orderName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Order order = null;

        try {
            tx = session.beginTransaction();
            List list = session.createQuery("from Order where name = '" + orderName + "'").list();
            if (list.size() > 0) {
                order = (Order) list.get(0);
            }
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }
        return order;
    }


    public List getOrders() {
        Session session = getCurrentSession();
        List list = session.createCriteria(Order.class).list();
        closeSession();
        return list;
    }


    @Override
    protected TypifiedObject saveObject(TypifiedObject typifiedObject) {
        Order order = (Order) typifiedObject;
        return super.saveObject(order);
    }


    public void deleteOrder(Order order) {
        super.delete(order);
    }


    public List findOrders(final String[] descriptions) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        log.warn("openSession");
        Transaction tx = null;
        Criteria criteria = null;
        Criterion criterion = null;
        List list = new ArrayList();
        try {
            tx = session.beginTransaction();
            criteria = session.createCriteria(Order.class);

            for (int i = 0; i < descriptions.length; i++) {
                String description = descriptions[i].trim();
                if (description.length() > 0) {
                    if (criterion == null) {
                        criterion = Restrictions.like("description", description, MatchMode.ANYWHERE);
                    } else {
                        criterion = Restrictions.or(criterion, Restrictions.like("description", description, MatchMode.ANYWHERE));
                    }
                    criterion = Restrictions.or(criterion, Restrictions.like("code", description, MatchMode.ANYWHERE));
                }
            }
            if (criterion != null) {
                criteria.add(criterion);
                list = criteria.list();
            }
            tx.commit();
            session.close(); log.warn("closeSession");
        } catch (HibernateException he) {
            if (tx != null) tx.rollback();
            throw he;
        }

        /*} else {
            return new ArrayList();
        }*/
        return list;
    }


    public void showOrders(Map model, Object obj) {
        Request request = ObjectModelHelper.getRequest(model);
//        checkLocale(request);
    }


}
