package com.example.demo.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;
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


    @GetMapping("Profile")
    public ModelAndView viewprofile(HttpSession session) {
        ModelAndView mav = new ModelAndView("profile.html");
        mav.addObject("username", (String) session.getAttribute("username"));
        return mav;
    }

    @GetMapping("EditProfile")
      public ModelAndView editProfile(HttpSession session) {
         ModelAndView mav = new ModelAndView("editProfile.html");
         String username = (String) session.getAttribute("username");
         User user = UserRepositry.findByUsername(username);
         mav.addObject("user", user);
         return mav;
      }
      @PostMapping("EditProfile")
      public RedirectView updateProfile(@ModelAttribute User updatedUser, HttpSession session, RedirectAttributes redirectAttributes) {
          // Retrieve the existing user from the session
          String username = (String) session.getAttribute("username");
          User existingUser = UserRepositry.findByUsername(username);
          
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
                  UserRepositry.save(existingUser);
                  
                  // Redirect to profile page after successful update
                  return new RedirectView("/Admin/Profile", true);
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
          return new RedirectView("/Admin/EditProfile", true);
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
