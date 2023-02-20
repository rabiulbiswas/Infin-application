package com.infin.service.impl;

import com.infin.dto.client.ClientAdminResponse;
import com.infin.exception.NotFoundException;
import com.infin.repository.ClientAdminRepository;
import com.infin.service.ClientAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientAdminServiceImpl implements ClientAdminService {

    @Autowired
    private ClientAdminRepository clientAdminRepository;
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
}
