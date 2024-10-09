package com.ankur.bms.userservice.security.models;

import com.ankur.bms.userservice.models.*;
import com.fasterxml.jackson.databind.annotation.*;
import org.springframework.security.core.*;


@JsonDeserialize
public class CustomGrantedAuthority implements GrantedAuthority {

   // private Role role;

    private String authority;

    public CustomGrantedAuthority(Role role) {

        //this.role = role;
        this.authority = role.getName();

    }

    @Override
    public String getAuthority() {

        //return role.getName();

        return authority;
    }

}
