package org.acme.infraestructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfesionalDto {
    private UUID id;
    private String nombres;
    private String apellidos;
    private String especialidad;
}

