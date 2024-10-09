package com.ankur.bms.userservice.controllers;


import com.ankur.bms.userservice.dtos.*;
import com.ankur.bms.userservice.models.*;
import com.ankur.bms.userservice.repositories.*;
import com.ankur.bms.userservice.services.*;
import lombok.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final TokenRepository tokenRepository;
    UserService userService;

    public UserController(UserService userService, TokenRepository tokenRepository) {
        this.userService = userService;
        this.tokenRepository = tokenRepository;
    }

    @PostMapping("/signUp")
    public User signUp(@RequestBody SignUpRequestDto request){

        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();


        return userService.signUp(email, password, name);

    }

    @PostMapping("/login")
    public Token login(@RequestBody LoginRequestDto request){

        return userService.login(request.getEmail(), request.getPassword());

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto request){

        String tokenValue = request.getToken();

       userService.logout(tokenValue);

       return new ResponseEntity<>(HttpStatus.OK);

    }

    @PostMapping("/validate/{token}")
    public UserDto validateToken(@PathVariable("token") @NonNull String token){


        return UserDto.from(userService.validateToken(token));


    }



}
