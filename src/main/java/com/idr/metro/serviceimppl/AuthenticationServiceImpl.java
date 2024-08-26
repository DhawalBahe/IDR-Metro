package com.idr.metro.serviceimppl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.idr.metro.Auth.JwtUtil;
import com.idr.metro.entity.Role;
import com.idr.metro.repository.RoleRepository;
import com.idr.metro.repository.UserRepository;
import com.idr.metro.wrappper.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.idr.metro.entity.User;
import com.idr.metro.repository.AuthenticationRepository;
import com.idr.metro.service.AuthenticationService;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    AuthenticationRepository authenticationRepository;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

     @Autowired
    private PasswordEncoder PasswordEncoder;

    @Autowired
    private IOTPService otpService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public APIResponse registerUser(SignUpRequest signUpRequest) {
        logger.info("Entering AuthServiceImpl :: registerUser method...");

        try {
            ValidationResponse validationResult = validateSignUpRequest(signUpRequest);
            if (!validationResult.isValid()) {
                String message = validationResult.getMessage();
                logger.error(message);
                return APIResponse.builder().message(message).error(true).build();
            }

            String email = signUpRequest.getEmail();
            String firstName = signUpRequest.getFirstName();
            String lastName = signUpRequest.getLastName();
            String password = signUpRequest.getPassword();

            // Validate input data
            if (!isValidEmail(email)) {
                String message = ValidationConstants.INVALID_EMAIL;
                logger.error(message);
                return APIResponse.builder().message(message).error(true).build();
            }
            if (!isValidFirstName(firstName)) {
                String message = ValidationConstants.INVALID_NAME;
                logger.error(message);
                return APIResponse.builder().message(message).error(true).build();
            }
            if (!isValidLastName(lastName)) {
                String message = ValidationConstants.INVALID_NAME;
                logger.error(message);
                return APIResponse.builder().message(message).error(true).build();
            }
            if (!isValidPassword(password)) {
                String message = ValidationConstants.INVALID_PASSWORD;
                logger.error(message);
                return APIResponse.builder().message(message).error(true).build();
            }

            // Retrieve roles from the request
            List<Role> roles =signUpRequest.getRoles();

            List<Role> persistedRoles = new ArrayList<>();
            for (Role role : roles) {
                Optional<Role> existingRole = roleRepository.findByName(role.getName());
                if (!existingRole.isPresent()) {
                    role = roleRepository.save(role);
                } else {
                    role = existingRole.get();
                }
                persistedRoles.add(role);
            }

            // Create new user entity
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setPassword(PasswordEncoder.encode(password));
            user.setRoles(persistedRoles);

            // Save the user entity
            user = userRepository.save(user);

            String message = ValidationConstants.USER_SAVED_SUCCESSFULLY;
            logger.info(message);
            logger.info("AuthServiceImpl :: registerUser method end... Response: {}", user);

            return APIResponse.builder().message(message).body(user).error(false).build();
        } catch (Exception e) {
            String message = "Internal Server Error";
            logger.error(message, e);
            return APIResponse.builder().message(message).error(true).build();
        }
    }

    @Override
    public APIResponse login(LogInRequest logInRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public APIResponse verifyOTP(OTPVerificationRequest otpVerificationRequest) {
        return null;
    }

    @Override
    public APIResponse resetPassword(ResetPasswordRequest resetPasswordRequest, HttpServletRequest request) {
        return null;
    }

    @Override
    public APIResponse sendPasswordResetOTP(PasswordResetOTPRequest passwordResetOTPRequest) {
        return null;
    }

    @Override
    public APIResponse resendOTP(String email) {
        return null;
    }

    @Override
    public User getUserById(Long id) {
        return null;
    }

    @Override
    public Object findAllUsers() {
        return null;
    }
}
