package clinic_registration.crud.entity.dto;

import clinic_registration.crud.entity.Doctor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class DoctorDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String specialization;

    public static DoctorDTO fromDoctor(Doctor doctor) {
        return DoctorDTO.builder()
                .id(doctor.getId())
                .firstName(doctor.getFirstName())
                .lastName(doctor.getLastName())
                .specialization(doctor.getSpecialization())
                .build();
    }
}
