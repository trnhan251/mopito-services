package com.mopito.model.request;

import com.mopito.enums.UserRole;
import lombok.Getter;

@Getter
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}
