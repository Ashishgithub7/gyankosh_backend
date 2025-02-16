package com.example.gyankosh.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDto {

    @Column(name ="userId")
    private Long id;
    @Column(name ="firstName")
    private String firstName;
    @Column(name ="lastName")
    private String lastName;
    @Column(name ="mobileNumber")
    private String mobileNumber;
    @Column(name ="email")
    private String email;
    @Column(name ="password")
    private String password;
    @Column(name = "confirmPassword")
    @JsonIgnore
    private String confirmPassword;
    @Column(name ="crateTime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime createdTime;

}
