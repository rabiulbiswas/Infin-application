package com.infin.service.impl;

import com.infin.dto.JwtAuthenticationResponse;
import com.infin.dto.LoginRequest;
import com.infin.dto.SignUpRequest;
import com.infin.entity.Role;
import com.infin.entity.RoleName;
import com.infin.entity.User;
import com.infin.entity.client.ClientAdminDetail;
import com.infin.entity.professional.admin.ProfessionalAdminDetail;
import com.infin.repository.RoleRepository;
import com.infin.repository.UserRepository;
import com.infin.security.JwtTokenProvider;
import com.infin.security.UserPrincipal;
import com.infin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
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
      /*  ProfessionalAdminRequest ProfessionalAdminDetails =  new ProfessionalAdminRequest();
        User user =User.build(0L,signUpRequest.getName(),
                signUpRequest.getEmail(),signUpRequest.getPassword(),
                signUpRequest.getMobile(),0L,0L,0L,ProfessionalAdminDetails,Set.of());

        ProfessionalAdminDetail professionalAdminDetail = ProfessionalAdminDetail.build(0L,
                signUpRequest.getProfessionalAdminDetail().getMembershipNumber(),
                signUpRequest.getProfessionalAdminDetail().getContactAddress()); */
        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setMobile(signUpRequest.getMobile());

        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        Set<String> strRoles = signUpRequest.getRole();

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication instanceof AnonymousAuthenticationToken) {
            user.setCreatedBy(0l);
            user.setUpdatedBy(0l);

        }else {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            user.setCreatedBy(userPrincipal.getId());
            user.setUpdatedBy(userPrincipal.getId());
        }
        user.setVerified(0l);
        if (strRoles == null) {
            roleRepository.findByName(RoleName.NOT_USER_ROLE)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

        } else {
            strRoles.forEach(role -> {
                if (role.equals("platform-admin")) {
                    Role platformAdminRole = roleRepository.findByName(RoleName.ROLE_PLATFORM_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                    user.setRoles(Collections.singleton(platformAdminRole));
                } else if (role.equals("platform-manager")) {
                    Role platformManagerRole = roleRepository.findByName(RoleName.ROLE_PLATFORM_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    user.setRoles(Collections.singleton(platformManagerRole));
                } else if (role.equals("platform-user")) {
                    Role platformUserRole = roleRepository.findByName(RoleName.ROLE_PLATFORM_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    user.setRoles(Collections.singleton(platformUserRole));
                } else if (role.equals("professional-admin")) {
                    Role professionalAdminRole = roleRepository.findByName(RoleName.ROLE_PROFESSIONAL_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    ProfessionalAdminDetail professionalAdminDetail = new ProfessionalAdminDetail();
                    professionalAdminDetail.setMembershipNumber(signUpRequest.getProfessionalAdminDetail().getMembershipNumber());
                    professionalAdminDetail.setContactAddress(signUpRequest.getProfessionalAdminDetail().getContactAddress());

                    user.setRoles(Collections.singleton(professionalAdminRole));
                    user.setProfessionalAdminDetails(professionalAdminDetail);
                    professionalAdminDetail.setUser(user);
                } else if (role.equals("professional-manager")) {
                    Role professionalManagerRole = roleRepository.findByName(RoleName.ROLE_PROFESSIONAL_MANAGER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    user.setRoles(Collections.singleton(professionalManagerRole));
                } else if (role.equals("professional-user")) {
                    Role professionalUserRole = roleRepository.findByName(RoleName.ROLE_PROFESSIONAL_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    user.setRoles(Collections.singleton(professionalUserRole));
                } else if (role.equals("client-admin")) {
                    Role clientAdminRole = roleRepository.findByName(RoleName.CLIENT_ADMIN)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    ClientAdminDetail clientAdminDetail = getClientAdminDetail(signUpRequest);
                    user.setRoles(Collections.singleton(clientAdminRole));
                    user.setClientAdminDetails(clientAdminDetail);
                    clientAdminDetail.setUser(user);
                } else if (role.equals("client-user")) {
                    Role clientUserRole = roleRepository.findByName(RoleName.CLIENT_USER)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));

                    user.setRoles(Collections.singleton(clientUserRole));
                } else {
                    Role userRole = roleRepository.findByName(RoleName.NOT_USER_ROLE)
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                }
            });
        }
       return userRepository.save(user);
    }

    private static ClientAdminDetail getClientAdminDetail(SignUpRequest signUpRequest) {
        ClientAdminDetail clientAdminDetail = new ClientAdminDetail();
        clientAdminDetail.setCompanyName(signUpRequest.getClientAdminDetail().getCompanyName());
        clientAdminDetail.setPanNumber(signUpRequest.getClientAdminDetail().getPanNumber());
        clientAdminDetail.setGstNumber(signUpRequest.getClientAdminDetail().getGstNumber());
        clientAdminDetail.setBusinessType(signUpRequest.getClientAdminDetail().getBusinessType());
        clientAdminDetail.setCommunicationAddress(signUpRequest.getClientAdminDetail().getCommunicationAddress());
        return clientAdminDetail;
    }

    @Override
    public JwtAuthenticationResponse userSignIn(LoginRequest loginRequest) {

        LoginRequest.build(loginRequest.getEmail(),loginRequest.getPassword());
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
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
                userDetails.getName(),
                userDetails.getEmail(),
                userDetails.getVerified(),
                roles);
    }
}
