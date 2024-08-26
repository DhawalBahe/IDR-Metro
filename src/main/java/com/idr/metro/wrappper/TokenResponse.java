package com.idr.metro.wrappper;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TokenResponse {

    private String email;
    private String token;
}
