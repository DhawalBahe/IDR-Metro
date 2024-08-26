package com.idr.metro.wrappper;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class PasswordResetOTPRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    private String email;

}

