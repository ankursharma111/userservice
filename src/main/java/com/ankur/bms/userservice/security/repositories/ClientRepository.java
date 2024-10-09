package com.ankur.bms.userservice.security.repositories;


import java.util.Optional;


import com.ankur.bms.userservice.security.models.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {
    Optional<Client> findByClientId(String clientId);

}