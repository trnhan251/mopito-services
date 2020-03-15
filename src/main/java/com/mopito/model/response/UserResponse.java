package com.mopito.model.response;

import com.mopito.enums.UserRole;
import lombok.Setter;

@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
}
