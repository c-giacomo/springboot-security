package it.uuid.tokenbased.auth.service;

import org.springframework.security.core.userdetails.User;

import it.uuid.tokenbased.auth.jpa.Customer;

import java.util.Optional;

public interface CustomerService {

    String login(String username, String password);
    Optional<User> findByToken(String token);
    Customer findById(Long id);
}
