package com.idr.metro.wrappper;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ErrorResponse {

    private HttpStatus httpStatus;
    private String message;
}
