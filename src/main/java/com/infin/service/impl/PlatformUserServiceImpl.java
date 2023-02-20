package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.platform.user.PlatformUserResponse;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.PlatformUserRepository;
import com.infin.service.PlatformUserService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class PlatformUserServiceImpl implements PlatformUserService {
    @Autowired
    private PlatformUserRepository platformUserRepository;

    @Override
    public PagedResponse<PlatformUserResponse> getAllPlatformUser(int page, int size) {
        validatePageNumberAndSize(page, size);

        Pageable pageable = PageRequest.of(page,size, Sort.Direction.DESC,"createdAt");
        Page<PlatformUserResponse> platformUserResponse = platformUserRepository.findAllPlatformUser(pageable);


        if (platformUserResponse.getNumberOfElements() == 0) {
            return new PagedResponse<>(Collections.emptyList(), platformUserResponse.getNumber(),
                    platformUserResponse.getSize(), platformUserResponse.getTotalElements(), platformUserResponse.getTotalPages(), platformUserResponse.isLast());
        }
        List<PlatformUserResponse> platformUserResponseList = platformUserResponse.getContent();

        return new PagedResponse<>(platformUserResponseList, platformUserResponse.getNumber(),
                platformUserResponse.getSize(), platformUserResponse.getTotalElements(), platformUserResponse.getTotalPages(), platformUserResponse.isLast());
    }

    @Override
    public PlatformUserResponse getPlatformUserDetail(Long id) {
        PlatformUserResponse platformUserResponse = platformUserRepository.findByPlatformUserId(id).orElseThrow(()->new NotFoundException(
                new StringBuffer().append("Platform User '")
                        .append(id)
                        .append("' not exist")
                        .toString())
        );
        return platformUserResponse;
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
