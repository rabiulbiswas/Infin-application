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
    private String firstName;
    private String lastName;
    private String email;
    private Long isVerified;
    private List<String> roles;

    public JwtAuthenticationResponse(String jwt,Long id, String firstName,String lastName, String email,Long isVerified, List<String> roles) {
        this.accessToken = jwt;
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isVerified = isVerified;
        this.roles = roles;
    }
}
