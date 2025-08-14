package com.zeta.miniproject2.Restaurant.Management.System.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    @Value("${jwt.username}") public String username;
    @Value("${jwt.password}") public String password;
    @Value("${jwt.secretKey}") public String secretKey;
    @Value("${jwt.expiration}") public Long expiration;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getSecretKey() { return secretKey; }
    public void setSecretKey(String secretKey) { this.secretKey = secretKey; }
    public Long getExpiration() { return expiration; }
    public void setExpiration(Long expiration) { this.expiration = expiration; }
}