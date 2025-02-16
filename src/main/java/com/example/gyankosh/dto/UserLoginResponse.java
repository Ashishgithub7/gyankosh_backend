package com.example.gyankosh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


//yesma response
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginResponse {
    private String token;
    private String message;
    private Long  userId;
    private String firstName;
    private String lastName;

}
