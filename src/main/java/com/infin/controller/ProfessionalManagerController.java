package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;
import com.infin.repository.UserRepository;
import com.infin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/professional-manager")
public class ProfessionalManagerController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthService authService;


}
