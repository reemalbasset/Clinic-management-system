// Doctor.java
package com.example.demo.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.util.Objects;
@Entity
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String specialty;
    private String universityName;

    public Doctor() {
    }

    public Doctor(Long id, String name, String email, String password, String specialty, String universityName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.specialty = specialty;
        this.universityName = universityName;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSpecialty() {
        return this.specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public String getUniversityName() {
        return this.universityName;
    }

    public void setUniversityName(String universityName) {
        this.universityName = universityName;
    }

    public Doctor id(Long id) {
        setId(id);
        return this;
    }

    public Doctor name(String name) {
        setName(name);
        return this;
    }

    public Doctor email(String email) {
        setEmail(email);
        return this;
    }

    public Doctor password(String password) {
        setPassword(password);
        return this;
    }

    public Doctor specialty(String specialty) {
        setSpecialty(specialty);
        return this;
    }

    public Doctor universityName(String universityName) {
        setUniversityName(universityName);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof Doctor)) {
            return false;
        }
        Doctor doctor = (Doctor) o;
        return Objects.equals(id, doctor.id) && Objects.equals(name, doctor.name) && Objects.equals(email, doctor.email) && Objects.equals(password, doctor.password) && Objects.equals(specialty, doctor.specialty) && Objects.equals(universityName, doctor.universityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, specialty, universityName);
    }

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", email='" + getEmail() + "'" +
            ", password='" + getPassword() + "'" +
            ", specialty='" + getSpecialty() + "'" +
            ", universityName='" + getUniversityName() + "'" +
            "}";
    }

    
}
