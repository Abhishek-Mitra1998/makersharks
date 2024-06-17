package com.makersharks.identity.controller;

import com.makersharks.identity.constants.ApiResponse;
import com.makersharks.identity.dao.LoginDao;
import com.makersharks.identity.dao.UserDao;
import com.makersharks.identity.entity.User;
import com.makersharks.identity.entity.UserDetails;
import com.makersharks.identity.exception.CustomException;
import com.makersharks.identity.service.UserService;
import com.makersharks.identity.util.UserUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class IdentityController {
    @Autowired
    UserService userService;

    @Autowired
    UserUtil userUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<String>> register(
            @RequestBody @Valid UserDao userDao
    ) throws CustomException {
        return ResponseEntity.ok(userService.register(userDao));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<String>> login(
            @RequestBody @Valid LoginDao loginDao
    ) throws CustomException {
        return ResponseEntity.ok(userService.login(loginDao));
    }

    @GetMapping("/fetch")
    public ResponseEntity<ApiResponse<UserDetails>> fetchUser(
            @RequestHeader("token") String token,
            @RequestParam(value = "username", required = true) String username
    ) throws CustomException {
        if(userUtil.isTokenValid(token,username)){
            return ResponseEntity.ok(userService.fetchUser(username));
        }
        return null;
    }


}
