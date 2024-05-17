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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

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
    @Test
    public void testLogin() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);
        
        // Call the method
        ModelAndView mav = userController.login();

        // Assert the view name
        assertEquals("login.html", mav.getViewName());
        // Assert that a user object is added to the ModelAndView
        assertNotNull(mav.getModel().get("user"));
    }
    @Test
    public void testLoginProcessEmptyCredentials() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Call the method with empty username and password
        RedirectView redirectView = userController.loginProcess("", "", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Username and password cannot be empty.");
    }
    @Test
   public void testLoginProcessInvalidUsername() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock behavior
        when(userRepository.findByUsername("testUser")).thenReturn(null);

        // Call the method with an invalid username
        RedirectView redirectView = userController.loginProcess("testUser", "testPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid username.");
    }
    @Test
    public void testLoginProcessInvalidPassword() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with an invalid password
        RedirectView redirectView = userController.loginProcess("testUser", "wrongPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Login", redirectView.getUrl());
        // Verify that an error message is added to redirect attributes
        verify(redirectAttributes).addFlashAttribute("errorMessage", "Invalid password.");
    }
    @Test
    public void testLoginProcessValidCredentialsAdmin() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        dbUser.setType("admin");
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with valid credentials
        RedirectView redirectView = userController.loginProcess("testUser", "correctPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("/Admin/Dashboard", redirectView.getUrl());
        // Verify that session attributes are set
        verify(session).setAttribute("username", "testUser");
        verify(session).setAttribute("type", "admin");
    }
    @Test
    public void testLoginProcessValidCredentialsUser() {
        // Create a mock UserRepository
        UserRepositry userRepository = mock(UserRepositry.class);
        // Create a UserController instance with the mock UserRepository
        UserController userController = new UserController(userRepository);

        // Create mocks for HttpSession and RedirectAttributes
        HttpSession session = mock(HttpSession.class);
        RedirectAttributes redirectAttributes = mock(RedirectAttributes.class);

        // Setup mock User object
        User dbUser = new User();
        dbUser.setUsername("testUser");
        dbUser.setPassword(org.mindrot.jbcrypt.BCrypt.hashpw("correctPassword", org.mindrot.jbcrypt.BCrypt.gensalt()));
        dbUser.setType("user");
        when(userRepository.findByUsername("testUser")).thenReturn(dbUser);

        // Call the method with valid credentials
        RedirectView redirectView = userController.loginProcess("testUser", "correctPassword", session, redirectAttributes);

        // Assert the redirection view
        assertEquals("Profile", redirectView.getUrl());
        // Verify that session attributes are set
        verify(session).setAttribute("username", "testUser");
        verify(session).setAttribute("type", "user");
    }

}



