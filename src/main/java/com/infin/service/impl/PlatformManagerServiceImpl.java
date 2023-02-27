package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.entity.User;
import com.infin.entity.platform.manager.PlatformManagerDetail;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.PlatformManagerRepository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.PlatformManagerService;
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
public class PlatformManagerServiceImpl implements PlatformManagerService {
    @Autowired
    private PlatformManagerRepository platformManagerRepository;
    @Autowired
    UserRepository userRepository;

    @Override
    public PagedResponse<PlatformManagerResponse> getAllPlatformManager(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<PlatformManagerResponse> platformManagerResponse = platformManagerRepository.findAllPlatformManager(pageable);


        if (platformManagerResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), platformManagerResponse.getNumber(),
                    platformManagerResponse.getSize(), platformManagerResponse.getTotalElements(), platformManagerResponse.getTotalPages(), platformManagerResponse.isLast());
        }
        List<PlatformManagerResponse> platformManagerResponseList = platformManagerResponse.getContent();

        return new PagedResponse<>(platformManagerResponseList, platformManagerResponse.getNumber(),
                platformManagerResponse.getSize(), platformManagerResponse.getTotalElements(), platformManagerResponse.getTotalPages(), platformManagerResponse.isLast());
    }

    @Override
    public PlatformManagerResponse getPlatformManagerDetail(Long id) {
        PlatformManagerResponse platformManagerResponse = platformManagerRepository.findByPlatformManagerId(id).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Platform Manager '")
                        .append(id)
                        .append("' not exist")
                        .toString())
        );
        return platformManagerResponse;
    }

    @Override
    public void updatePlatformManagerProfile(Long id, UserRequestDto updateRequest)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal =(UserPrincipal) authentication.getPrincipal();
        if(!userRepository.existsById(id)) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Platform manager '")
                            .append(id)
                            .append("' not exist")
                            .toString());
        }
        User updatePlatformManager = userRepository.findById(id).get();
        updatePlatformManager.setFirstName(updateRequest.getFirstName());
        updatePlatformManager.setLastName(updateRequest.getLastName());
        updatePlatformManager.setMobile(updateRequest.getMobile());
        updatePlatformManager.setUpdatedBy(userPrincipal.getId());

        Optional<PlatformManagerDetail> fetchPlatformManagerDetail =  platformManagerRepository.findById(updateRequest.getPlatformManagerDetail().getPlatformManagerDetailId());
        if(fetchPlatformManagerDetail.isPresent()){
            PlatformManagerDetail updatePlatformManagerDetail = fetchPlatformManagerDetail.get();
            updatePlatformManagerDetail.setContactAddress(updateRequest.getPlatformManagerDetail().getContactAddress());
            updatePlatformManagerDetail.setUploadedDocument(updateRequest.getPlatformManagerDetail().getUploadedDocument());
            updatePlatformManagerDetail.setValidIdProof(updateRequest.getPlatformManagerDetail().getValidIdProof());
            updatePlatformManager.setPlatformManagerDetails(updatePlatformManagerDetail);
            updatePlatformManagerDetail.setUser(updatePlatformManager);
        }
        userRepository.save(updatePlatformManager);


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
