<<<<<<< HEAD
package com.aaronbujatin.behera.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/endpoint")
@RestController
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Public endpoint";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint(){
        return "User endpoint";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint(){
        return "Admin endpoint";
    }
}
=======
package com.aaronbujatin.behera.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/endpoint")
@RestController
public class TestController {

    @GetMapping("/public")
    public String publicEndpoint(){
        return "Public endpoint";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint(){
        return "User endpoint";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint(){
        return "Admin endpoint";
    }
}
>>>>>>> aa5d7930261bfcda661c8514cba1651c03c65717
