package com.infin.controller;

import com.infin.dto.*;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.security.CurrentUser;
import com.infin.security.UserPrincipal;
import com.infin.service.AuthService;
import com.infin.service.ClientAdminService;
import com.infin.service.PasswordService;
import com.infin.service.ProfessionalAdminService;
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
    UserRepository userRepository;

    @Autowired
    private AuthService authService;
    @Autowired
    private PasswordService passwordService;

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
        return professionalAdminService.getAllClients(currentUser, page, size);
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

    @PostMapping("/change-password")
    @PreAuthorize("hasRole('PROFESSIONAL_ADMIN')")
    public ResponseEntity<?> changePassword(@CurrentUser UserPrincipal currentUser,@RequestBody ChangePasswordRequest changePasswordRequest){

        ResponseEntity<String> resp = null;
        try {
            passwordService.changePassword(currentUser.getId(),changePasswordRequest);
            resp = new ResponseEntity(new ApiResponse(true, "You have successfully changed your password."),
                    HttpStatus.OK);

        } catch (NotFoundException nfe) {
            throw nfe;
        } catch (Exception e) {
            e.printStackTrace();
            resp = new ResponseEntity<String>(
                    "Something went wrong,please try again",
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

}
