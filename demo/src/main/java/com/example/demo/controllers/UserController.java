package com.example.demo.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositry;


import jakarta.servlet.http.HttpSession;

import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;




@RestController
@RequestMapping("/User")
public class UserController {
   @Autowired
   private UserRepositry userRepositry;
   
   @GetMapping("/")
   public ModelAndView index() {
       ModelAndView mav = new ModelAndView("index.html");
       return mav;
   }
   
   @GetMapping("Registration")
   public ModelAndView addUser() {
    ModelAndView mav=new ModelAndView("registration.html");
    User newUser=new User();
    mav.addObject("user", newUser);
       return mav;
   }
   @PostMapping("Registration")
   public String saveUser(@ModelAttribute User user) {
      String encoddedPassword=org.mindrot.jbcrypt.BCrypt.hashpw(user.getPassword(),org.mindrot.jbcrypt.BCrypt.gensalt(12));
      user.setPassword(encoddedPassword);
      this.userRepositry.save(user);
       
       return "added";
   }
   @GetMapping("Login")
   public ModelAndView login() {
      ModelAndView mav=new ModelAndView("login.html");
      User newUser=new User();
      mav.addObject("user", newUser);
      return mav;
   }
   
   @PostMapping("Login")
   public RedirectView loginProcess(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   HttpSession session) {
       User dbUser = this.userRepositry.findByUsername(username);
       if (dbUser != null) {
           Boolean isPasswordMatched = org.mindrot.jbcrypt.BCrypt.checkpw(password, dbUser.getPassword());
           if (isPasswordMatched) {
               session.setAttribute("username", dbUser.getUsername());
               // if ("admin".equals(dbUser.getType())) {
                  //  return new RedirectView("Dashboard");
               // } else {
                   return new RedirectView("Profile");
               // }
           }
       }
       return new RedirectView("Login");
   }
   
   
      @GetMapping("Profile")
      public ModelAndView viewprofile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");
        ModelAndView mav1 = new ModelAndView("profile.html");
        mav.addObject("username",(String)session.getAttribute("username"));
        mav.addObject("name",(String)session.getAttribute("name"));
        mav.addObject("dob",(String)session.getAttribute("dob"));
        return mav;
      }
      
       @GetMapping("Dashboard")
       public ModelAndView viewdashboard(HttpSession session) {
          ModelAndView mav = new ModelAndView("dashboard.html");
          mav.addObject("username", (String) session.getAttribute("username"));
         return mav;
   }
   
   @GetMapping("EditProfile")
      public ModelAndView editProfile(HttpSession session) {
         ModelAndView mav = new ModelAndView("editProfile.html");
         String username = (String) session.getAttribute("username");
         User user = userRepositry.findByUsername(username);
         mav.addObject("user", user);
         return mav;
      }
      @PostMapping("EditProfile")
      public RedirectView updateProfile(@ModelAttribute User updatedUser, HttpSession session, RedirectAttributes redirectAttributes) {
          // Retrieve the existing user from the session
          String username = (String) session.getAttribute("username");
          User existingUser = userRepositry.findByUsername(username);
          
          // Update the existing user with the values from the updated user
          if (existingUser != null) {
              try {
                  existingUser.setName(updatedUser.getName());
                  existingUser.setDob(updatedUser.getDob());
                  existingUser.setUsername(updatedUser.getUsername());
                  
                  // Check if the password field is provided in the form
                  if (!updatedUser.getPassword().isEmpty()) {
                      // Hash and update the password only if provided
                      existingUser.setPassword(BCrypt.hashpw(updatedUser.getPassword(), BCrypt.gensalt(12)));
                  }
                  
                  // Save the updated user
                  userRepositry.save(existingUser);
                  
                  // Redirect to profile page after successful update
                  return new RedirectView("/User/Profile", true);
              } catch (Exception e) {
                  // Log the exception
                  e.printStackTrace();
                  // Add error message to redirect attributes
                  redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while updating your profile.");
              }
          } else {
              // User not found in the session
              redirectAttributes.addFlashAttribute("errorMessage", "User not found. Please try again.");
          }
          
          // Redirect back to the edit profile page in case of errors
          return new RedirectView("/User/EditProfile", true);
      }
      
      @GetMapping("/logout")
    public RedirectView logout(HttpSession session) {
        // Invalidate session
        session.invalidate();
        // Redirect to login page after logout
        return new RedirectView("/User/Login", true);
    }


   }
