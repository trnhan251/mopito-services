package com.mopito.controller;

import com.mopito.model.dto.UserDto;
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

    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(ModelMapper mapper, UserService userService) {
        this.mapper = mapper;
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAllUsers().stream().map(this::convertToUserResponse).collect(Collectors.toList());
    }

    private UserResponse convertToUserResponse(UserDto userDto) {
        return this.mapper.map(userDto, UserResponse.class);
    }
}
