package clinic_registration.crud.service;


import clinic_registration.crud.entity.Appointment;
import clinic_registration.crud.entity.Doctor;
import clinic_registration.crud.entity.Patient;
import clinic_registration.crud.entity.dto.AppointmentDTO;
import clinic_registration.crud.repository.AppointmentRepository;
import clinic_registration.crud.repository.DoctorRepository;
import clinic_registration.crud.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private final AppointmentRepository appointmentRepository;

    @Autowired
    private final PatientRepository patientRepository;

    @Autowired
    private final DoctorRepository doctorRepository;


    public AppointmentService(AppointmentRepository appointmentRepository, PatientRepository patientRepository, DoctorRepository doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.patientRepository = patientRepository;
        this.doctorRepository = doctorRepository;
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream().map(AppointmentDTO::fromAppointment).toList();
    }

    public Optional<AppointmentDTO> getAppointment(Integer id) {
        return appointmentRepository.findById(id).map(AppointmentDTO::fromAppointment);
    }

    public List<LocalDateTime> findAvailableTimes(Integer doctorId) {
        List<Appointment> allByDoctorId = appointmentRepository.findAllByDoctorId(doctorId);
        List<LocalDateTime> notAvailableTimes = allByDoctorId.stream()
                .map(Appointment::getAppointmentDate)
                .toList();
        List<LocalDateTime> availableTimes = new ArrayList<>();
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(7);

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            if (date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY) {
                continue;
            }
            LocalDateTime startTime = LocalDateTime.of(date, LocalTime.of(9, 0));
            LocalDateTime endTime = LocalDateTime.of(date, LocalTime.of(17, 0));

            for (LocalDateTime dateTime = startTime; dateTime.isBefore(endTime); dateTime = dateTime.plusMinutes(30)) {
                if (!notAvailableTimes.contains(dateTime)) {
                    availableTimes.add(dateTime);
                }
            }
        }

        return availableTimes;
    }

    public Appointment saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = new Appointment();
        appointment.setAppointmentDate(appointmentDTO.getAppointmentDate());
        appointment.setDescription(appointmentDTO.getDescription());

        Doctor doctor = doctorRepository.findById(appointmentDTO.getDoctorId()).orElseThrow(() -> new RuntimeException("missing doctor ID"));
        appointment.setDoctor(doctor);

        Patient patient = patientRepository.findById(appointmentDTO.getPatientId()).orElseThrow(() -> new RuntimeException("missing patient id"));
        appointment.setPatient(patient);

        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(Integer id){
        if (appointmentRepository.findById(id).isPresent()){
            appointmentRepository.deleteById(id);
        }else {
            throw new IllegalArgumentException();
        }

    }


}
