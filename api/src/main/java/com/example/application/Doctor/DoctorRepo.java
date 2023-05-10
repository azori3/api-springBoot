package com.example.application.Doctor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DoctorRepo extends JpaRepository<Doctor, Long> {

    Doctor findByFirstName(String firstName);

}
