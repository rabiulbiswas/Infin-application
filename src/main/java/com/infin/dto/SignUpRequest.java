package com.infin.dto;

import com.infin.dto.client.ClientAdminRequest;
import com.infin.dto.professional.admin.ProfessionalAdminRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequest {

    @NotEmpty(message = "Name shouldn't be empty")
    private String name;
    @Email(message = "invalid email address")
    @NotEmpty(message = "Email shouldn't be empty")
    private String email;

    @NotEmpty(message = "password shouldn't be empty")
    private String password;
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered ")
    @NotEmpty(message = "Mobile shouldn't be empty")
    private String mobile;
    private Set<String> role;
    private ProfessionalAdminRequest professionalAdminDetail;
    private ClientAdminRequest clientAdminDetail;

}
