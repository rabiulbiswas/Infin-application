package com.infin.service.impl;

import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.entity.User;
import com.infin.exception.BadRequestException;
import com.infin.exception.NotFoundException;
import com.infin.repository.PlatformAdminRepository;
import com.infin.repository.UserRepository;
import com.infin.security.UserPrincipal;
import com.infin.service.PlatformAdminService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PlatformAdminServiceImpl implements PlatformAdminService {
    @Autowired
    private PlatformAdminRepository platformAdminServiceRepository;
    @Autowired
    private UserRepository userRepository;

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
        updatePlatformAdminldata.setFirstName(updateRequest.getFirstName());
        updatePlatformAdminldata.setLastName(updateRequest.getLastName());
        updatePlatformAdminldata.setMobile(updateRequest.getMobile());
        updatePlatformAdminldata.setUpdatedBy(userPrincipal.getId());
        userRepository.save(updatePlatformAdminldata);
    }


    @Override
    @Transactional
    public Integer verifyUserAccountById(Long status,Long id)
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
