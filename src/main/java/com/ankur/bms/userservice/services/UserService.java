package com.ankur.bms.userservice.services;


import com.ankur.bms.userservice.models.*;
import com.ankur.bms.userservice.repositories.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;
import java.time.LocalDate;


import java.util.*;

@Service
public class UserService {

    private TokenRepository tokenRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bcrypPasswordEncoder,
                       TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcrypPasswordEncoder;
        this.tokenRepository = tokenRepository;
    }

    public User signUp(String email, String password, String fullName){

        User u = new User();

        u.setName(fullName);
        u.setEmail(email);
        u.setHashedPassword(bcryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);

        return user;

    }

    public Token login(String email, String password){

        Optional<User> userOptional = userRepository.findByEmail(email);

        if(userOptional.isEmpty()){

            return null;

        }

        User user = userOptional.get();

        if(!bcryptPasswordEncoder.matches(password, user.getHashedPassword())) {

            return null;
        }

        Token token = new Token();

        LocalDate today = LocalDate.now();
        LocalDate futureDate = today.plusDays(30);

        token.setUser(user);
        token.setExpiryAt(java.sql.Date.valueOf(futureDate));

        RandomStringUtils.randomAlphanumeric(128);

        token.setValue(RandomStringUtils.randomAlphanumeric(128));

       Token savedToken = tokenRepository.save(token);

       return savedToken;

}

    public void logout(String token){

        Optional<Token> token1 = tokenRepository.findByValueAndDeleted(token, false);

        if(token.isEmpty()){
            return;
        }

        Token tkn = token1.get();
        tkn.setDeleted(true);

        tokenRepository.save(tkn);

        return;
    }

    public User validateToken(String token){

        Optional<Token> tkn = tokenRepository.
                findByValueAndDeletedAndExpiryAtGreaterThan(token, false, new Date());

        if(tkn.isEmpty()){
            return null;
        }

        return tkn.get().getUser();

    }
        }

