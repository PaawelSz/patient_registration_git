package clinic_registration.crud.entity.dto;

import lombok.Builder;
import lombok.Data;


@Builder
@Data
public class AppointmentErrorDTO {

    @Builder.Default
    private String errorCode = "401";
    @Builder.Default
    private String errorMessage = "Appointment does not exists :(";

}
