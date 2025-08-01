package clinic_registration.crud.repository;

import clinic_registration.crud.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Integer> {
  }