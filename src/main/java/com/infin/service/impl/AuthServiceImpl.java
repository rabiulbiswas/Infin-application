package com.infin.service.impl;

import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.SignUpRequest;
import com.infin.entity.Role;
import com.infin.entity.RoleName;
import com.infin.entity.User;
import com.infin.exception.AppException;
import com.infin.repository.RoleRepository;
import com.infin.repository.UserRepository;
import com.infin.security.JwtTokenProvider;
import com.infin.security.UserPrincipal;
import com.infin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Override
    public User userSignUp(@Valid SignUpRequest signUpRequest) {
        // Creating user's account
        User user =User.build(0L,signUpRequest.getName(),signUpRequest.getUsername(),
                signUpRequest.getEmail(),signUpRequest.getPassword(),
                signUpRequest.getMobile(),signUpRequest.getNationality(), Set.of());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

       return userRepository.save(user);
    }

    @Override
    public JwtAuthenticationResponse userSignIn(LoginRequest loginRequest) {

        LoginRequest.build(loginRequest.getUsernameOrEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsernameOrEmail(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = tokenProvider.generateToken(authentication);
        UserPrincipal userDetails = (UserPrincipal) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
        return new JwtAuthenticationResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }
}
