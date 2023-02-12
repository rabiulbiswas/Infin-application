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
    private String name;
    private String email;
    private Long verified;
    private List<String> roles;

    public JwtAuthenticationResponse(String jwt,Long id, String name, String email,Long verified, List<String> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.name = name;
        this.email = email;
        this.verified = verified;
        this.roles = roles;
    }
}
