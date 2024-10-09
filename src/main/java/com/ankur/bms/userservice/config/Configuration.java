package com.ankur.bms.userservice.config;

import org.springframework.context.annotation.*;
import org.springframework.security.crypto.bcrypt.*;


@org.springframework.context.annotation.Configuration
public class Configuration {


    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
