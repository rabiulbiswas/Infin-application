package com.infin.controller;

import com.infin.dto.*;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.dto.professional.manager.ProfessionalManagerResponse;
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
@RequestMapping("/api/professional-admin")
public class ProfessionalAdminController {

    @Autowired
    private ProfessionalAdminService professionalAdminService;

    @Autowired
    private ClientAdminService clientAdminService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordService passwordService;
    @Autowired
    private ProfessionalManagerService professionalManagerService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequestDto signUpRequest) {
        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = authService.userSignUp(signUpRequest);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{email}")
                .buildAndExpand(result.getEmail()).toUri();

        return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfessionalAdmin(@CurrentUser UserPrincipal currentUser, @RequestBody UserRequestDto updateRequest){

        ResponseEntity<String> resp = null;
        try {
            professionalAdminService.updateProfessionalAdminProfile(currentUser.getId(),updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Professional Admin Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Unable to Update Professional Admin profile",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }


    @GetMapping("/client")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public PagedResponse<ClientAdminResponse> getAllClients(@CurrentUser UserPrincipal currentUser,
                                                       @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                       @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return clientAdminService.getAllClientAdminCreatedBy(currentUser, page, size);
    }

    @GetMapping("/client-admin-profile-detail/{clientDetailId}")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> getAllClients(@PathVariable Long clientDetailId) {
        ResponseEntity<?> resp= null;
        try {
            ClientAdminResponse clientAdminResponse =  clientAdminService.getClientAdminDetail(clientDetailId);
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

    @GetMapping("/professional-admin-profile-detail")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> getProfessionalAdminDetail(@CurrentUser UserPrincipal currentUser) {
        ResponseEntity<?> resp= null;
        try {
            ProfessionalAdminResponse professionalAdminResponse =  professionalAdminService.getProfessionalAdminDetail(currentUser.getId());
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

    @PostMapping("/create-professional-manager")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> createProfessionalManager(@Valid @RequestBody UserRequestDto signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
                    HttpStatus.BAD_REQUEST);
        }

        User result = authService.userSignUp(signUpRequest);

        return new ResponseEntity(new ApiResponse(true, "professional manager created successfully"), HttpStatus.CREATED);
    }

    @GetMapping("/professional-manager-list")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public PagedResponse<ProfessionalManagerResponse> getProfessionalManagerByProfessionalAdmin(@CurrentUser UserPrincipal currentUser,
                                                                                                @RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) int page,
                                                                                                @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) int size) {
        return professionalManagerService.getProfessionalManagerByProfessionalAdmin(currentUser.getId(), page, size);
    }

    @GetMapping("/professional-manager-profile-detail/{professionalManagerId}")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> getProfessionalManagerProfile(@PathVariable Long professionalManagerId) {
        ResponseEntity<?> resp= null;
        try {
            ProfessionalManagerResponse professionalManagerResponse =  professionalManagerService.getProfessionalManagerProfile(professionalManagerId);
            resp= new ResponseEntity<ProfessionalManagerResponse> (professionalManagerResponse,HttpStatus.OK);
        } catch (NotFoundException nfe) {
            throw nfe;
        }catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to fetch professional manager Profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;

    }

    @PutMapping("/update-professional-manager-profile/{professionalManagerId}")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> updateProfessionalManagerProfile(@PathVariable Long professionalManagerId, @RequestBody UserRequestDto updateRequest){

        ResponseEntity<String> resp = null;
        try {
            professionalManagerService.updateProfessionalManagerProfile(professionalManagerId,updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Professional Manager Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to Update professional manager Profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

    @PutMapping("/update-client-admin-profile/{clientAdminId}")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> updateClientAdmin(@PathVariable Long clientAdminId, @RequestBody UserRequestDto updateRequest){

        ResponseEntity<String> resp = null;
        try {
            clientAdminService.updateClientAdminProfile(clientAdminId, updateRequest);
            resp = new ResponseEntity(new ApiResponse(true, "Client Admin Profile Updated Successfully"),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity(new ApiResponse(false, "Unable to Update Client Admin profile"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

}
