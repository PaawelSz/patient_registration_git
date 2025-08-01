package clinic_registration.crud.controller;

import clinic_registration.crud.entity.Doctor;
import clinic_registration.crud.entity.dto.DoctorDTO;
import clinic_registration.crud.service.DoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> allDoctors = doctorService.getAllDoctors();
        if (allDoctors == null) {
            throw new RuntimeException("There is any doctor in the base");
        }
        return new ResponseEntity<>(allDoctors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Integer id) {
        Optional<DoctorDTO> doctorById = doctorService.findDoctorById(id);
        if (doctorById.isPresent()) {
            return new ResponseEntity<>(doctorById.get(), HttpStatus.OK);
        } else
            log.info("The indicated doctor ID is not visible in the base");
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<DoctorDTO> addDoctor(@RequestBody DoctorDTO doctorDTO) {
        if (doctorDTO.getFirstName() == null || doctorDTO.getLastName() == null || doctorDTO.getSpecialization() == null) {
            return ResponseEntity.badRequest().build();
        } else {
            Doctor doctor = doctorService.saveDoctor(doctorDTO);
            return new ResponseEntity<>(DoctorDTO.fromDoctor(doctor), HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Integer id) {
        if (doctorService.findDoctorById(id).isPresent()) {
            doctorService.deleteDoctor(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}