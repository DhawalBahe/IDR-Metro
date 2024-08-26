package com.idr.metro.wrappper;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class OTPVerificationResponse {

    private String email;
    private String message;
}

