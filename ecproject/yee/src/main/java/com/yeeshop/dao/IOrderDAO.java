package com.yeeshop.dao;

import java.util.List;

import com.yeeshop.entity.Order;
import com.yeeshop.entity.OrderDetail;
import com.yeeshop.entity.Product;
import com.yeeshop.entity.User;

public interface IOrderDAO {
    Order findById(Integer id);

	List<Order> findAll();

	Order create(Order order);

	void update(Order order);

	Order delete(Integer id);

	void create(Order order, List<OrderDetail> details);

	List<Order> findByUser(User user);

	List<Product> findItemsByUser(User user);
}
