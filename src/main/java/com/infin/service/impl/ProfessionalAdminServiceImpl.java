package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.exception.ResourceNotFoundException;
import com.infin.repository.ProfessionalAdminRespository;
import com.infin.security.UserPrincipal;
import com.infin.service.ProfessionalAdminService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class ProfessionalAdminServiceImpl implements ProfessionalAdminService {

    @Autowired
    private ProfessionalAdminRespository professionalAdminRespository;
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
    public ClientAdminResponse getClientAdminDetail(Long clientDetailId) {

       ClientAdminResponse clientAdminResponse = professionalAdminRespository.findByClientAdminId(clientDetailId)
               .orElseThrow(()->new NotFoundException(
               new StringBuffer().append("Client Admin '")
                       .append(clientDetailId)
                       .append("' not exist")
                       .toString())
       );
        return clientAdminResponse;
    }

    @Override
    public ProfessionalAdminResponse getProfessionalAdminDetail(UserPrincipal currentUser) {

        ProfessionalAdminResponse professionalAdminResponse = professionalAdminRespository.findByProfessionalAdminId(currentUser.getId()).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Professional Admin '")
                        .append(currentUser.getId())
                        .append("' not exist")
                        .toString())
        );
        return professionalAdminResponse;
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
