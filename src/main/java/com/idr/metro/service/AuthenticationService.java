package com.idr.metro.service;

import com.idr.metro.entity.User;
import com.idr.metro.wrappper.*;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {



    APIResponse registerUser(SignUpRequest signUpRequest);

	APIResponse login(LogInRequest logInRequest, HttpServletRequest request);

	APIResponse verifyOTP(OTPVerificationRequest otpVerificationRequest);

	APIResponse resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest request);

	APIResponse sendPasswordResetOTP(PasswordResetOTPRequest passwordResetOTPRequest);

	APIResponse resendOTP(String email);

	User getUserById(Long id);

	Object findAllUsers();
}
