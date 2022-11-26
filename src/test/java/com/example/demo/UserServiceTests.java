package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import com.example.demo.model.User;
import com.example.demo.service.UserServices;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTests {

    @Autowired
    private UserServices userServices;

    @Test
    @Rollback(false)
    @Order(1)
    public void testListUsers() {
        List<User> users = userServices.findAll();

        assertTrue(users.size() > 0);
    }
    
    @Test
    @Rollback(false)
    @Order(2)
    public void testCreateUser() {
        User user = new User("Dupont","Jean",
                "email@test.fr","1234", (float) 45.34,
                (float) -10.12,true);
        List<User> users = userServices.findAll();
        userServices.createUser(user);
        List<User> usersPlusOne = userServices.findAll();

        assertEquals(users.size()+1, usersPlusOne.size());
    }

    @Test
    @Rollback(false)
    @Order(3)
    public void testEmailExists() {
        String email = "email@test.fr";
        User user = userServices.findByEmail(email);

        assertNotNull(user);
    }

    @Test
    @Rollback(false)
    @Order(4)
    public void testEmailDoesntExist() {
        String email = "emaildoesntexist@test.fr";
        User user = userServices.findByEmail(email);

        assertNull(user);
    }

    @Test
    @Rollback(false)
    @Order(5)
    public void testUpdateUser() {
        String email = "email@test.fr";
        User user = userServices.findByEmail(email);
        user.setName("Martin");

        userServices.createUser(user);
        User updatedUser = userServices.findByEmail(email);

        assertEquals(user.getName(), updatedUser.getName());
    }

    @Test
    @Rollback(false)
    @Order(6)
    public void testGetUserId() {
    	Optional<User> user = userServices.getById((long) 1);
    	
    	assertNotNull(user);
    }

    @Test
    @Rollback(false)
    @Order(7)
    public void testDeleteUser() {
        String email = "email@test.fr";

        User userExists = userServices.findByEmail(email);

        userServices.deleteUser(userExists);

        User userDoesntExist = userServices.findByEmail(email);

        assertNotNull(userExists);
        assertNull(userDoesntExist);

    }
    
    
}
