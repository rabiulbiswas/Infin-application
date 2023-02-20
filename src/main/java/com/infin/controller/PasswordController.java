package com.infin.controller;

import com.infin.dto.ApiResponse;
import com.infin.dto.ForgetPasswordRequest;
import com.infin.dto.UserRequestDto;
import com.infin.entity.User;
import com.infin.exception.NotFoundException;
import com.infin.service.PasswordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/password")
public class PasswordController {

    @Autowired
    private PasswordService passwordService;

    @PostMapping("/forget-password")
    public ResponseEntity<?> processForgotPassword(@RequestBody ForgetPasswordRequest forgetPasswordRequest){
        ResponseEntity<String> resp = null;
        try {
            Boolean isSuccess =  passwordService.findUserByEmail(forgetPasswordRequest);
            if(!isSuccess){
                resp = new ResponseEntity(new ApiResponse(false, "Something went wrong,please try again"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            resp = new ResponseEntity(new ApiResponse(true, "A password reset link has been sent to " + forgetPasswordRequest.getEmail()),
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
    @GetMapping("/verify-reset-token/{token}")
    public ResponseEntity<?> verifyResetToken(@PathVariable String token){
        ResponseEntity<String> resp = null;
        try {
            Optional<User> verifyResetToken =  passwordService.findUserByResetToken(token);
            if(verifyResetToken.isEmpty()){
                resp = new ResponseEntity(new ApiResponse(false, "Something went wrong,please try again"),
                        HttpStatus.INTERNAL_SERVER_ERROR);
            }
            resp = new ResponseEntity(new ApiResponse(true, "password reset link verfied successfully."),
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

    @PostMapping("/update-new-password")
    public ResponseEntity<?> updateNewPassword(@RequestBody UserRequestDto restNewPasswordRequest){

        ResponseEntity<String> resp = null;
        try {
            passwordService.updateNewPassword(restNewPasswordRequest);
            resp = new ResponseEntity(new ApiResponse(true, "You have successfully reset your password.  You may now login."),
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
