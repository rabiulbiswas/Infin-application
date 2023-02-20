package com.infin.controller;

import com.infin.dto.*;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.platform.admin.PlatformAdminResponse;
import com.infin.dto.platform.manager.PlatformManagerResponse;
import com.infin.dto.platform.user.PlatformUserResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.security.CurrentUser;
import com.infin.security.UserPrincipal;
import com.infin.service.*;
import com.infin.util.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/platform-admin")
public class PlatformAdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private PlatformAdminService platformAdminService;
    @Autowired
    private PlatformManagerService platformManagerService;
    @Autowired
    private PlatformUserService platformUserService;
    @Autowired
    private ProfessionalAdminService professionalAdminService;
    @Autowired
    private ClientAdminService clientAdminService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        ResponseEntity<?> resp = null;
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authService.userSignIn(loginRequest);

            if (jwtAuthenticationResponse.getVerified() == 0) {
                return new ResponseEntity(new ApiResponse(false, "Your account verification in-progress!"),
                        HttpStatus.OK);
            }
            resp = new ResponseEntity<JwtAuthenticationResponse>(jwtAuthenticationResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            resp = new ResponseEntity(new ApiResponse(false, "Unable to sign in!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/platform-admin-profile-detail")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getPlatformProfileDetail(@CurrentUser UserPrincipal currentUser) {
        ResponseEntity<?> resp = null;
        try {
            PlatformAdminResponse platformAdminResponse = platformAdminService.getPlatformAdminDetail(currentUser);
            resp = new ResponseEntity<PlatformAdminResponse>(platformAdminResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch platform Admin Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/professional-admin")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getAllProfessionalAdmin(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<ProfessionalAdminResponse> professionalAdminResponse = platformAdminService.getProfessionalAdminList(page, size);
            resp = new ResponseEntity<PagedResponse<ProfessionalAdminResponse>>(professionalAdminResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch Client Admin Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/client-admin")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getAllClientAdmin(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<ClientAdminResponse> clientAdminResponse = platformAdminService.getClientAdminList(page, size);
            resp = new ResponseEntity<PagedResponse<ClientAdminResponse>>(clientAdminResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch Client Admin list", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PostMapping("/update-profile")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> updatePlatformAdminProfile(@CurrentUser UserPrincipal currentUser, @RequestBody UserRequestDto updateRequest) {

        ResponseEntity<String> resp = null;
        try {
            platformAdminService.updatePlatformAdminProfile(currentUser.getId(), updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Platform Admin Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to Update Platform Admin profile",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PostMapping("/create-platform-manager")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> createPlatformManager(@Valid @RequestBody UserRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = authService.userSignUp(signUpRequest);

        return new ResponseEntity(new ApiResponse(true, "Platform manager created successfully"), HttpStatus.CREATED);
    }

    @PostMapping("/create-platform-user")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> createPlatformUser(@Valid @RequestBody UserRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = authService.userSignUp(signUpRequest);

        return new ResponseEntity(new ApiResponse(true, "Platform user created successfully"), HttpStatus.CREATED);
    }

    @PatchMapping("/verify-account/{id}/{status}")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> verifyUserAccountById(@PathVariable Long id, @PathVariable Long status) {
        ResponseEntity<?> resp = null;
        try {
            platformAdminService.verifyUserAccountById(id, status);
            resp = new ResponseEntity<String>(
                    "Account verified successfully", HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Something went wrong ,please try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/platform-manager")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getAllPlatformManager(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                               @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<PlatformManagerResponse> platformManagerResponse = platformManagerService.getAllPlatformManager(page,size);
            resp = new ResponseEntity<PagedResponse<PlatformManagerResponse>>(platformManagerResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch platform manager list", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/platform-user")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getAllPlatformUser(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                   @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        ResponseEntity<?> resp = null;
        try {
            PagedResponse<PlatformUserResponse> platformManagerResponse = platformUserService.getAllPlatformUser(page,size);
            resp = new ResponseEntity<PagedResponse<PlatformUserResponse>>(platformManagerResponse, HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to fetch platform user list", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @GetMapping("/platform-manaer-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getPlatformManagerDetail(@PathVariable Long id) {
        ResponseEntity<?> resp = null;
        try {
            PlatformManagerResponse platformManagerResponse = platformManagerService.getPlatformManagerDetail(id);
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

    @GetMapping("/platform-user-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
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

    @GetMapping("/client-admin-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getAllClients(@PathVariable Long id) {
        ResponseEntity<?> resp= null;
        try {
            ClientAdminResponse clientAdminResponse =  clientAdminService.getClientAdminDetail(id);
            resp= new ResponseEntity<ClientAdminResponse> (clientAdminResponse,HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        }catch (Exception e) {
            e.printStackTrace();
            resp= new ResponseEntity<String>(
                    "Unable to fetch Client Admin Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;

    }

    @GetMapping("/professional-admin-profile-detail/{id}")
    @PreAuthorize("hasRole('PLATFORM_ADMIN')")
    public ResponseEntity<?> getProfessionalAdminDetail(@PathVariable Long id) {
        ResponseEntity<?> resp= null;
        try {
            ProfessionalAdminResponse professionalAdminResponse =  professionalAdminService.getProfessionalAdminDetail(id);
            resp= new ResponseEntity<ProfessionalAdminResponse> (professionalAdminResponse,HttpStatus.OK);

        }catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp= new ResponseEntity<String>(
                    "Unable to fetch Professional Admin Profile", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }
}
