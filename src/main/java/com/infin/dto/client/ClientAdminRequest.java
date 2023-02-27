package com.infin.dto.client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientAdminRequest {
    private Long clientAdminDetailId;
    @NotEmpty(message = "Company Name shouldn't be empty")
    private String companyName;
    @NotEmpty(message = "Pan Number shouldn't be empty")
    private String panNumber;
    @NotEmpty(message = "GST Number shouldn't be empty")
    private String gstNumber;
    @NotEmpty(message = "Business Type shouldn't be empty")
    private String businessType;
    @NotEmpty(message = "Communication Address shouldn't be empty")
    private String communicationAddress;
}
