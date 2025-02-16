package com.example.gyankosh.serviceImpl;

import com.example.gyankosh.Entity.User;
import com.example.gyankosh.Repository.UserRepository;
import com.example.gyankosh.dto.GetUserDto;
import com.example.gyankosh.dto.UserDto;
import com.example.gyankosh.security.JwtTokenHelper;
import com.example.gyankosh.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserSeviceImpl implements UserService {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtTokenHelper jwtTokenHelper;
//    @Value("{loksewa}")
//    private String baseurl;

    @Override
    public UserDto registerNewUser(UserDto userDto)
    {
        User user=this.modelMapper.map(userDto,User.class);
        System.out.println("FirstName: " + user.getFirstName());
        System.out.println("LastName: " + user.getLastName());
//        user.setFirstName(user.getFirstName());
        user.setPassword(this.passwordEncoder.encode(user.getPassword()));
        user.setCreatedTime(LocalDateTime.now());
        User newUser=this.userRepository.save(user);
        return this.modelMapper.map(newUser,UserDto.class);

    }

   public GetUserDto getUserDetailsByEmail(String email)
   {
    Optional<User> user= userRepository.findByEmail(email);
       return user.map(value -> modelMapper.map(value,GetUserDto.class)).orElse(null);

   }
}
