package com.example.application.Doctor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Random;


@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DoctorServiceImpl implements DoctorService{


    private final DoctorRepo doctorRepo;

    @Override
    public Doctor create(Doctor doctor) {
       log.info("Saving new doctor {}", doctor.getFirstName());
       doctor.setImageUrl(setServerImageUrl());
       return doctorRepo.save(doctor);
    }




    @Override
    public Collection<Doctor> List(int limit) {

        log.info("Fetching all servers");
        return doctorRepo.findAll(PageRequest.of(0, limit)).toList();

    }

    @Override
    public Doctor get(Long id) {
        log.info("Fetching  doctor by id:  {}", id);
        return doctorRepo.findById(id).get();
    }

    @Override
    public Doctor update(Doctor doctor) {
        log.info("update  doctor by id:  {}", doctor.getId());
        return doctorRepo.save(doctor);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("deleting  doctor by id:  {}", id);
         doctorRepo.deleteById(id);
         return true;
    }


    private String setServerImageUrl()
    {
        String[] imageNames = {"profile1.png","profile2.png","profile3.png"};

        return ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/v1/doctor/image/"+ imageNames[new Random().nextInt(3)]).toUriString();

    }
}
