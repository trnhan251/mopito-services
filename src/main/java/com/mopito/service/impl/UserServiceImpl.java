package com.mopito.service.impl;

import com.mopito.dto.UserDto;
import com.mopito.entity.User;
import com.mopito.repository.UserRepository;
import com.mopito.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new RuntimeException("Record already exists");
        }

        User user = new User();
        BeanUtils.copyProperties(userDto, user);

        user.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        user.setCreatedOn(new Date());

        User storedUser = userRepository.save(user);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUser, returnValue);
        return returnValue;
    }

    @Override
    public UserDto findWithUsername(String username) {
        User user = userRepository.findByUsername(username);
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(user, userDto);

        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
