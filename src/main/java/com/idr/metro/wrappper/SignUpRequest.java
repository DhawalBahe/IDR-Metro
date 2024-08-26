package com.idr.metro.wrappper;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;


import com.idr.metro.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpRequest implements Serializable {

    @NotNull(message = "Email cannot be blank")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotNull(message = "First Name cannot be blank")
    @NotBlank(message = "First Name cannot be blank")
    private String firstName;

    @NotNull(message = "Last Name cannot be blank")
    @NotBlank(message = "Last Name cannot be blank")
    private String lastName;

    @NotNull(message = "Password cannot be blank")
    @NotBlank(message = "Password cannot be blank")
    private String password;

    @NotNull(message = "roles cannot be blank")
    @NotBlank(message = "roles cannot be blank")
    private List<Role> roles;

}
