package com.mopito.controller;

import com.mopito.model.dto.UserDto;
import com.mopito.model.request.AuthenticationRequest;
import com.mopito.model.request.UserRequest;
import com.mopito.model.response.AuthenticationResponse;
import com.mopito.service.UserService;
import com.mopito.util.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsServiceImpl;
    private final JwtUtil jwtUtil;
    private final UserService userService;

    public AuthenticationController(AuthenticationManager authenticationManager, UserDetailsService userDetailsServiceImpl, JwtUtil jwtUtil, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password!");
        }

        String username = authenticationRequest.getUsername();
        AuthenticationResponse authenticationResponse = getAuthenticationResponse(username);
        UserDto userDto = userService.findWithUsername(username);
        authenticationResponse.setUsername(userDto.getUsername());
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);

        UserDto createdUser = userService.createUser(userDto);
        AuthenticationResponse authenticationResponse = getAuthenticationResponse(createdUser.getUsername());
        authenticationResponse.setUsername(createdUser.getUsername());
        return ResponseEntity.ok().body(authenticationResponse);
    }

    private AuthenticationResponse getAuthenticationResponse(String username) {
        //get user details from service
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

        //generate token
        final String token = jwtUtil.generateToken(userDetails);

        //create authentication response
        AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(token);

        return authenticationResponse;
    }
}
