package com.example.windowPos.global.jwt;

import com.example.windowPos.global.encrypt.EncryptionUtils;
import com.example.windowPos.global.util.Util;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Component
public class JwtProvider {

    private final EncryptionUtils encryptionUtils;
    private SecretKey cachedSecretKey;
    @Autowired
    public JwtProvider(EncryptionUtils encryptionUtils) {
        this.encryptionUtils = encryptionUtils;
    }

    private SecretKey _getSecretKey() {
        return encryptionUtils.getSecretKey();
    }

    public SecretKey getSecretKey() {
        if (cachedSecretKey == null) cachedSecretKey = _getSecretKey();

        return cachedSecretKey;
    }
    public String getUsername(String token) {
        Object usernameObject = getClaims(token).get("username");

        if (usernameObject != null) {
            return usernameObject.toString();
        } else {
            return null;
        }
    }

    public String genToken(Map<String, Object> claims, int seconds) {
        long now = new Date().getTime();
        Date accessTokenExpiresIn = new Date(now + 1000L * seconds);

        return Jwts.builder()
                .claim("body", Util.json.toStr(claims))
                .setExpiration(accessTokenExpiresIn)
                .signWith(getSecretKey(), SignatureAlgorithm.HS512)
                .compact();
    }

    public boolean verify(String token) {
        try {
            Jwts.parser()
                    .setSigningKey(getSecretKey())
                    .build()
                    .parseClaimsJws(token); // 만료됐는지 확인
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    public Map<String, Object> getClaims(String token) {
        String body = Jwts.parser()
                .setSigningKey(getSecretKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("body", String.class);

        return Util.toMap(body);
    }

}