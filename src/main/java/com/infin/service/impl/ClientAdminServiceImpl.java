package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.entity.User;
import com.infin.entity.client.ClientAdminDetail;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.ClientAdminRepository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.ClientAdminService;
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
public class ClientAdminServiceImpl implements ClientAdminService {

    @Autowired
    private ClientAdminRepository clientAdminRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public PagedResponse<ClientAdminResponse> getAllClientAdminList(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ClientAdminResponse> clientAdminResponse = clientAdminRepository.findAllClientAdmin(pageable);


        if (clientAdminResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), clientAdminResponse.getNumber(),
                    clientAdminResponse.getSize(), clientAdminResponse.getTotalElements(), clientAdminResponse.getTotalPages(), clientAdminResponse.isLast());
        }
        List<ClientAdminResponse> clientAdminResponseList = clientAdminResponse.getContent();

        return new PagedResponse<>(clientAdminResponseList, clientAdminResponse.getNumber(),
                clientAdminResponse.getSize(), clientAdminResponse.getTotalElements(), clientAdminResponse.getTotalPages(), clientAdminResponse.isLast());
    }

    @Override
    public PagedResponse<ClientAdminResponse> getAllClientAdminCreatedBy(UserPrincipal currentUser, int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<ClientAdminResponse> clients = clientAdminRepository.findByUser(currentUser.getId(),pageable);


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

        ClientAdminResponse clientAdminResponse = clientAdminRepository.findByClientAdminId(clientDetailId)
                .orElseThrow(()->new NotFoundException(
                        new StringBuffer().append("Client Admin '")
                                .append(clientDetailId)
                                .append("' not exist")
                                .toString())
                );
        return clientAdminResponse;
    }

    @Override
    public void updateClientAdminProfile(Long clientAdminId, UserRequestDto updateRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
        if(!userRepository.existsById(clientAdminId)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Client Admin '")
                            .append(clientAdminId)
                            .append("' not exist")
                            .toString());
        }
        User updateClientAdminData = userRepository.findById(clientAdminId).get();
        updateClientAdminData.setFirstName(updateRequest.getFirstName());
        updateClientAdminData.setLastName(updateRequest.getLastName());
        updateClientAdminData.setMobile(updateRequest.getMobile());
        updateClientAdminData.setUpdatedBy(userPrincipal.getId());
        Optional<ClientAdminDetail> fetchClientAdminDetail =  clientAdminRepository.findById(updateRequest.getClientAdminDetail().getClientAdminDetailId());
        if(fetchClientAdminDetail.isPresent()){
            ClientAdminDetail updateClientAdminDetail = fetchClientAdminDetail.get();
            updateClientAdminDetail.setCompanyName(updateRequest.getClientAdminDetail().getCompanyName());
            updateClientAdminDetail.setPanNumber(updateRequest.getClientAdminDetail().getPanNumber());
            updateClientAdminDetail.setGstNumber(updateRequest.getClientAdminDetail().getGstNumber());
            updateClientAdminDetail.setBusinessType(updateRequest.getClientAdminDetail().getBusinessType());
            updateClientAdminDetail.setCommunicationAddress(updateRequest.getClientAdminDetail().getCommunicationAddress());
            updateClientAdminData.setClientAdminDetails(updateClientAdminDetail);
            updateClientAdminDetail.setUser(updateClientAdminData);
        }
        userRepository.save(updateClientAdminData);
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
