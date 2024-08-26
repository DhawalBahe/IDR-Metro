package com.idr.metro.wrappper;

import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Validated
@EqualsAndHashCode
@ToString
public class ResetPasswordRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "New password cannot be blank")
    @NotNull(message = "New password cannot be null")
    @Size(min = 8, message = "New password must be at least 8 characters long")
    private String newPassword;

    @NotBlank(message = "Confirm password cannot be blank")
    @NotNull(message = "Confirm password cannot be null")
    @Size(min = 8, message = "Confirm password must be at least 8 characters long")
    private String confirmPassword;

}


