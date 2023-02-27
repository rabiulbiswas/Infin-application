package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.professional.manager.ProfessionalManagerResponse;
import com.infin.entity.User;
import com.infin.entity.professional.manager.ProfessionalManagerDetail;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.ProfessionalManagerRepository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.ProfessionalManagerService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProfessionalManagerImpl implements ProfessionalManagerService {
    @Autowired
    private ProfessionalManagerRepository professionalManagerRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public PagedResponse<ProfessionalManagerResponse> getProfessionalManagerByProfessionalAdmin(Long professionalAdminId, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ProfessionalManagerResponse> professionalManagerResponse = professionalManagerRepository.getProfessionalManagerByProfessionalAdmin(professionalAdminId,pageable);


        if (professionalManagerResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), professionalManagerResponse.getNumber(),
                    professionalManagerResponse.getSize(), professionalManagerResponse.getTotalElements(), professionalManagerResponse.getTotalPages(), professionalManagerResponse.isLast());
        }
        List<ProfessionalManagerResponse> professionalManagerResponseList = professionalManagerResponse.getContent();

        return new PagedResponse<>(professionalManagerResponseList, professionalManagerResponse.getNumber(),
                professionalManagerResponse.getSize(), professionalManagerResponse.getTotalElements(), professionalManagerResponse.getTotalPages(), professionalManagerResponse.isLast());
    }

    @Override
    public ProfessionalManagerResponse getProfessionalManagerProfile(Long professionalManagerId) {
        ProfessionalManagerResponse professionalManagerResponse = professionalManagerRepository.getProfessionalManagerProfile(professionalManagerId).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Professional Manager '")
                        .append(professionalManagerId)
                        .append("' not exist")
                        .toString())
        );
        return professionalManagerResponse;
    }

    @Override
    public void updateProfessionalManagerProfile(Long professionalManagerId, UserRequestDto updateRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
        if(!userRepository.existsById(professionalManagerId)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Professional Manager '")
                            .append(professionalManagerId)
                            .append("' not exist")
                            .toString());
        }
        User updateProfessionalManager = userRepository.findById(professionalManagerId).get();
        updateProfessionalManager.setFirstName(updateRequest.getFirstName());
        updateProfessionalManager.setLastName(updateRequest.getLastName());
        updateProfessionalManager.setMobile(updateRequest.getMobile());
        updateProfessionalManager.setUpdatedBy(userPrincipal.getId());

        Optional<ProfessionalManagerDetail> fetchProfessionalManagerDetail =  professionalManagerRepository.findById(updateRequest.getProfessionalManagerRequest().getProfessionalManagerDetailId());
        if(fetchProfessionalManagerDetail.isPresent()){
            ProfessionalManagerDetail updateProfessionalManagerDetail = fetchProfessionalManagerDetail.get();
            updateProfessionalManagerDetail.setValidIdProof(updateRequest.getProfessionalManagerRequest().getValidIdProof());

            updateProfessionalManager.setProfessionalManagerDetail(updateProfessionalManagerDetail);
            updateProfessionalManagerDetail.setUser(updateProfessionalManager);
        }
        userRepository.save(updateProfessionalManager);
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
