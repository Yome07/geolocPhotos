package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserRepositoryTests {

    @Autowired
    private UserRepository userRepository;
    

    @Test
    @Rollback(false)
    @Order(1)
    public void testCreateUser() {
        User user = new User("Dupont","Jean","email@test.fr","1234", (float) 45.34, (float) -10.12,true);
        User savedUser = userRepository.save(user);

        assertNotNull(savedUser);
    }

    @Test
    @Rollback(false)
    @Order(2)
    public void testEmailExists() {
        String email = "email@test.fr";
        User user = userRepository.findByEmail(email);

        assertNotNull(user);
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void testEmailDoesntExist() {
        String email = "emaildoesntexist@test.fr";
        User user = userRepository.findByEmail(email);

        assertNull(user);
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void testUpdateUser() {
        String email = "email@test.fr";
        User user = userRepository.findByEmail(email);
        user.setName("Martin");

        userRepository.save(user);
        User updatedUser = userRepository.findByEmail(email);

        assertEquals(user.getName(), updatedUser.getName());
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testListUsers() {
        List<User> users = userRepository.findAll();

        assertTrue(users.size() > 0);
    }

    @Test
    @Rollback(false)
    @Order(6)
    public void testDeleteUser() {
        String email = "email@test.fr";

        User userExists = userRepository.findByEmail(email);

        userRepository.deleteById(userExists.getId());

        User userDoesntExist = userRepository.findByEmail(email);

        assertNotNull(userExists);
        assertNull(userDoesntExist);

    }
}