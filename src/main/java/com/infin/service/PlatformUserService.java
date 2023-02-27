package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.user.PlatformUserResponse;

public interface PlatformUserService {
    PagedResponse<PlatformUserResponse> getAllPlatformUser(int page, int size);

    PagedResponse<PlatformUserResponse> findAllPlatformUserByPlatformManagerId(Long platformManaerId, int page, int size);

    PlatformUserResponse getPlatformUserDetail(Long id);

    void updatePlatformUserProfile(Long id, UserRequestDto updateRequest);
}
