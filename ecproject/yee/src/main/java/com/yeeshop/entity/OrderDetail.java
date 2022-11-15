package com.yeeshop.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="OrderDetails")
@Getter @Setter
public class OrderDetail {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    Double unitprice;
    Integer quantity;
    Double discount;

    @ManyToOne
    @JoinColumn(name="orderId")
    Order order;

    @ManyToOne
    @JoinColumn(name="productId")
    Product product;


}
