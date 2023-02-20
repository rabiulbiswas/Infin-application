package com.infin.dto.platform.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PlatformUserResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Long platformUserDetailId;
    private String contactAddress;
    private String uploadedDocument;
    private String validIdProof;
    private Integer platformManagerId;

    public PlatformUserResponse(Long id, String name, String email, String mobile, Long platformUserDetailId, String contactAddress, String uploadedDocument, String validIdProof, Integer platformManagerId) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.platformUserDetailId = platformUserDetailId;
        this.contactAddress = contactAddress;
        this.uploadedDocument = uploadedDocument;
        this.validIdProof = validIdProof;
        this.platformManagerId = platformManagerId;
    }
}
