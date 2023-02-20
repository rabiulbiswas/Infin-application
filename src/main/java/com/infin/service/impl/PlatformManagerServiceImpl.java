package com.infin.service.impl;

import com.infin.dto.PagedResponse;
import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.PlatformManagerRepository;
import com.infin.service.PlatformManagerService;
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
public class PlatformManagerServiceImpl implements PlatformManagerService {
    @Autowired
    private PlatformManagerRepository platformManagerRepository;

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

    private void validatePageNumberAndSize(int page, int size) {
        if (page < 0) {
            throw new BadRequestException("Page number cannot be less than zero.");
        }

        if (size > AppConstants.MAX_PAGE_SIZE) {
            throw new BadRequestException("Page size must not be greater than " + AppConstants.MAX_PAGE_SIZE);
        }
    }
}
