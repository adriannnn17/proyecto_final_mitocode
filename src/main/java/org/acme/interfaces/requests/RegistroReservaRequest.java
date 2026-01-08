package org.acme.interfaces.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Date;
import java.util.UUID;
import org.acme.reservas.api.beans.RegistroReservaSchemaRequest;

public class RegistroReservaRequest extends RegistroReservaSchemaRequest {

    @Override
    @NotNull(message = "El campo id no puede ser nulo")
    public UUID getId() {
        return super.getId();
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

    @Override
    @NotNull(message = "El campo clienteId no puede ser nulo")
    public UUID getClienteId() {
        return super.getClienteId();
    }

    @Override
    @NotNull(message = "El campo profesionalId no puede ser nulo")
    public UUID getProfesionalId() {
        return super.getProfesionalId();
    }
}
