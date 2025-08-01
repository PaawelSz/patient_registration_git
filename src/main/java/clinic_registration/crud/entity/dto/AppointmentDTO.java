package clinic_registration.crud.entity.dto;

import clinic_registration.crud.entity.Appointment;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AppointmentDTO {

    private Integer id;
    private Integer patientId;
    private Integer doctorId;
    private LocalDateTime appointmentDate;
    private String description;


    public static AppointmentDTO fromAppointment(Appointment appointment) {
        return AppointmentDTO.builder()
                .id(appointment.getId())
                .patientId(appointment.getPatient().getId())
                .doctorId(appointment.getDoctor().getId())
                .appointmentDate(appointment.getAppointmentDate())
                .description(appointment.getDescription())
                .build();
    }

}