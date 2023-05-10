package com.example.application.Doctor;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.TimeUnit;

import static java.time.LocalDateTime.now;
import static java.util.Map.of;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RequestMapping(path = "/api/v1/auth/doctor")
@CrossOrigin("http://localhost:8100/")
@RestController
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorServiceImpl doctorService;

    @GetMapping("/list")
    public ResponseEntity<GenericResponse> getDoctors() throws InterruptedException {
      //  TimeUnit.SECONDS.sleep(3);

        return ResponseEntity.ok(
                GenericResponse.builder()
                        .timestamp(now())
                        .data(of("doctors", doctorService.List(30)))
                        .message("Doctors retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build());
    }



    @GetMapping("/list/{id}")
    public ResponseEntity<GenericResponse> getDoctor(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                GenericResponse.builder()
                        .timestamp(now())
                        .data(of("doctors", doctorService.get(id)))
                        .message("Doctor once  retrieved")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()
        );
    }


    @GetMapping("/delete/{id}")
    public ResponseEntity<GenericResponse> deleteDoctor(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                GenericResponse.builder()
                        .timestamp(now())
                        .data(of("doctors", doctorService.delete(id)))
                        .message("Doctor once  deleted")
                        .status(OK)
                        .statusCode(OK.value())
                        .build()

        );

    }



    @GetMapping(path ="/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getDoctorImage(@PathVariable("fileName") String fileName) throws IOException {

        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+ "/Desktop/profile_image/"+fileName));

    }




    @PostMapping("/save")
    public ResponseEntity<GenericResponse> saveDoctor(@RequestBody @Valid Doctor doctor) {
        return ResponseEntity.ok(
                GenericResponse.builder()
                        .timestamp(now())
                        .data(of("doctors",doctorService.create(doctor)))
                        .message("Server created")
                        .status(CREATED)
                        .statusCode(CREATED.value())
                        .build()

        );
    }





}
