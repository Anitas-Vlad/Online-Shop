package com.example.demo.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserDto {
    private String name;
    private String email;
    private String password;
    private String phoneNumber;
    private String address;
    private String role;
}
