package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.models.Doctor;
import com.example.demo.models.User;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.UserRepositry;




import org.springframework.ui.Model;
import java.nio.file.Path;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/Admin")
public class AdminController {
    @Autowired
    private UserRepositry AdminRepositry;
    private UserRepositry UserRepositry;

    @GetMapping("Index")
   public ModelAndView index() {
       ModelAndView mav = new ModelAndView("index.html");
       return mav;
   }

    @GetMapping("Login")
    public ModelAndView login() {
        ModelAndView mav = new ModelAndView("login.html");
        User newUser = new User();
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
        mav.addObject("username", (String) session.getAttribute("username"));
        return mav;
    }

    @GetMapping("Dashboard")
    public ModelAndView viewdashboard(HttpSession session) {
        ModelAndView mav = new ModelAndView("dashboard.html");
        mav.addObject("username", (String) session.getAttribute("username"));
        return mav;
    }

    @GetMapping("Patient")
    public ModelAndView getUser(){
        ModelAndView mav = new ModelAndView("patients.html");
        List<User> users=this.UserRepositry.findAll();
        mav.addObject( "user", users);
        return mav;
    }








    @Autowired
    private DoctorRepository doctorRepository;

    // Display form to add doctor
  @GetMapping("addDoctor")
public ModelAndView showAddDoctorForm() {
    ModelAndView mav = new ModelAndView("add_doctor");
    mav.addObject("doctor", new Doctor());
    return mav;
}

@PostMapping("addDoctor")
public ModelAndView saveDoctor(@ModelAttribute Doctor doctor,
                               RedirectAttributes redirectAttributes) {
    ModelAndView mav = new ModelAndView();
    try {
        // Save doctor details
        doctorRepository.save(doctor);
        redirectAttributes.addFlashAttribute("successMessage", "Doctor added successfully.");
    } catch (DataIntegrityViolationException e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details due to a constraint violation.");
    } catch (Exception e) {
        e.printStackTrace();
        redirectAttributes.addFlashAttribute("errorMessage", "Failed to save doctor details.");
    }
    // Redirect to the same page
    mav.setViewName("redirect:/Admin/addDoctor");
    return mav;
}
 
@GetMapping("view_doctors")
public ModelAndView getCourses() {
ModelAndView mav = new ModelAndView("view_doctors.html");
List<Doctor> doctor = this.doctorRepository.findAll();
mav.addObject("doctors", doctor);
return mav;
}
@PostMapping("/deleteDoctor")
public String deletdr(@RequestParam("doctorId") Long doctorId) {
    // Implement logic to delete the food item with the given id
    // Example:
    doctorRepository.deleteById((long) doctorId);
    // Redirect to the page displaying the list of foods after deletion
    return "redirect:/view_doctors";
}
}
