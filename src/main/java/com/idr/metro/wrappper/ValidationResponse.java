package com.idr.metro.wrappper;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class ValidationResponse {
        private boolean valid;
        private String message;
    }

