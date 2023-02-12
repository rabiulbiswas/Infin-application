package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.security.UserPrincipal;

public interface ProfessionalAdminService {
    PagedResponse<ClientAdminResponse> getAllClients(UserPrincipal currentUser, int page, int size);
    ClientAdminResponse getClientAdminDetail(Long clientDetailId);

    ProfessionalAdminResponse getProfessionalAdminDetail(UserPrincipal currentUser);
}
