package com.infin.dto.platform.manager;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Setter
@Getter
public class PlatformManagerResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Long platformManagerDetailId;
    private String contactAddress;
    private String uploadedDocument;
    private String validIdProof;

    public PlatformManagerResponse(Long id, String name, String email, String mobile, Long platformManagerDetailId, String contactAddress, String uploadedDocument, String validIdProof) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.platformManagerDetailId = platformManagerDetailId;
        this.contactAddress = contactAddress;
        this.uploadedDocument = uploadedDocument;
        this.validIdProof = validIdProof;
    }
}
