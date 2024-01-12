package com.brandon.moneybox.controller;

import com.brandon.moneybox.model.Response;
import com.brandon.moneybox.model.UpdateUserRequest;
import com.brandon.moneybox.model.UserResponse;
import com.brandon.moneybox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public Response<UserResponse> getCurrentUserInfo(@RequestHeader("X-API-TOKEN") String token) {
        String username = userService.validateToken(token);
        if (username != null) {
            return userService.getUser(username);
        } else {
            return Response.<UserResponse>builder().error("Unauthorized").build();
        }
    }

    @PatchMapping
    public Response<String> updateUser(@RequestHeader("X-API-TOKEN") String token, @RequestBody UpdateUserRequest updateUserRequest) {
        return userService.updateUser(token, updateUserRequest);
    }

}
