package com.yeeshop.dao.impl;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yeeshop.dao.IProductDAO;
import com.yeeshop.entity.Product;

@Transactional @Repository
public class ProductDAOImpl implements IProductDAO {

    @Autowired 
    SessionFactory factory;
    @Override
    public List<Product> findAll() {
        String hql="From Product";
        Session session=factory.getCurrentSession();
        TypedQuery<Product> query= session.createQuery(hql,Product.class);
        List<Product> products=query.getResultList();
        return products;
    }

    @Override
    public List<Product> findSpecialProducts() {
        String hql="From Product p where p.special=1";
        Session session= factory.getCurrentSession();
        TypedQuery<Product> query= session.createQuery(hql, Product.class);
        List<Product> products= query.getResultList();
        return products;
    }

    @Override
    public Product findById(Integer id) {
        Session session= factory.getCurrentSession();
        Product product=session.find(Product.class, id);
        return product;
    }

    @Override
    public List<Product> findByCategoryId(Integer cateId) {
        String hql="FROM Product p WHERE p.category.id=:cid";
		Session session=factory.getCurrentSession();
		TypedQuery<Product> query=session.createQuery(hql,Product.class);
		query.setParameter("cid", cateId);
		List<Product> products=query.getResultList();
		return products;
    }

    @Override
    public List<Product> findLastestProducts() {
        String hql="from Product order by id desc";
        Session session= factory.getCurrentSession();
        TypedQuery<Product> query= session.createQuery(hql, Product.class).setMaxResults(8);
        List<Product> products = query.getResultList();
        return products;
    }

    @Override
    public List<Product> findTopViewProducts() {
        String hql="from Product order by viewCount desc ";
        Session session= factory.getCurrentSession();
        TypedQuery<Product> query = session.createQuery(hql,Product.class).setMaxResults(8);
        List<Product> products= query.getResultList();
        return products;
    }

    @Override
    public List<Product> findOnsaleProducts() {
        String hql="from Product p where p.discount > 0 order by p.id desc ";
        Session session= factory.getCurrentSession();
        TypedQuery<Product> query = session.createQuery(hql, Product.class);
        List<Product> products= query.getResultList();
        return products;
    }

    @Override
    public List<Product> findHotSaleProduct() {
        String hql="from Product p where p.discount>0 order by p.discount desc";
        Session session = factory.getCurrentSession();
        TypedQuery<Product> query= session.createQuery(hql,Product.class).setMaxResults(1);
        List<Product> products= query.getResultList();
        return products;
    }
    @Override
    public void update(Product product) {
        Session session=factory.getCurrentSession();
		session.update(product);
    }

    @Override
    public List<Product> findbyIds(String ids) {
        String hql="FROM Product p WHERE p.id IN ("+ids+")";
		Session session=factory.getCurrentSession();
		TypedQuery<Product> query=session.createQuery(hql,Product.class);
		List<Product> products=query.getResultList();
		return products;
    }

    @Override
    public Product create(Product entity) {
        Session session=factory.getCurrentSession();
		session.save(entity);
		return null;
    }

    @Override
    public Product delete(Integer id) {
        Session session=factory.getCurrentSession();
		Product entity=session.find(Product.class, id);
		session.delete(entity);
		return entity;
    }

    @Override
    public List<Product> findSpecialPrice() {
        String hql="from Product order by unitPrice ASC ";
        Session session= factory.getCurrentSession();
        TypedQuery<Product> query = session.createQuery(hql,Product.class).setMaxResults(8);
        List<Product> products= query.getResultList();
        return products;
    }

    @Override
    public List<Product> findByKeywords(String keywords) {
        String hql="FROM Product p WHERE p.name LIKE :kw OR p.category.name LIKE :kw OR p.category.nameVN LIKE :kw";
		Session session=factory.getCurrentSession();
		TypedQuery<Product> query=session.createQuery(hql,Product.class);
		query.setParameter("kw", "%"+keywords+"%");
		List<Product> list=query.getResultList();
		return list;
    }  
}
