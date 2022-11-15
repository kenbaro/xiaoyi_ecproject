package com.yeeshop.service;

import com.yeeshop.entity.UserEntity;

public interface UserService {
        UserEntity findByUsername(String username);
}
