package com.ankur.bms.userservice.dtos;

import com.ankur.bms.userservice.models.*;
import lombok.*;

import java.util.*;


@Getter
@Setter
public class UserDto {


    private String name;
    private String email;

    private List<Role> roles;
    private Boolean isEmailVerified;


    public static UserDto from(User user) {

        if(user==null){
            return null;
        }

        UserDto userDto = new UserDto();
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setRoles(user.getRoles());
        userDto.setIsEmailVerified(user.isEmailVerified());
        return userDto;
    }
}
