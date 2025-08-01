package clinic_registration.crud.service;

import clinic_registration.crud.entity.Doctor;
import clinic_registration.crud.entity.dto.DoctorDTO;
import clinic_registration.crud.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream().map(DoctorDTO::fromDoctor).toList();
    }

    public Optional<DoctorDTO> findDoctorById(Integer id) {
        return doctorRepository.findById(id).map(DoctorDTO::fromDoctor);
    }

    public Doctor saveDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        doctor.setFirstName(doctorDTO.getFirstName());
        doctor.setLastName(doctorDTO.getLastName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Integer id){
        if (!doctorRepository.existsById(id)){
            throw new IllegalArgumentException("Indicated ID not exists");
        }
        doctorRepository.deleteById(id);
    }


}
