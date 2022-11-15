package com.yeeshop.service;

import org.springframework.security.core.token.Token;

public interface TokenService {
    Token createToken(Token token);

}
