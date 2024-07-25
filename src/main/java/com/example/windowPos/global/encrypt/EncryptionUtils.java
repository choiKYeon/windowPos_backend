package com.example.windowPos.global.encrypt;


import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class EncryptionUtils {
//    private static final String ALGORITHM = "AES";
    private SecretKey secretKey;

    @Value("${custom.jwt.secretKey}")
    private String base64SecretString;
    @PostConstruct
    public void init() {
        // HS512에 적합한 키 길이 확인 및 변환
        byte[] decodedKey = java.util.Base64.getDecoder().decode(base64SecretString);
        // JWT 생성에 사용될 SecretKey 생성
        this.secretKey = Keys.hmacShaKeyFor(decodedKey);
    }
    public SecretKey getSecretKey() {return secretKey;}

}
