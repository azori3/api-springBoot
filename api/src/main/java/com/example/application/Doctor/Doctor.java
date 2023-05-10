package com.example.application.Doctor;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {


    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotEmpty(message = "Name cannot be empty or null")
    private String firstName;
    private String lastName;
    private String speciality;
    private String email;
    private CabinetName cabinet_name;
    private Long telephone;

    private String imageUrl;

}
