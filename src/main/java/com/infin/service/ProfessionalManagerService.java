package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.professional.manager.ProfessionalManagerResponse;

public interface ProfessionalManagerService {
    PagedResponse<ProfessionalManagerResponse> getProfessionalManagerByProfessionalAdmin(Long professionalAdminId, int page, int size);

    ProfessionalManagerResponse getProfessionalManagerProfile(Long professionalManagerId);

    void updateProfessionalManagerProfile(Long professionalManagerId, UserRequestDto updateRequest);
}
