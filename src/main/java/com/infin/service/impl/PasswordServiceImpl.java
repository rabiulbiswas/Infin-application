package com.infin.service.impl;

import com.infin.dto.ChangePasswordRequest;
import com.infin.dto.ForgetPasswordRequest;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.repository.UserRepository;
import com.infin.service.EmailService;
import com.infin.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordServiceImpl implements PasswordService {


    @Value("${app.reset.password.url}")
    private String passwordResetAppUrl;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;


    @Override
    public Boolean findUserByEmail(ForgetPasswordRequest forgetPasswordRequest) {
        Boolean success = true;
        Optional<User> optional = userRepository.findByEmail(forgetPasswordRequest.getEmail());
        if (!optional.isPresent()) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("We didn't find an account for that e-mail address.")
                            .toString());
        }
        User user = optional.get();
        user.setResetToken(UUID.randomUUID().toString());
        userRepository.save(user);

        try {
            // Email message
            SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
            passwordResetEmail.setFrom("support@infin.com");
            passwordResetEmail.setTo(user.getEmail());
            passwordResetEmail.setSubject("Password Reset Request");
            passwordResetEmail.setText("To reset your password, click the link below:\n" + passwordResetAppUrl
                    + "?token=" + user.getResetToken());

            emailService.sendEmail(passwordResetEmail);
             success = true;
        }catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;
    }

    @Override
    public Optional<User> findUserByResetToken(String resetToken) {

        Optional<User> optional = userRepository.findByResetToken(resetToken);
        if (!optional.isPresent()) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Oops!  This is an invalid password reset link.")
                            .toString());
        }
        return optional;
    }

    @Override
    public void updateNewPassword(UserRequestDto restNewPasswordRequest) {
        Optional<User> optional = userRepository.findByResetToken(restNewPasswordRequest.getResetToken());
        if (!optional.isPresent()) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Oops!  This is an invalid password reset link.")
                            .toString());
        }

        User resetUserPassword = optional.get();
        resetUserPassword.setPassword(passwordEncoder.encode(restNewPasswordRequest.getPassword()));
        resetUserPassword.setResetToken(null);
        userRepository.save(resetUserPassword);
    }

    @Override
    public void changePassword(Long id, ChangePasswordRequest changePasswordRequest) {
        Optional<User> optional = userRepository.findById(id);
        User changeUserPassword = optional.get();
        if (passwordEncoder.matches(changeUserPassword.getPassword(), passwordEncoder.encode(changePasswordRequest.getOldPassword()))) {
            throw new NotFoundException(
                    new StringBuffer()
                            .append("Please eneter your correct old password")
                            .toString());
        }

        changeUserPassword.setPassword(passwordEncoder.encode(changePasswordRequest.getNewPassword()));
        userRepository.save(changeUserPassword);
    }
}
