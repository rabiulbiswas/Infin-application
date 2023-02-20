package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.security.UserPrincipal;

import javax.validation.Valid;

public interface ProfessionalAdminService {
    PagedResponse<ClientAdminResponse> getAllClients(UserPrincipal currentUser, int page, int size);

    ProfessionalAdminResponse getProfessionalAdminDetail(Long id);
    Long updateProfessionalAdminProfile(Long id,@Valid UserRequestDto updateRequest);


}
