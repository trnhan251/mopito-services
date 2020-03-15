package com.mopito.controller;

import com.mopito.dto.UserDto;
import com.mopito.model.response.UserResponse;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    private UserResponse convertToUserResponse(UserDto userDto) {
        return modelMapper.map(userDto, UserResponse.class);
    }
}
