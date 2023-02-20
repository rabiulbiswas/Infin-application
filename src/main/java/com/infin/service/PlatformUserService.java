package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.platform.user.PlatformUserResponse;
import com.infin.security.UserPrincipal;

public interface PlatformUserService {
    PagedResponse<PlatformUserResponse> getAllPlatformUser(int page, int size);

    PlatformUserResponse getPlatformUserDetail(Long id);
}
