package com.example.gyankosh.controller;

import com.example.gyankosh.Entity.User;
import com.example.gyankosh.Repository.UserRepository;
import com.example.gyankosh.dto.GetUserDto;
import com.example.gyankosh.dto.UserDto;
import com.example.gyankosh.dto.UserLoginRequest;
import com.example.gyankosh.dto.UserLoginResponse;
import com.example.gyankosh.security.JwtTokenHelper;
import com.example.gyankosh.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    @Autowired
    private UserService userService;
@Autowired
private UserRepository userRepository;
@Autowired
private AuthenticationManager authenticationManager;
@Autowired
private JwtTokenHelper jwtTokenHelper;

    @PostMapping("/register")
    public UserDto registerNewUser(@RequestBody UserDto userDto)
    {
        return userService.registerNewUser(userDto);
    }

@PostMapping(path = "/login")
public ResponseEntity<?> loginUser(@RequestBody UserLoginRequest request) {
//    logInfo.info("User login Api");
    Optional<User> theUser = userRepository.findByEmail(request.getEmail());

UserLoginResponse response=new UserLoginResponse();
    if (theUser.isPresent()) {
        User user = theUser.get();
        try {
            Authentication authentication = authenticationManager.authenticate(

                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            if (authentication.isAuthenticated()) {
                response.setToken(jwtTokenHelper.generateToken(user.getEmail(), "user"));
                response.setMessage("Login Successful");
                response.setUserId(user.getId());
                response.setFirstName(user.getFirstName());
                response.setLastName(user.getLastName());
//                response.setMobileNumber(user.getMobileNumber());
//                response.setEmail(user.getEmail());
//                response.setTokenExpiryTime(jwtTokenHelper.getExpirationSecondsFromToken(jwtTokenHelper.generateToken(user.getEmail(),"user")));
//                logInfo.info("Login successful "+ user.getEmail());
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
//                logInfo.error("Invalid email or Password!");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
            }
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("status", "error", "message", "Invalid email or password. Please try again."));
        }
    }
    else
    {
//        logInfo.error("User not found");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("status", "error", "message", "User not found"));
    }
}

    @GetMapping("/getbyGmail/{email}")
    public ResponseEntity<?> getUserDetails(@PathVariable String email)
    {
       GetUserDto getUserDto =userService.getUserDetailsByEmail(email);
        if (getUserDto != null) {
            return ResponseEntity.ok(getUserDto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
