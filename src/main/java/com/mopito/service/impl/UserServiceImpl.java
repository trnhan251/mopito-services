package com.mopito.service.impl;

import com.mopito.config.security.UserDetailsImpl;
import com.mopito.exception.DuplicateRecordException;
import com.mopito.exception.RecordNotFoundException;
import com.mopito.exception.UnauthorizedActionException;
import com.mopito.model.dto.UserDto;
import com.mopito.model.entity.User;
import com.mopito.model.enums.UserRole;
import com.mopito.repository.UserRepository;
import com.mopito.service.AuthenticationService;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ModelMapper mapper;
    private final AuthenticationService authenticationService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder bCryptPasswordEncoder, ModelMapper mapper, AuthenticationService authenticationService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.mapper = mapper;
        this.authenticationService = authenticationService;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new DuplicateRecordException("Record already exists");
        }
        User user = convertToEntity(userDto);
        user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setCreatedOn(new Date());
        User storedUser = userRepository.save(user);
        return convertToDto(storedUser);
    }

    @Override
    public UserDto findWithUsername(String username) {
        return convertToDto(findUserWithUsername(username));
    }

    @Override
    public List<UserDto> findUsers(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<User> userPage = userRepository.findAll(pageable);
        List<User> userList = userPage.getContent();
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDto editUser(String username, UserDto userDto) {
        User user = findUserWithUsername(username);
//        user.set
        return null;
    }

    @Override
    public void deleteUser(String username) {
        User user = findUserWithUsername(username);

        //Get user role
        Object principal = authenticationService.getAuthentication().getPrincipal();
        UserRole userRole = ((UserDetailsImpl) principal).getRole();

        if (!userRole.toString().equals("ADMIN") && !authenticationService.getAuthentication().getName().equals(username)) {
            throw new UnauthorizedActionException("Unauthorized action. User can not delete another user account");
        }

        userRepository.delete(user);
    }

    private User findUserWithUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new RecordNotFoundException("User with username not found");
        }
        return user;
    }

    private UserDto convertToDto(User user) {
        return this.mapper.map(user, UserDto.class);
    }

    private User convertToEntity(UserDto userDto) {
        return this.mapper.map(userDto, User.class);
    }
}
