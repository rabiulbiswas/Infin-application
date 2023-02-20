package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.security.UserPrincipal;

public interface PlatformManagerService {
    PagedResponse<PlatformManagerResponse> getAllPlatformManager(int page, int size);


    PlatformManagerResponse getPlatformManagerDetail(Long id);
}
