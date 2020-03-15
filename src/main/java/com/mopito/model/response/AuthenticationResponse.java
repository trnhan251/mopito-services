package com.mopito.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AuthenticationResponse {
    private Long userId;
    private String token;
}
