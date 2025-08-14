package com.zeta.miniproject2.Restaurant.Management.System.Controller;

import com.zeta.miniproject2.Restaurant.Management.System.Config.AppConfig;
import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.AuthRequest;
import com.zeta.miniproject2.Restaurant.Management.System.Model.DTO.AuthResponse;
import com.zeta.miniproject2.Restaurant.Management.System.Security.JWTUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthController {

    private final JWTUtil jwtUtil;
    private final AppConfig appConfig;

    @Autowired
    public AuthController(JWTUtil jwtUtil, AppConfig appConfig) {
        this.jwtUtil = jwtUtil;
        this.appConfig = appConfig;
    }

    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {

        if (appConfig.getUsername().equals(authRequest.getUsername()) &&
                appConfig.getPassword().equals(authRequest.getPassword())) {

            String token = jwtUtil.generateToken(authRequest.getUsername(), authRequest.getRole());
            return ResponseEntity.ok(new AuthResponse(token));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Collections.singletonMap("error", "Invalid Credentials!"));
    }

    @PostConstruct
    public void printValues() {
        System.out.println("JWT Username: " + appConfig.getUsername());
        System.out.println("JWT Password: " + appConfig.getPassword());
    }
}
