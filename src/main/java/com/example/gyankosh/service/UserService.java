package com.example.gyankosh.service;

import com.example.gyankosh.dto.GetUserDto;
import com.example.gyankosh.dto.UserDto;
import org.springframework.stereotype.Service;

@Service
public interface UserService  {
    UserDto registerNewUser(UserDto userDto);
    GetUserDto getUserDetailsByEmail(String email);

}
