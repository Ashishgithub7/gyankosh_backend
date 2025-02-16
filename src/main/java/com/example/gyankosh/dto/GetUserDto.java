package com.example.gyankosh.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GetUserDto {

    private String firstName;
    private String lastName;
    private String mobileNumber;
    private String email;
}
