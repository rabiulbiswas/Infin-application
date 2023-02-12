package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.PagedResponse;
import com.infin.dto.SignUpRequest;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.security.CurrentUser;
import com.infin.security.UserPrincipal;
import com.infin.service.AuthService;
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

@RestController
@RequestMapping("/api/professional-admin")
public class ProfessionalAdminController {

    @Autowired
    private ProfessionalAdminService professionalAdminService;
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
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
            ClientAdminResponse clientAdminResponse =  professionalAdminService.getClientAdminDetail(clientDetailId);
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
            ProfessionalAdminResponse professionalAdminResponse =  professionalAdminService.getProfessionalAdminDetail(currentUser);
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
