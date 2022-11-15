package com.yeeshop.utils;

import java.sql.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.yeeshop.entity.UserEntity;

@Component
public class JwtUtil {
    private static Logger logger = LoggerFactory.getLogger(JwtUtil.class);
    private static final String USER = "user";
    private static final String SECRET = "xiaoyi";

    public String generateToken(UserEntity user) {
        String token = null;
        try {
            JWTClaimsSet.Builder builder = new JWTClaimsSet.Builder();
            builder.claim(USER, user);
            builder.expirationTime(generateExpirationDate());
            JWTClaimsSet claimsSet = builder.build();
            SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
            JWSSigner signer = new MACSigner(SECRET.getBytes());
            signedJWT.sign(signer);
            token = signedJWT.serialize();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return token;
    }
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 864000000);
    }
}
