package com.yeeshop.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Products")

@Getter @Setter
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    Double unitPrice;
    String image;
    
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    Date productDate;

    Boolean available;
	//Integer categoryId;
	Integer quantity;
	String description;
	Double discount;
	Integer viewCount;
	Boolean special;

    @ManyToOne
    @JoinColumn(name="categoryId")
    Category category;

    @OneToMany(mappedBy="product")
    List<OrderDetail> orderDetails;

    
}
