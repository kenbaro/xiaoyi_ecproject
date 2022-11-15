package com.yeeshop.dao;

import java.util.List;

import com.yeeshop.entity.User;

public interface IUserDAO {
    User findById(String id);
    List <User> findAll();
    User create(User entity);
    void update (User entity);
    User delete (String id);
    long getPageCount(int pageSize);
    List<User>getPage(int pageNo,int pageSize);
}
