package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.SignUpRequest;
<<<<<<< Updated upstream
import com.infin.entity.User;
import com.infin.repository.UserRepository;
=======
import com.infin.dto.professional.admin.ProfessionalAdminResponse;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.RoleRepository;
import com.infin.repository.UserRepository;
import com.infin.security.JwtTokenProvider;
>>>>>>> Stashed changes
import com.infin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
<<<<<<< Updated upstream
=======
import org.springframework.security.crypto.password.PasswordEncoder;
>>>>>>> Stashed changes
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    UserRepository userRepository;
<<<<<<< Updated upstream


=======
>>>>>>> Stashed changes
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        ResponseEntity<?> resp= null;
        try {
            JwtAuthenticationResponse jwtAuthenticationResponse = authService.userSignIn(loginRequest);

            if(jwtAuthenticationResponse.getVerified()==0){
                return new ResponseEntity(new ApiResponse(false, "Your account verification in-progress!"),
                        HttpStatus.OK);
            }
            resp= new ResponseEntity<JwtAuthenticationResponse> (jwtAuthenticationResponse,HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();

            resp = new ResponseEntity(new ApiResponse(false, "Unable to sign in!"),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return resp;
    }

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
}
