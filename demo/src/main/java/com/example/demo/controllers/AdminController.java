package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepositry;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
     private UserRepositry AdminRepositry;
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
       User dbUser = this.AdminRepositry.findByUsername(username);
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


