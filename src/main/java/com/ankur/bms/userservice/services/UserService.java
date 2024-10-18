package com.ankur.bms.userservice.services;


import com.ankur.bms.userservice.dtos.*;
import com.ankur.bms.userservice.models.*;
import com.ankur.bms.userservice.repositories.*;
import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import org.apache.commons.lang3.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.kafka.core.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.stereotype.*;
import java.time.LocalDate;


import java.util.*;

@Service
public class UserService {

    private TokenRepository tokenRepository;

    private UserRepository userRepository;

    private BCryptPasswordEncoder bcryptPasswordEncoder;

    private KafkaTemplate<String, String> kafkaTemplate;

    private ObjectMapper objectMapper;

    @Autowired
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bcrypPasswordEncoder,
                       TokenRepository tokenRepository,
                       KafkaTemplate<String, String> kafkaTemplate,
                       ObjectMapper objectMapper) {
        this.userRepository = userRepository;
        this.bcryptPasswordEncoder = bcrypPasswordEncoder;
        this.tokenRepository = tokenRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public User signUp(String email, String password, String fullName) {

        User u = new User();

        u.setName(fullName);
        u.setEmail(email);
        u.setHashedPassword(bcryptPasswordEncoder.encode(password));

        User user = userRepository.save(u);

        SendEmailEventDto sendEmailEventDto = new SendEmailEventDto();

        sendEmailEventDto.setTo(email);
        sendEmailEventDto.setFrom("info@scaler.com");
        sendEmailEventDto.setSubject("Welcome to Scaler");
        sendEmailEventDto.setBody("Thanks for signing up with scaler. We wish you a great success ahead");

        try {
            kafkaTemplate.send(
                    "sendEmail",
                    objectMapper.writeValueAsString(sendEmailEventDto)

            );
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

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

