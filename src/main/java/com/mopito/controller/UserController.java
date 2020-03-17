package com.mopito.controller;

import com.mopito.model.dto.UserDto;
import com.mopito.model.response.UserResponse;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final ModelMapper mapper;

    public UserController(UserService userService, ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<?> getUsers(@RequestParam(value = "page", defaultValue = "1") int page,
                                      @RequestParam(value = "limit", defaultValue = "50") int limit) {
        page--;
        List<UserResponse> userResponseList = userService.findUsers(page, limit).stream()
                .map(this::convertToUserResponse).collect(Collectors.toList());
        return ResponseEntity.ok().body(userResponseList);
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getUserWithUsername(@PathVariable String username) {
        UserDto userDto = userService.findWithUsername(username);
        return ResponseEntity.ok().body(convertToUserResponse(userDto));
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> editUser(@PathVariable String username, @RequestBody UserResponse userResponse) {

        return ResponseEntity.ok().body("Edit a user");
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<?> deleteUser(@PathVariable String username) {
        userService.deleteUser(username);
        return ResponseEntity.ok().body(username + "'s account is deleted");
    }


    private UserResponse convertToUserResponse(UserDto userDto) {
        return this.mapper.map(userDto, UserResponse.class);
    }
}
