package com.infin.dto.professional.manager;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProfessionalManagerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long professionalManagerDetailId;
    private String validIdProof;
    private Long isVerified;
    private Long isEnabled;

    public ProfessionalManagerResponse(Long id, String firstName, String lastName, String email, String mobile, Long professionalManagerDetailId,String validIdProof, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.professionalManagerDetailId = professionalManagerDetailId;
        this.validIdProof = validIdProof;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
