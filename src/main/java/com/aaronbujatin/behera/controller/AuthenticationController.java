package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
@CrossOrigin(value = "http://localhost:4200")
public class AuthenticationController {

    private final UserService userService;

    

}
