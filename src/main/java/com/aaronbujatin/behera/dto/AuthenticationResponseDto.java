<<<<<<< HEAD
package com.aaronbujatin.behera.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {

    private String token;
    private String tokenType = "Bearer ";

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
=======
package com.aaronbujatin.behera.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {

    private String token;
    private String tokenType = "Bearer ";

    public AuthenticationResponseDto(String token) {
        this.token = token;
    }
}
>>>>>>> aa5d7930261bfcda661c8514cba1651c03c65717
