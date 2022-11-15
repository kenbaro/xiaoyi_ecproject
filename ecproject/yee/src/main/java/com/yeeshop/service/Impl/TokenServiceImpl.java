package com.yeeshop.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;

import com.yeeshop.repository.TokenRepository;

public class TokenServiceImpl implements TokenService {

    @Override
    public Token allocateToken(String extendedInformation) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Token verifyToken(String key) {
        // TODO Auto-generated method stub
        return null;
    }
    @Autowired
    private TokenRepository tokenRepository;

    public Token creaToken(Token token){
        return tokenRepository.saveAndFlush(token);
    }
}
