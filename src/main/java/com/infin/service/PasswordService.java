package com.infin.service;

import com.infin.dto.ChangePasswordRequest;
import com.infin.dto.ForgetPasswordRequest;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;

import java.util.Optional;

public interface PasswordService {

    public Boolean findUserByEmail(ForgetPasswordRequest forgetPasswordRequest);
    public Optional<User> findUserByResetToken(String resetToken);
    public void updateNewPassword(UserRequestDto restNewPasswordRequest);

    public void changePassword(Long id, ChangePasswordRequest changePasswordRequest);
}
