package clinic_registration.crud.controller;


import clinic_registration.crud.entity.Appointment;
import clinic_registration.crud.entity.dto.AppointmentDTO;
import clinic_registration.crud.entity.dto.AppointmentErrorDTO;
import clinic_registration.crud.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("appointments")
public class AppointmentController {

    @Autowired
    private final AppointmentService appointmentService;


    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        List<AppointmentDTO> allAppointments = appointmentService.getAllAppointments();
        return new ResponseEntity<>(allAppointments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<AppointmentDTO> getAppointment(@PathVariable Integer id) {
        Optional<AppointmentDTO> appointment = appointmentService.getAppointment(id);
        if (appointment.isPresent()) {
            return new ResponseEntity<>(appointment.get(), HttpStatus.OK);
        } else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping("/available-appointments/{id}")
    ResponseEntity<List<LocalDateTime>> getAvailableAppointments(@PathVariable Integer id) {
        List<LocalDateTime> availableTimes = appointmentService.findAvailableTimes(id);
        return new ResponseEntity<>(availableTimes, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<?> addAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        List<LocalDateTime> availableTimes = appointmentService.findAvailableTimes(appointmentDTO.getDoctorId());
        if (availableTimes.contains(appointmentDTO.getAppointmentDate())) {
            Appointment appointment = appointmentService.saveAppointment(appointmentDTO);
            return new ResponseEntity<>(AppointmentDTO.fromAppointment(appointment), HttpStatus.CREATED);
        } else if (Objects.isNull(appointmentDTO.getAppointmentDate())  || Objects.isNull( appointmentDTO.getDoctorId())
                || Objects.isNull(appointmentDTO.getPatientId())) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.badRequest().body(AppointmentErrorDTO.builder().build());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteAppointment(@PathVariable Integer id){
        try {
            appointmentService.deleteAppointment(id);
            return new ResponseEntity<>("Appointment deleted", HttpStatus.OK);
        }
        catch (Exception e){
            return new ResponseEntity<>("Appointment does not exist",HttpStatus.NOT_FOUND);
        }
    }
}

