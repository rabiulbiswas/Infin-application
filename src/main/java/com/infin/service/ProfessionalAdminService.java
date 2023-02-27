package com.infin.service;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;

import javax.validation.Valid;

public interface ProfessionalAdminService {

    PagedResponse<ProfessionalAdminResponse> getProfessionalAdminList(int page, int size);

    ProfessionalAdminResponse getProfessionalAdminDetail(Long id);
    void updateProfessionalAdminProfile(Long id,@Valid UserRequestDto updateRequest);


}
