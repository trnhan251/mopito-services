package com.mopito.controller;

import com.mopito.model.dto.UserDto;
import com.mopito.model.response.UserResponse;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> getAllUsers() {
        List<UserResponse> userResponseList = userService.findAllUsers().stream()
                .map(this::convertToUserResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(userResponseList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserWithUsername(@PathVariable String username) {
        UserDto userDto = userService.findWithUsername(username);
        return ResponseEntity.ok().body(convertToUserResponse(userDto));
    }


    private UserResponse convertToUserResponse(UserDto userDto) {
        return this.mapper.map(userDto, UserResponse.class);
    }
}
