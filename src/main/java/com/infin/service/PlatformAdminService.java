package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.security.UserPrincipal;
import org.springframework.transaction.annotation.Transactional;

public interface PlatformAdminService {
    PlatformAdminResponse getPlatformAdminDetail(UserPrincipal currentUser);

    PagedResponse<ProfessionalAdminResponse> getProfessionalAdminList(int page, int size);

    PagedResponse<ClientAdminResponse> getClientAdminList(int page, int size);

    void updatePlatformAdminProfile(Long id, UserRequestDto updateRequest);

    @Transactional
    Integer verifyUserAccountById(Long status, Long id);
}

