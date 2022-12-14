package com.fastcampus.sns.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

public class JwtTokenUtils {

    public static Boolean validate(String token, UserDetails userDetails, String key) {
        String username = getUserName(token, key);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token, key);
    }

    public static Claims extractAllClaims(String token, String key) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey(key))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUserName(String token, String key) {
        return extractAllClaims(token, key).get("username", String.class);
    }

    public static Boolean isTokenExpired(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.before(new Date());
    }

    private static Key getSigningKey(String secretKey) {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public static String generateAccessToken(String username, String key, long expiredTimeMs) {
        return doGenerateToken(username, expiredTimeMs, key);
    }

    public static String generateRefreshToken(String username, String key, long expiredTimeMs) {
        return doGenerateToken(username, expiredTimeMs, key);
    }

    public static long getRemainMilliSeconds(String token, String key) {
        Date expiration = extractAllClaims(token, key).getExpiration();
        return expiration.getTime() - new Date().getTime();
    }

    private static String doGenerateToken(String username, long expireTime, String key) {
        Claims claims = Jwts.claims();
        claims.put("username", username);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigningKey(key), SignatureAlgorithm.HS256)
                .compact();
    }

}
