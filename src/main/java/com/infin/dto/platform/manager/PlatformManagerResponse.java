package com.infin.dto.platform.manager;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Setter
@Getter
public class PlatformManagerResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long platformManagerDetailId;
    private String contactAddress;
    private String uploadedDocument;
    private String validIdProof;
    private Long isVerified;
    private Long isEnabled;

    public PlatformManagerResponse(Long id, String firstName, String lastName, String email, String mobile, Long platformManagerDetailId, String contactAddress, String uploadedDocument, String validIdProof, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.platformManagerDetailId = platformManagerDetailId;
        this.contactAddress = contactAddress;
        this.uploadedDocument = uploadedDocument;
        this.validIdProof = validIdProof;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
