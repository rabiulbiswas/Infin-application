package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.entity.professional.admin.ProfessionalAdminDetail;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.ProfessionalAdminRespository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.ProfessionalAdminService;
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
public class ProfessionalAdminServiceImpl implements ProfessionalAdminService {

    @Autowired
    private ProfessionalAdminRespository professionalAdminRespository;
    @Autowired
    UserRepository userRepository;

    @Override
    public PagedResponse<ProfessionalAdminResponse> getProfessionalAdminList(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ProfessionalAdminResponse> professionalAdminResponse = professionalAdminRespository.findAllProfessionalAdmin(pageable);


        if (professionalAdminResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), professionalAdminResponse.getNumber(),
                    professionalAdminResponse.getSize(), professionalAdminResponse.getTotalElements(), professionalAdminResponse.getTotalPages(), professionalAdminResponse.isLast());
        }
        List<ProfessionalAdminResponse> professionalAdminResponseList = professionalAdminResponse.getContent();

        return new PagedResponse<>(professionalAdminResponseList, professionalAdminResponse.getNumber(),
                professionalAdminResponse.getSize(), professionalAdminResponse.getTotalElements(), professionalAdminResponse.getTotalPages(), professionalAdminResponse.isLast());
    }

    @Override
    public ProfessionalAdminResponse getProfessionalAdminDetail(Long id) {

        ProfessionalAdminResponse professionalAdminResponse = professionalAdminRespository.findByProfessionalAdminId(id).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Professional Admin '")
                        .append(id)
                        .append("' not exist")
                        .toString())
        );
        return professionalAdminResponse;
    }

    @Override
    public void updateProfessionalAdminProfile(Long id, UserRequestDto updateRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
        if(!userRepository.existsById(id)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Professional Admin '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        User updateProfessionaAdminldata = userRepository.findById(id).get();
        updateProfessionaAdminldata.setFirstName(updateRequest.getFirstName());
        updateProfessionaAdminldata.setLastName(updateRequest.getLastName());
        updateProfessionaAdminldata.setMobile(updateRequest.getMobile());
        updateProfessionaAdminldata.setUpdatedBy(userPrincipal.getId());
        Optional<ProfessionalAdminDetail> fetchProfessionalAdminDetail =  professionalAdminRespository.findById(updateRequest.getProfessionalAdminDetail().getProfessionalAdminDetailId());
        if(fetchProfessionalAdminDetail.isPresent()){
            ProfessionalAdminDetail updateProfessionalAdminDetail = fetchProfessionalAdminDetail.get();
            updateProfessionalAdminDetail.setMembershipNumber(updateRequest.getProfessionalAdminDetail().getMembershipNumber());
            updateProfessionalAdminDetail.setContactAddress(updateRequest.getProfessionalAdminDetail().getContactAddress());
            updateProfessionaAdminldata.setProfessionalAdminDetails(updateProfessionalAdminDetail);
            updateProfessionalAdminDetail.setUser(updateProfessionaAdminldata);
        }
       userRepository.save(updateProfessionaAdminldata);
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
