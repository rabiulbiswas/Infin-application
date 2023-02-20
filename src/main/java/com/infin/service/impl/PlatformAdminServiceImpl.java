package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.PlatformAdminServiceRepository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.PlatformAdminService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class PlatformAdminServiceImpl implements PlatformAdminService {
    @Autowired
    private PlatformAdminServiceRepository platformAdminServiceRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public PlatformAdminResponse getPlatformAdminDetail(UserPrincipal currentUser) {
        PlatformAdminResponse platformAdminResponse = platformAdminServiceRepository.findByPlatformAdminId(currentUser.getId()).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Professional Admin '")
                        .append(currentUser.getId())
                        .append("' not exist")
                        .toString())
        );
        return platformAdminResponse;
    }

    @Override
    public PagedResponse<ProfessionalAdminResponse> getProfessionalAdminList(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ProfessionalAdminResponse> professionalAdminResponse = platformAdminServiceRepository.findAllProfessionalAdmin(pageable);


        if (professionalAdminResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), professionalAdminResponse.getNumber(),
                    professionalAdminResponse.getSize(), professionalAdminResponse.getTotalElements(), professionalAdminResponse.getTotalPages(), professionalAdminResponse.isLast());
        }
        List<ProfessionalAdminResponse> professionalAdminResponseList = professionalAdminResponse.getContent();

        return new PagedResponse<>(professionalAdminResponseList, professionalAdminResponse.getNumber(),
                professionalAdminResponse.getSize(), professionalAdminResponse.getTotalElements(), professionalAdminResponse.getTotalPages(), professionalAdminResponse.isLast());
    }

    @Override
    public PagedResponse<ClientAdminResponse> getClientAdminList(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ClientAdminResponse> clientAdminResponse = platformAdminServiceRepository.findAllClientAdmin(pageable);


        if (clientAdminResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), clientAdminResponse.getNumber(),
                    clientAdminResponse.getSize(), clientAdminResponse.getTotalElements(), clientAdminResponse.getTotalPages(), clientAdminResponse.isLast());
        }
        List<ClientAdminResponse> clientAdminResponseList = clientAdminResponse.getContent();

        return new PagedResponse<>(clientAdminResponseList, clientAdminResponse.getNumber(),
                clientAdminResponse.getSize(), clientAdminResponse.getTotalElements(), clientAdminResponse.getTotalPages(), clientAdminResponse.isLast());
    }

    @Override
    public void updatePlatformAdminProfile(Long id, UserRequestDto updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
        if(!userRepository.existsById(id)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Platform Admin '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        User updatePlatformAdminldata = userRepository.findById(id).get();
        updatePlatformAdminldata.setName(updateRequest.getName());
        updatePlatformAdminldata.setMobile(updateRequest.getMobile());
        updatePlatformAdminldata.setUpdatedBy(userPrincipal.getId());
        userRepository.save(updatePlatformAdminldata);
    }


    @Override
    @Transactional
    public Integer verifyUserAccountById(Long status, Long id)
    {
        if(!userRepository.existsById(id)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("User '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        return userRepository.verifyUserAccountById(status, id);
    }

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
