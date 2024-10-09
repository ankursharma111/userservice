package com.ankur.bms.userservice.models;


import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
public class Role extends BaseModel {


    private String name;
}
