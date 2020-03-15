package com.mopito.model.response;

import lombok.Setter;

@Setter
public class AuthenticationResponse {
    private Long userId;
    private String token;
}
