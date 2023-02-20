package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
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
    public PagedResponse<ClientAdminResponse> getAllClients(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ClientAdminResponse> clients = professionalAdminRespository.findByUser(currentUser.getId(),pageable);


        if (clients.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), clients.getNumber(),
                    clients.getSize(), clients.getTotalElements(), clients.getTotalPages(), clients.isLast());
        }
        List<ClientAdminResponse> clientAdminResponse = clients.getContent();

        return new PagedResponse<>(clientAdminResponse, clients.getNumber(),
                clients.getSize(), clients.getTotalElements(), clients.getTotalPages(), clients.isLast());
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
    public Long updateProfessionalAdminProfile(Long id, UserRequestDto updateRequest)
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
        updateProfessionaAdminldata.setName(updateRequest.getName());
        updateProfessionaAdminldata.setMobile(updateRequest.getMobile());
        updateProfessionaAdminldata.setUpdatedBy(userPrincipal.getId());
         new ProfessionalAdminDetail();
        Optional<ProfessionalAdminDetail> fetchProfessionalAdminDetail =  professionalAdminRespository.findById(updateRequest.getProfessionalAdminDetail().getProfessionalAdminDetailId());
        if(fetchProfessionalAdminDetail.isPresent()){
            ProfessionalAdminDetail updateProfessionalAdminDetail = fetchProfessionalAdminDetail.get();
            updateProfessionalAdminDetail.setMembershipNumber(updateRequest.getProfessionalAdminDetail().getMembershipNumber());
            updateProfessionalAdminDetail.setContactAddress(updateRequest.getProfessionalAdminDetail().getContactAddress());
            updateProfessionaAdminldata.setProfessionalAdminDetails(updateProfessionalAdminDetail);
            updateProfessionalAdminDetail.setUser(updateProfessionaAdminldata);
        }
        return userRepository.save(updateProfessionaAdminldata).getId();


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
