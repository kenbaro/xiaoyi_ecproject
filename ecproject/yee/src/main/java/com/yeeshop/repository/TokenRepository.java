package com.yeeshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.token.Token;
public interface TokenRepository extends JpaRepository<Token,Long> {


}
