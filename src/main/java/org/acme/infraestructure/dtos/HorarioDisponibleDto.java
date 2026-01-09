package org.acme.infraestructure.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HorarioDisponibleDto {
    private UUID id;
    private UUID profesionalId;
    private ProfesionalDto profesional;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}

