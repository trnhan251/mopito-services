package com.mopito.controller;

import com.mopito.model.dto.UserDto;
import com.mopito.model.response.UserResponse;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper mapper;

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
        return mapper.map(userDto, UserResponse.class);
    }
}
