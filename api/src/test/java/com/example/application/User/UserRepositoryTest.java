package com.example.application.User;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindByEmail() {
        // Create a test user
        User user = new User();
        user.setEmail("test@example.com");
        userRepository.save(user);

        // Find the user by email
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // Assert that the user was found
        assertTrue(foundUser.isPresent());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
    }

    @Test
    public void testFindByNonexistentEmail() {
        // Try to find a user with an email that doesn't exist
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Assert that no user was found
        assertFalse(foundUser.isPresent());
    }


}