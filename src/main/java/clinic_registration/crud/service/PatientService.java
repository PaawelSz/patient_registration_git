package clinic_registration.crud.service;

import clinic_registration.crud.entity.Patient;
import clinic_registration.crud.repository.PatientRepository;
import clinic_registration.crud.entity.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PatientService {

    @Autowired
    final private PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public List<PatientDTO> getAllPatients(){
        return patientRepository.findAll().stream().map(PatientDTO::fromPatient).toList();
    }
    public Optional<PatientDTO> getPatientById (Integer id){
        return patientRepository.findById(id).map(PatientDTO::fromPatient);
    }
    public Patient savePatient(PatientDTO patientDto){
       Patient patient = new Patient();
       patient.setFirstName(patientDto.getFirstName());
       patient.setLastName(patientDto.getLastName());
       patient.setEmail(patientDto.getEmail());
       return patientRepository.save(patient);
    }

    public void deletePatient(Integer id){
        if (!patientRepository.existsById(id)){
            throw new IllegalArgumentException("Patient with indicated ID already not exists");
        }
        patientRepository.deleteById(id);
    }


}
