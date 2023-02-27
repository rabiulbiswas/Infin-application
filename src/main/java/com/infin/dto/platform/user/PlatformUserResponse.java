package com.infin.dto.platform.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PlatformUserResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long platformUserDetailId;
    private String contactAddress;
    private String uploadedDocument;
    private String validIdProof;
    private Long platformManagerId;
    private Long isVerified;
    private Long isEnabled;

    public PlatformUserResponse(Long id, String firstName, String lastName, String email, String mobile, Long platformUserDetailId, String contactAddress, String uploadedDocument, String validIdProof, Long platformManagerId, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.platformUserDetailId = platformUserDetailId;
        this.contactAddress = contactAddress;
        this.uploadedDocument = uploadedDocument;
        this.validIdProof = validIdProof;
        this.platformManagerId = platformManagerId;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
