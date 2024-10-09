package com.ankur.bms.userservice.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;


@Getter
@Setter
@Entity
public class User extends BaseModel {

    private String name;
    private String email;
    private String hashedPassword;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Role> roles;
    private boolean isEmailVerified;

}
