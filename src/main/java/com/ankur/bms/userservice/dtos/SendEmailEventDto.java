package com.ankur.bms.userservice.dtos;


import lombok.*;

@Getter
@Setter
public class SendEmailEventDto {

    private String to;
    private String from;
    private String subject;
    private String body;

}
