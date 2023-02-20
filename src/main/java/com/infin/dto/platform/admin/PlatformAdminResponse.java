package com.infin.dto.platform.admin;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class PlatformAdminResponse {
    private Long id;
    private String name;
    private String email;
    private String mobile;
    private Long createdBy;

    public PlatformAdminResponse(Long id, String name, String email, String mobile,Long createdBy) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.createdBy = createdBy;
    }
}
