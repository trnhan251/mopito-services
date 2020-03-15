package com.mopito.service;

import com.mopito.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto findWithUsername(String username);

    List<UserDto> findAllUsers();
}
