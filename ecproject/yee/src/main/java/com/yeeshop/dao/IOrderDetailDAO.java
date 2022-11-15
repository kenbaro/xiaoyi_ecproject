package com.yeeshop.dao;

import java.util.List;

import com.yeeshop.entity.Order;
import com.yeeshop.entity.OrderDetail;

public interface IOrderDetailDAO {
    
    OrderDetail findById(Integer id);

	List<OrderDetail> findAll();

	OrderDetail create(OrderDetail entity);

	void update(OrderDetail entity);

	OrderDetail delete(Integer id);

	List<OrderDetail> findByOrder(Order order);
}
