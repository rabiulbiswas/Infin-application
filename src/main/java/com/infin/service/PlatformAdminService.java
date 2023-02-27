package com.infin.service;

import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.security.UserPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface PlatformAdminService {
    PlatformAdminResponse getPlatformAdminDetail(UserPrincipal currentUser);
    void updatePlatformAdminProfile(Long id, UserRequestDto updateRequest);

    @Transactional
    Integer verifyUserAccountById(Long status, Long id);
}

