package com.infin.dto.platform.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PlatformAdminResponse {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobile;
    private Long isVerified;
    private Long isEnabled;

    public PlatformAdminResponse(Long id, String firstName, String lastName, String email, String mobile, Long isVerified, Long isEnabled) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.mobile = mobile;
        this.isVerified = isVerified;
        this.isEnabled = isEnabled;
    }
}
