package com.example.demo.controllers;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.models.Appointment;
import com.example.demo.models.Doctor;
import com.example.demo.models.Nurse;
import com.example.demo.repositories.DoctorRepository;
import com.example.demo.repositories.NurseRepository;
import com.example.demo.repositories.UserRepositry;
import com.example.demo.repositories.AppointmentRepository;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class RouteController {
    @Autowired
    private UserRepositry userRepository;
    
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
    @GetMapping("/User/appointmentreview")
    public ModelAndView appointmentreviewpageusr() {
        ModelAndView mav = new ModelAndView("appointmentreview.html");
        return mav;
    }
    
    @GetMapping("appointmentreview")
    public ModelAndView appointmentreviewpage() {
        ModelAndView mav = new ModelAndView("appointmentreview.html");
        return mav;
    }

    @GetMapping("specialities")
    public ModelAndView specialitiespageIndex() {
        ModelAndView mav = new ModelAndView("specialities.html");
        return mav;
    }

    @GetMapping("booking")
    public ModelAndView bookingpage() {
        ModelAndView mav = new ModelAndView("booking.html");
        List<Doctor> doctors = doctorRepository.findAll();
        List<Nurse> nurses = nurseRepository.findAll();
        mav.addObject("doctors", doctors);
        mav.addObject("nurses", nurses);
        return mav;
    }
}

@Controller
class BookingController {

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final NurseRepository nurseRepository;

    @Autowired
    public BookingController(AppointmentRepository appointmentRepository, DoctorRepository doctorRepository, NurseRepository nurseRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
        this.nurseRepository = nurseRepository;
    }

    @PostMapping("/booking")
    public String bookAppointment(
            @RequestParam Long doctorId,
            @RequestParam Long nurseId,
            @RequestParam String patientName,
            @RequestParam Date appointmentDate,
            @RequestParam String appointmentTime,
            @RequestParam String reason) {

        Doctor doctor = doctorRepository.findById(doctorId).orElse(null);
        Nurse nurse = nurseRepository.findById(nurseId).orElse(null);

        if (doctor != null && nurse != null) {
            Appointment appointment = new Appointment();
            appointment.setDoctor(doctor);
            appointment.setNurse(nurse);
            appointment.setPatientName(patientName);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setAppointmentTime(appointmentTime);
            appointment.setReason(reason);

            appointmentRepository.save(appointment);
            return "redirect:/appointmentreview"; // Redirect to a success page
        } else {
            return "redirect:/error-page"; // Redirect to an error page
        }
    }
}
