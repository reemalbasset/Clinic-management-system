package com.example.demo.controllers;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositry;

import ch.qos.logback.core.model.Model;
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
            if ("admin".equals(dbUser.getType())) {
                return new RedirectView("Dashboard");
            } else {
                return new RedirectView("Profile");
            }
        }
    }
    return new RedirectView("Login");
}


   @GetMapping("Profile")
   public ModelAndView viewprofile(HttpSession session) {
     ModelAndView mav = new ModelAndView("profile.html");
     mav.addObject("username",(String)session.getAttribute("username"));
     return mav;
   }
   
    @GetMapping("Dashboard")
    public ModelAndView viewdashboard(HttpSession session) {
       ModelAndView mav = new ModelAndView("dashboard.html");
       mav.addObject("username", (String) session.getAttribute("username"));
      return mav;
   }
   
}
