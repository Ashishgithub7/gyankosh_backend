package com.example.gyankosh.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserLoginRequest {
    private String Email;
    private String password;
}
