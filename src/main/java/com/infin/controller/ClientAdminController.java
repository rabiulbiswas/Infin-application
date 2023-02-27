package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.UserRequestDto;
import com.infin.dto.client.ClientAdminResponse;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.security.CurrentUser;
import com.infin.security.UserPrincipal;
import com.infin.service.AuthService;
import com.infin.service.ClientAdminService;
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
@RequestMapping("/api/client-admin")
public class ClientAdminController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private ClientAdminService clientAdminService;

    @GetMapping("/profile-detail")
    @PreAuthorize("hasRole('CLIENT_ADMIN')")
    public ResponseEntity<?> getAllClients(@CurrentUser UserPrincipal currentUser) {
        ResponseEntity<?> resp= null;
        try {
            ClientAdminResponse clientAdminResponse =  clientAdminService.getClientAdminDetail(currentUser.getId());
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


}
