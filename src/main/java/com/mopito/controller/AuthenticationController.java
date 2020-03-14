package com.mopito.controller;

import com.mopito.dto.UserDto;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username and password!");
        }

        AuthenticationResponse authenticationResponse = getAuthenticationResponse(authenticationRequest.getUsername());
        return ResponseEntity.ok().body(authenticationResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userRequest, userDto);

        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.ok().body(createdUser.getUsername());
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
