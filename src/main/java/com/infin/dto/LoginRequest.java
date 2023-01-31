package com.infin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class LoginRequest {

    @NotEmpty(message = "User name or email is required")
    private String usernameOrEmail;

    @NotEmpty(message = "password is required")
    private String password;
}
