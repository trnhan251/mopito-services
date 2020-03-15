package com.mopito.model.request;

import com.mopito.model.enums.UserRole;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRequest {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}
