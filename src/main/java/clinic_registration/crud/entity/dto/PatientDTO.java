package clinic_registration.crud.entity.dto;

import clinic_registration.crud.entity.Patient;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PatientDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;


    public static PatientDTO fromPatient(Patient patient) {

        return PatientDTO.builder()
                .id(patient.getId())
                .firstName(patient.getFirstName())
                .lastName(patient.getLastName())
                .email(patient.getEmail())
                .build();
    }
}

