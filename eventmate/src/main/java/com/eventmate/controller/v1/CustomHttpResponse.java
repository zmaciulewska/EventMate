package com.eventmate.controller.v1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomHttpResponse {
    Integer status;
    String message;
}
