package com.yeeshop.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yeeshop.dao.IOrderDAO;
import com.yeeshop.entity.Order;
import com.yeeshop.entity.OrderDetail;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;

@Transactional @Repository
public class OrderDAOImpl implements IOrderDAO {

    @Autowired
    SessionFactory factory;
    @Override
    public Order findById(Integer id) {
        Session session=factory.getCurrentSession();
		Order order=session.find(Order.class, id);
		return order;
    }

    @Override
    public List<Order> findAll() {
        String hql="FROM Order";
		Session session=factory.getCurrentSession();
		TypedQuery<Order> query=session.createQuery(hql,Order.class);
		List<Order> orders=query.getResultList();
		return orders;
    }

    @Override
    public Order create(Order order) {
        Session session=factory.getCurrentSession();
		session.save(order);
		return order;
    }

    @Override
    public void update(Order order) {
        Session session=factory.getCurrentSession();
		session.update(order);
    }

    @Override
    public Order delete(Integer id) {
        Session session=factory.getCurrentSession();
		Order entity=session.find(Order.class, id);
		session.delete(entity);
		return entity;
    }

    @Override
    public void create(Order order, List<OrderDetail> details) {
        Session session=factory.getCurrentSession();
		session.save(order);
		for(OrderDetail detail : details) {
			session.save(detail);

		}
        
    }

    @Override
    public List<Order> findByUser(User user) {
        String hql="FROM Order o WHERE o.user.id=:uid ORDER BY o.orderDate DESC";
		Session session=factory.getCurrentSession();
		TypedQuery<Order> query=session.createQuery(hql,Order.class);
		query.setParameter("uid", user.getId());
		List<Order> list=query.getResultList();
		return list;
    }

    @Override
    public List<Product> findItemsByUser(User user) {
        String hql="SELECT DISTINCT d.product FROM OrderDetail d WHERE d.order.user.id=:uid";
		Session session=factory.getCurrentSession();
		TypedQuery<Product> query=session.createQuery(hql,Product.class);
		query.setParameter("uid", user.getId());
		List<Product> list=query.getResultList();
		return list;
    }
    
}
