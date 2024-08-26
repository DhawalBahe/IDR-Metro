package com.idr.metro.wrappper;


import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class SignUpResponse {
    private String email;
    private String message;

}
