package com.infin.dto.professional.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Data
@Setter
@Getter
public class ProfessionalAdminResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Long professionalAdminDetailId;
    private String membershipNumber;
    private String contactAddress;
    private Long createdBy;

    public ProfessionalAdminResponse(Long id, String name, String email, String mobile,Long professionalAdminDetailId, String membershipNumber, String contactAddress, Long createdBy) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.professionalAdminDetailId = professionalAdminDetailId;
        this.membershipNumber = membershipNumber;
        this.contactAddress = contactAddress;
        this.createdBy = createdBy;
    }
}
