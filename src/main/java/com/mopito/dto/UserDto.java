package com.mopito.dto;

import com.mopito.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@ToString
public class UserDto implements Serializable {
    private static final long serialVersionUID = 779418356081323429L;
    private Long id;
    private String username;
    private String password;
    private String encryptedPassword;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private Date createdOn;
}
