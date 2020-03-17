package com.mopito.service;

import com.mopito.model.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findUsers(int page, int limit);

    UserDto createUser(UserDto userDto);

    UserDto findWithUsername(String username);

    void deleteUser(String username);

    UserDto editUser(String username, UserDto userDto);
}
