package com.idr.metro.controller;

import com.idr.metro.wrappper.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.idr.metro.entity.User;
import com.idr.metro.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authService;

    @Autowired
    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public APIResponse signUp(@RequestBody @Validated SignUpRequest signUpRequest) {
        try {
            APIResponse response = authService.registerUser(signUpRequest);

            if (!response.getError()) {
                return APIResponse.builder()
                        .message("User registered successfully")
                        .body(response.getBody())
                        .error(false)
                        .build();
            }

            return response;  // Return the error response from the service
        } catch (BadCredentialsException e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Invalid username or password")
                    .build();
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Internal Server Error")
                    .build();
        }
    }

    @PostMapping("/login")
    public APIResponse login(@RequestBody @Validated LogInRequest logInRequest, HttpServletRequest request) {
        try {
            return authService.login(logInRequest, request);
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Internal Server Error")
                    .body(logInRequest != null ? logInRequest.getEmail() : null)
                    .build();
        }
    }

    @PostMapping("/resetPassword")
    public APIResponse resetPassword(@RequestBody @Validated ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        try {
            return authService.resetPassword(resetPasswordRequest, request);
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Internal Server Error")
                    .build();
        }
    }

    @PostMapping("/verifyOTP")
    public APIResponse verifyOTP(@RequestBody OTPVerificationRequest otpVerificationRequest) {
        try {
            logger.info("Entering AuthenticationController::verifyOTP method for email: {}", otpVerificationRequest.getEmail());

            if (otpVerificationRequest.getEmail() == null || otpVerificationRequest.getEmail().trim().isEmpty()) {
                String message = "Email is required.";
                logger.warn(message);
                return APIResponse.builder().error(true).message(message).build();
            }

            if (otpVerificationRequest.getOtp() == null || otpVerificationRequest.getOtp().trim().isEmpty()
                    || otpVerificationRequest.getOtp().equalsIgnoreCase("null")) {
                String message = "OTP is required.";
                logger.warn(message);
                return APIResponse.builder().error(true).message(message).build();
            }
            return authService.verifyOTP(otpVerificationRequest);
        } catch (Exception e) {
            String message = "Internal server error.";
            logger.error("Error in AuthenticationController for email {}: {}", otpVerificationRequest.getEmail(), e.toString());
            return APIResponse.builder().error(true).message(message).build();
        }
    }

    @PostMapping("/sendPasswordResetOTP")
    public APIResponse sendPasswordResetOTP(@RequestBody PasswordResetOTPRequest passwordResetOTPRequest) {
        try {
            return authService.sendPasswordResetOTP(passwordResetOTPRequest);
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Internal Server Error")
                    .build();
        }
    }

    @PostMapping("/resendOTP")
    public APIResponse resendOTP(@RequestParam String email) {
        try {
            return authService.resendOTP(email);
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("Internal Server Error")
                    .build();
        }
    }

    @GetMapping("/getUserById")
    public APIResponse getUserById(@RequestParam Long id) {
        try {
            User user = authService.getUserById(id);
            if (user == null) {
                return APIResponse.builder()
                        .error(true)
                        .message("User not found.")
                        .build();
            }
            return APIResponse.builder().body(user).build();
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("An error occurred while retrieving the user.")
                    .build();
        }
    }

    @GetMapping("/getAllUsers")
    public APIResponse findAllUsers() {
        try {
            return APIResponse.builder()
                    .body(authService.findAllUsers())
                    .build();
        } catch (Exception e) {
            return APIResponse.builder()
                    .error(true)
                    .message("An error occurred while retrieving users.")
                    .build();
        }
    }
}
