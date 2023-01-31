package com.infin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";

    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    public JwtAuthenticationResponse(String jwt,Long id, String username, String email, List<String> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}
