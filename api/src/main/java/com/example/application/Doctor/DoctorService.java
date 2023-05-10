package com.example.application.Doctor;

import java.util.Collection;

public interface DoctorService {


    Doctor create(Doctor doctor);
    Collection<Doctor> List(int limit);

    Doctor get(Long id);

    Doctor update(Doctor doctor);

    Boolean delete(Long id);





}
