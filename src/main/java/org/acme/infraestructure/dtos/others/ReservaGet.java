package org.acme.infraestructure.dtos.others;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaGet {
    String idClient;
    String idProfessional;
    String date;
    String specialty;
}
