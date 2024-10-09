package com.ankur.bms.userservice.models;


import jakarta.persistence.*;
import lombok.*;

@MappedSuperclass
@Getter
@Setter
public class BaseModel {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private boolean deleted;



}
