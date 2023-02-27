package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.security.UserPrincipal;

public interface ClientAdminService {
    PagedResponse<ClientAdminResponse> getAllClientAdminList(int page, int size);

    PagedResponse<ClientAdminResponse> getAllClientAdminCreatedBy(UserPrincipal currentUser, int page, int size);

    ClientAdminResponse getClientAdminDetail(Long clientDetailId);

    void updateClientAdminProfile(Long id, UserRequestDto updateRequest);
}
