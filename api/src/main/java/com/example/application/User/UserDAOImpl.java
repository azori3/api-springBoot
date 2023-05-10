package com.example.application.User;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserDAOImpl implements UserDAO {

    private final UserRepository userService;



    @Override
    public List<User> findAllUser() {
        return userService.findAll();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userService.findById(id);
    }


}
