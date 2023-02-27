package com.infin.dto.professional.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
public class ProfessionalAdminResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long professionalAdminDetailId;
    private String membershipNumber;
    private String contactAddress;
    private Long isVerified;
    private Long isEnabled;

    public ProfessionalAdminResponse(Long id, String firstName, String lastName, String email, String mobile, Long professionalAdminDetailId, String membershipNumber, String contactAddress, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.professionalAdminDetailId = professionalAdminDetailId;
        this.membershipNumber = membershipNumber;
        this.contactAddress = contactAddress;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
