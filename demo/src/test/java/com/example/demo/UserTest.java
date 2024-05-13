package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.controllers.UserController;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositry;


public class UserTest {
    @Test
    public void testAddUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Call the method
        ModelAndView mav = userController.addUser();

        // Assert the view name
        assertEquals("registration.html", mav.getViewName());
        // Assert that a user object is added to the ModelAndView
        assertNotNull(mav.getModel().get("user"));
    }

    @Test
    public void testSaveUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Create a User object
        User user = new User();
        user.setUsername("testUser");
        user.setPassword("testPassword");

        // Call the method
        ModelAndView mav = userController.saveUser(user);

        // Assert the view name
        assertEquals("login.html", mav.getViewName());
        // Verify that userRepository.save() method is called once with the user object
        verify(userRepository, times(1)).save(user);
    }
}



