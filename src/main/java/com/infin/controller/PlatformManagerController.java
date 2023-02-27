package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.PagedResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.dto.platform.user.PlatformUserResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.security.CurrentUser;
import com.infin.security.UserPrincipal;
import com.infin.service.ClientAdminService;
import com.infin.service.PlatformManagerService;
import com.infin.service.PlatformUserService;
import com.infin.service.ProfessionalAdminService;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/platform-manager")
public class PlatformManagerController {

    @Autowired
    private ProfessionalAdminService professionalAdminService;

    @Autowired
    private ClientAdminService clientAdminService;

    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private PlatformManagerService platformManagerService;
    @Autowired
    private UserRepository userRepository;


    @GetMapping("/profile")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> getPlatformManagerDetail(@CurrentUser UserPrincipal currentUser) {
        ResponseEntity<?> resp = null;
        try {
            PlatformManagerResponse platformManagerResponse = platformManagerService.getPlatformManagerDetail(currentUser.getId());
            resp = new ResponseEntity<PlatformManagerResponse>(platformManagerResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch platform manager Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
    @PostMapping("/update-profile")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> updatePlatformManagerProfile(@CurrentUser UserPrincipal currentUser, @RequestBody UserRequestDto updateRequest) {

        ResponseEntity<String> resp = null;
        try {
            platformManagerService.updatePlatformManagerProfile(currentUser.getId(), updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Platform Manager Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to Update Platform Manager profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
    @GetMapping("/platform-user")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> getPlatformUserByPlatformManager(@CurrentUser UserPrincipal currentUser, @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<PlatformUserResponse> platformUserResponse = platformUserService.findAllPlatformUserByPlatformManagerId(currentUser.getId(),page, size);
            resp = new ResponseEntity<PagedResponse<PlatformUserResponse>>(platformUserResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to fetch platform user list"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/professional-admin")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> getAllProfessionalAdmin(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<ProfessionalAdminResponse> professionalAdminResponse = professionalAdminService.getProfessionalAdminList(page, size);
            resp = new ResponseEntity<PagedResponse<ProfessionalAdminResponse>>(professionalAdminResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();

            resp = new ResponseEntity(new ApiResponse(false, "Unable to fetch professional Admin list"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/professional-admin-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> getProfessionalAdminDetail(@PathVariable Long id) {
        ResponseEntity<?> resp= null;
        try {
            ProfessionalAdminResponse professionalAdminResponse =  professionalAdminService.getProfessionalAdminDetail(id);
            resp= new ResponseEntity<ProfessionalAdminResponse> (professionalAdminResponse,HttpStatus.OK);

        }catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to fetch Professional Admin Profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PutMapping("/update-professional-admin-profile/{id}")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> updateProfessionalAdmin(@PathVariable Long id, @RequestBody UserRequestDto updateRequest){

        ResponseEntity<String> resp = null;
        try {
            professionalAdminService.updateProfessionalAdminProfile(id,updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Professional Admin Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to Update Professional Admin profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/platform-user-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> getPlatformUserDetail(@PathVariable Long id) {
        ResponseEntity<?> resp = null;
        try {
            PlatformUserResponse platformUserResponse = platformUserService.getPlatformUserDetail(id);
            resp = new ResponseEntity<PlatformUserResponse>(platformUserResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch platform user Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PutMapping("/update-platform-user-profile/{id}")
    @PreAuthorize("hasRole('PLATFORM_MANAGER')")
    public ResponseEntity<?> updatePlatformUser(@PathVariable Long id, @RequestBody UserRequestDto updateRequest){

        ResponseEntity<String> resp = null;
        try {
            platformUserService.updatePlatformUserProfile(id, updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Platform User Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to Update Platform User profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
}
