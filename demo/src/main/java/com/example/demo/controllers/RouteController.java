package com.example.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.repositories.UserRepositry;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;

@RestController
@RequestMapping("/")
public class RouteController {
      @Autowired
   private UserRepositry userRepositry;
   @GetMapping("/User/specialities")
   public ModelAndView specialitiespage() {
       ModelAndView mav = new ModelAndView("specialities.html");
       return mav;
   }
   @GetMapping("/User/clinicteam")
   public ModelAndView clinicteampage() {
       ModelAndView mav = new ModelAndView("clinicteam.html");
       return mav;
   }
   @GetMapping("/User/aboutus")
   public ModelAndView aboutuspage() {
       ModelAndView mav = new ModelAndView("aboutus.html");
       return mav;
   }
   @GetMapping("/User/doctorslist")
   public ModelAndView doctorslistpage() {
       ModelAndView mav = new ModelAndView("doctorslist.html");
       return mav;
   }
   @GetMapping("/User/doctorsdetails")
   public ModelAndView doctorsdetailspage() {
       ModelAndView mav = new ModelAndView("doctorsdetails.html");
       return mav;
   }
   @GetMapping("specialities")
   public ModelAndView specialitiespageIndex() {
    ModelAndView mav = new ModelAndView("specialities.html");
    return mav;
}
@Autowired
private DoctorRepository doctorRepository;

@Autowired
private NurseRepository nurseRepository; // Corrected the name

@GetMapping("booking")
public ModelAndView bookingpage() {
    ModelAndView mav = new ModelAndView("booking.html");
    List<Doctor> doctors = doctorRepository.findAll();
    List<Nurse> nurses = nurseRepository.findAll(); // Corrected the name
    mav.addObject("doctors", doctors); // Corrected the key name
    mav.addObject("nurses", nurses); // Corrected the key name
    return mav;
}


   @GetMapping("appointmentreview")
   public ModelAndView appointmentreviewpage() {
       ModelAndView mav = new ModelAndView("appointmentreview.html");
       return mav;
   }

   @GetMapping("aboutus")
   public ModelAndView aboutUsPage() {
    ModelAndView mav = new ModelAndView("aboutus.html");
    return mav;
   }
   
   @GetMapping("clinicteam")
   public ModelAndView clinicteampageIndex() {
    ModelAndView mav = new ModelAndView("clinicteam.html");
    return mav;
   }
 

}

