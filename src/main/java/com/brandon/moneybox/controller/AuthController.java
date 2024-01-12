package com.brandon.moneybox.controller;

import com.brandon.moneybox.entity.User;
import com.brandon.moneybox.model.LoginUserRequest;
import com.brandon.moneybox.model.Response;
import com.brandon.moneybox.model.TokenResponse;
import com.brandon.moneybox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Response<String> registerUser(@RequestBody User user) {
        return userService.registerUser(user);
    }

    @PostMapping("/login")
    public Response<TokenResponse> loginUser(@RequestBody LoginUserRequest loginRequest) {
        return userService.loginUser(loginRequest);
    }

    @DeleteMapping("/logout")
    public Response<String> logoutUser(@RequestHeader("X-API-TOKEN") String token) {
        return userService.logoutUser(token);
    }
}
