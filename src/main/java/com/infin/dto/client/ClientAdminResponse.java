package com.infin.dto.client;

import lombok.*;

@Data
@Setter
@Getter
public class ClientAdminResponse {

    private Long id;
    private String name;
    private String email;
    private String mobile;
    private String companyName;
    private String panNumber;
    private String gstNumber;
    private String businessType;
    private String communicationAddress;
    private Long createdBy;

    public ClientAdminResponse(Long id, String name, String email, String mobile, String companyName, String panNumber, String gstNumber, String businessType, String communicationAddress, Long createdBy) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.companyName = companyName;
        this.panNumber = panNumber;
        this.gstNumber = gstNumber;
        this.businessType = businessType;
        this.communicationAddress = communicationAddress;
        this.createdBy = createdBy;
    }
}
