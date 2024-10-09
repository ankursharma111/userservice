package com.ankur.bms.userservice.repositories;

import com.ankur.bms.userservice.models.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface TokenRepository extends JpaRepository<Token,Long> {

    Token save(Token token);

   Optional<Token> findByValueAndDeleted(String value, boolean isDeleted);


   Optional<Token> findByValueAndDeletedAndExpiryAtGreaterThan(String value, boolean isDeleted, Date expiryGreaterThan );





}
