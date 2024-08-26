package com.idr.metro.wrappper;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LogInRequest {

    @Email(message = "Please provide a valid email address")
    @NotBlank(message = "Email cannot be blank")
    @NotNull(message = "Email cannot be null")
    @JsonProperty("email")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    @NotNull(message = "Password cannot be null")
    @JsonProperty("password")
    private String password;

}

