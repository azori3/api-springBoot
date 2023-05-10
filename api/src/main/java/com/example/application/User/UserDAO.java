package com.example.application.User;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


public interface UserDAO  {

    List<User> findAllUser();
    Optional<User> findByEmail(String email);
    Optional<User> findUserById(Long id);


}
