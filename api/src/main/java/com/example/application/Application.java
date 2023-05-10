package com.example.application;

import com.example.application.Doctor.CabinetName;
import com.example.application.Doctor.Doctor;
import com.example.application.Doctor.DoctorRepo;
import com.example.application.Doctor.Status;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	//@Bean
	CommandLineRunner run(DoctorRepo doctorRepo) {
		return args -> {
			doctorRepo.save(new Doctor(null, "Baraka","Léna","Médecin dentiste","Baraka.lena@hygie-groupe.lu", CabinetName.ESCH,671888345L,"http://localhost:8080/api/v1/doctor/image/profile1.png"));
			doctorRepo.save(new Doctor(null, "Hoffmann","Tibériu","CHIRURGIEN DENTISTE/IMPLANTOLOGUE","Hoffmann.tiberio@hygie-groupe.lu", CabinetName.ESCH,671888345L,"http://localhost:8080/api/v1/doctor/image/profile1.png"));
			doctorRepo.save(new Doctor(null, "Salam","Azeezah","CHIRURGIEN DENTISTE","Salam.Azeezah@hygie-groupe.lu", CabinetName.ESCH,671888345L,"http://localhost:8080/api/v1/doctor/image/profile1.png"));
		};
	}

}
