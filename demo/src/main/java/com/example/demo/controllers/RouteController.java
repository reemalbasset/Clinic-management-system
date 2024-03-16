package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.repositories.UserRepositry;
@RestController
@RequestMapping("/")
public class RouteController {
      @Autowired
   private UserRepositry userRepositry;
   @GetMapping("specialities")
   public ModelAndView specialitiespage() {
       ModelAndView mav = new ModelAndView("specialities.html");
       return mav;
   }
   @GetMapping("clinicteam")
   public ModelAndView clinicteampage() {
       ModelAndView mav = new ModelAndView("clinicteam.html");
       return mav;
   }
   @GetMapping("aboutus")
   public ModelAndView aboutuspage() {
       ModelAndView mav = new ModelAndView("aboutus.html");
       return mav;
   }
   @GetMapping("doctorslist")
   public ModelAndView doctorslistpage() {
       ModelAndView mav = new ModelAndView("doctorslist.html");
       return mav;
   }
   @GetMapping("doctorsdetails")
   public ModelAndView doctorsdetailspage() {
       ModelAndView mav = new ModelAndView("doctorsdetails.html");
       return mav;
   }
   @GetMapping("booking")
   public ModelAndView bookingpage() {
       ModelAndView mav = new ModelAndView("booking.html");
       return mav;
   }
   @GetMapping("appointmentreview")
   public ModelAndView appointmentreviewpage() {
       ModelAndView mav = new ModelAndView("appointmentreview.html");
       return mav;
   }
}

