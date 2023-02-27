package com.infin.dto.client;

import lombok.*;

@Data
@Setter
@Getter
public class ClientAdminResponse {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long clientAdminDetailId;
    private String companyName;
    private String panNumber;
    private String gstNumber;
    private String businessType;
    private String communicationAddress;
    private Long isVerified;
    private Long isEnabled;

    public ClientAdminResponse(Long id, String firstName, String lastName, String email, String mobile, Long clientAdminDetailId, String companyName, String panNumber, String gstNumber, String businessType, String communicationAddress, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.clientAdminDetailId = clientAdminDetailId;
        this.companyName = companyName;
        this.panNumber = panNumber;
        this.gstNumber = gstNumber;
        this.businessType = businessType;
        this.communicationAddress = communicationAddress;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
