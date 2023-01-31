package com.infin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "Name shouldn't be null")
    private String name;
    @NotEmpty(message = "username shouldn't be null")
    private String username;
    @Email(message = "invalid email address")
    @NotEmpty(message = "Email shouldn't be null")
    private String email;

    @NotEmpty(message = "password shouldn't be null")
    private String password;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
    @NotEmpty(message = "Mobile shouldn't be null")
    private String mobile;
    @NotEmpty(message = "nationality shouldn't be null")
    private String nationality;
}
