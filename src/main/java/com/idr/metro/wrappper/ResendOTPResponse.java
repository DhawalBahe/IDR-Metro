package com.idr.metro.wrappper;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ResendOTPResponse {

    private String email;
    private String message;
}
