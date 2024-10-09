package com.ankur.bms.userservice.repositories;

import com.ankur.bms.userservice.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {


    User save(User user);

    Optional<User> findByEmail(String email);


}
