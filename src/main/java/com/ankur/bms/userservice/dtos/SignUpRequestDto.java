package com.ankur.bms.userservice.dtos;


import lombok.*;

@Getter
@Setter
public class SignUpRequestDto {


    private String email;
    private String password;
    private String name;
}
