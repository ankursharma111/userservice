package com.ankur.bms.userservice.security.models;

import com.ankur.bms.userservice.models.*;
import com.ankur.bms.userservice.models.User;
import com.fasterxml.jackson.databind.annotation.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;

import java.util.*;

@JsonDeserialize
public class CustomUserDetails implements UserDetails {


    //private User user;
    private List<CustomGrantedAuthority> authorities;
    private String password;
    private  String username;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private Long userId;


    public CustomUserDetails(User user) {
        this.accountNonLocked = true;
        this.accountNonExpired = true;
        this.credentialsNonExpired = true;
        this.enabled = true;
        this.username = user.getEmail();
        this.password = user.getHashedPassword();
        this.userId = user.getId();


        List<CustomGrantedAuthority> grantedAuthorities = new ArrayList<>();


        for(Role role : user.getRoles()){
            grantedAuthorities.add(new CustomGrantedAuthority(role));
        }

        this.authorities = grantedAuthorities;

    }

    public CustomUserDetails(){

    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

//        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
//
//
//        for(Role role : user.getRoles()){
//            grantedAuthorities.add(new CustomGrantedAuthority(role));
//        }
//
//
//        return grantedAuthorities;

        return authorities;
    }

    @Override
    public String getPassword() {
//        return user.getHashedPassword();

        return password;

    }

    @Override
    public String getUsername() {
        //return user.getEmail();

        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return true;

        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {

        //return true;

        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
       // return true;

        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {

        //return true;

        return enabled;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
