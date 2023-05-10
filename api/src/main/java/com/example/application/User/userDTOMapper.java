package com.example.application.User;


import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class userDTOMapper implements Function<User,UserDTO> {


    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getFirstname(),
                user.getLastname(),
                user.getEmail()
        );
    }
}
