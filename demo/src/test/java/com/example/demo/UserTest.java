package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.http.HttpSession;

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

    @Test
    public void testViewProfile() {
        // Create a mock HttpSession
        HttpSession session = mock(HttpSession.class);

        // Set session attributes
        when(session.getAttribute("username")).thenReturn("testUser");
        when(session.getAttribute("name")).thenReturn("Test Name");
        when(session.getAttribute("dob")).thenReturn("2000-01-01");

        // Create a UserController instance
        UserController userController = new UserController(null); // Pass null as UserRepository is not needed for this test

        // Call the method
        ModelAndView mav = userController.viewprofile(session);

        // Assert the view name
        assertEquals("profile.html", mav.getViewName());

        // Assert model attributes
        assertEquals("testUser", mav.getModel().get("username"));
        assertEquals("Test Name", mav.getModel().get("name"));
        assertEquals("2000-01-01", mav.getModel().get("dob"));
    }
}



