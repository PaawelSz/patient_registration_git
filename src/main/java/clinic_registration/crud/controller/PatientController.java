package clinic_registration.crud.controller;

import clinic_registration.crud.entity.Patient;
import clinic_registration.crud.entity.dto.PatientDTO;
import clinic_registration.crud.repository.PatientRepository;
import clinic_registration.crud.service.PatientService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Slf4j
@RestController
@RequestMapping(path = "patients")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;


    @GetMapping
    public ResponseEntity<List<PatientDTO>> get() {
        List<PatientDTO> getAllPatients = patientService.getAllPatients();
        return new ResponseEntity<>(getAllPatients, HttpStatusCode.valueOf(200));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatient(@PathVariable Integer id) {
        Optional<PatientDTO> getPatient = patientService.getPatientById(id);
        if (getPatient.isPresent()) {
            return new ResponseEntity<>(getPatient.get(), HttpStatus.OK);
        } else {
            log.info("Brak wskaznego id w bazie");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping
    public ResponseEntity<PatientDTO> addPatient(@RequestBody PatientDTO patientDto){
        if (patientDto.getFirstName() == null || patientDto.getLastName() == null || patientDto.getEmail() == null){
            log.info("First name, last name and email are required");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }else {
            Patient savedPatient = patientService.savePatient(patientDto);
            return new ResponseEntity<>(PatientDTO.fromPatient(savedPatient),HttpStatus.CREATED);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable Integer id){
        if (patientRepository.existsById(id)){
            patientService.deletePatient(id);
            return ResponseEntity.noContent().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }


}
