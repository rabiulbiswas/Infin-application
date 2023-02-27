package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.manager.PlatformManagerResponse;

public interface PlatformManagerService {
    PagedResponse<PlatformManagerResponse> getAllPlatformManager(int page, int size);


    PlatformManagerResponse getPlatformManagerDetail(Long id);

    void updatePlatformManagerProfile(Long id, UserRequestDto updateRequest);
}
