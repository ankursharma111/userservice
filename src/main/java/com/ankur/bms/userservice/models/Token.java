package com.ankur.bms.userservice.models;


import jakarta.persistence.*;
import lombok.*;

import java.sql.*;

@Getter
@Setter
@Entity
public class Token extends BaseModel {

    private String value;
    @ManyToOne
    private User user;
    private Date expiryAt;

}
