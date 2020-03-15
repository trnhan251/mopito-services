package com.mopito.model.request;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class AuthenticationRequest {
    private String username;
    private String password;
}
