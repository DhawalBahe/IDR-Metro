package com.idr.metro.wrappper;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OTPVerificationRequest {

    @javax.validation.constraints.Email(message = "Please provide a valid email address")
    @javax.validation.constraints.NotBlank(message = "Email cannot be blank")
    @javax.validation.constraints.NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

   @JsonProperty("otp")
    private String otp;



}
