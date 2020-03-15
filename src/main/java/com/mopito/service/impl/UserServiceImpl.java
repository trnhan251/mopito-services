package com.mopito.service.impl;

import com.mopito.dto.UserDto;
import com.mopito.entity.UserEntity;
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

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userEntity.setCreatedOn(new Date());

        UserEntity storedUserEntity = userRepository.save(userEntity);
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserEntity, returnValue);
        return returnValue;
    }

    @Override
    public UserDto findWithUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username);
        UserDto userDto = new UserDto();

        BeanUtils.copyProperties(userEntity, userDto);

        return userDto;
    }

    @Override
    public List<UserDto> findAllUsers() {
        List<UserEntity> userEntityList = userRepository.findAll();
        return userEntityList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private UserDto convertToDto(UserEntity userEntity) {
        return modelMapper.map(userEntity, UserDto.class);
    }
}
