package com.aaronbujatin.behera.controller;

import com.aaronbujatin.behera.dto.AuthenticationResponseDto;
import com.aaronbujatin.behera.dto.SigninDto;
import com.aaronbujatin.behera.entity.User;
import com.aaronbujatin.behera.jwt.JwtProvider;
import com.aaronbujatin.behera.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
@RestController
@CrossOrigin(value = "http://localhost:4200")
public class AuthenticationController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>("User successfully created", HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<AuthenticationResponseDto> signin(@RequestBody SigninDto signinDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signinDto.getUsername(), signinDto.getPassword()));

        log.info("Authentication : {}", authentication);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generateToken(authentication);
        return new ResponseEntity<>(new AuthenticationResponseDto(token), HttpStatus.OK);
    }


}
