package org.acme.interfaces.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import org.acme.reservas.api.beans.HorarioDisponibleSchemaRequest;

public class HorarioDisponibleRequest extends HorarioDisponibleSchemaRequest {

    @Override
    @NotNull(message = "El campo profesionalId no puede ser nulo")
    @NotBlank(message = "El campo profesionalId no puede estar vacío o en blanco")
    public String getProfesionalId() {
        return super.getProfesionalId();
    }

    @Override
    @NotNull(message = "El campo fecha no puede ser nulo")
    @NotBlank(message = "El campo fecha no puede estar vacío o en blanco")
    public String getFecha() {
        return super.getFecha();
    }

    @Override
    @NotNull(message = "El campo horaInicio no puede ser nulo")
    public Date getHoraInicio() {
        return super.getHoraInicio();
    }

    @Override
    @NotNull(message = "El campo horaFin no puede ser nulo")
    public Date getHoraFin() {
        return super.getHoraFin();
    }
}
