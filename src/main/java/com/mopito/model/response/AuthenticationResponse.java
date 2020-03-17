package com.mopito.model.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationResponse {
    private String username;
    private String token;
}
