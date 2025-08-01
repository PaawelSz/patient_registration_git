package clinic_registration.crud.repository;

import clinic_registration.crud.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Integer> {

    List<Appointment> findAllByDoctorId(Integer doctorId);
}
