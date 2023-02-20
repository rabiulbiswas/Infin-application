package com.infin.dto.platform.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlatformUserRequest {
    @NotEmpty(message = "Location shouldn't be empty")
    private String contactAddress;
    @NotEmpty(message = "Please upload any document")
    private String uploadedDocument;
    @NotEmpty(message = "Please upload any valid ID proof")
    private String validIdProof;
    @NotEmpty
    private Integer platformManagerId;
}
