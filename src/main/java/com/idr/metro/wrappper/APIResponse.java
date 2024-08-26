package com.idr.metro.wrappper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class APIResponse {

	 private String message;
	 private Object body;
	 private Boolean error;
}
