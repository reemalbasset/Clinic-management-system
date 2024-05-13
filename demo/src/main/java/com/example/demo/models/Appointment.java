package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import java.util.Date;
import java.util.Objects;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Doctor doctor;

    @ManyToOne
    private Nurse nurse;

    @ManyToOne
    private Patient patient;

    private Date appointmentDate;

    private String reason;

    public Appointment() {
    }

    public Appointment(Long id, Doctor doctor, Nurse nurse, Patient patient, Date appointmentDate, String reason) {
        this.id = id;
        this.doctor = doctor;
        this.nurse = nurse;
        this.patient = patient;
        this.appointmentDate = appointmentDate;
        this.reason = reason;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Doctor getDoctor() {
        return this.doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Nurse getNurse() {
        return this.nurse;
    }

    public void setNurse(Nurse nurse) {
        this.nurse = nurse;
    }

    public Patient getPatient() {
        return this.patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getAppointmentDate() {
        return this.appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public String getReason() {
        return this.reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Appointment)) {
            return false;
        }
        Appointment appointment = (Appointment) o;
        return Objects.equals(id, appointment.id) && Objects.equals(doctor, appointment.doctor) && Objects.equals(nurse, appointment.nurse) && Objects.equals(patient, appointment.patient) && Objects.equals(appointmentDate, appointment.appointmentDate) && Objects.equals(reason, appointment.reason);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, doctor, nurse, patient, appointmentDate, reason);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", doctor='" + getDoctor() + "'" +
            ", nurse='" + getNurse() + "'" +
            ", patient='" + getPatient() + "'" +
            ", appointmentDate='" + getAppointmentDate() + "'" +
            ", reason='" + getReason() + "'" +
            "}";
    }
}
