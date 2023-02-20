package com.infin.dto.professional.admin;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfessionalAdminRequest {
    private Long professionalAdminDetailId;
    @NotEmpty(message = "Membership Number shouldn't be empty")
    private String membershipNumber;
    @NotEmpty(message = "Contact Address shouldn't be empty")
    private String contactAddress;
}
