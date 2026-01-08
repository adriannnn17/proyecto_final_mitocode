package org.acme.interfaces.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.acme.reservas.api.beans.ProfesionalSchemaRequest;

public class ProfesionalRequest extends ProfesionalSchemaRequest {

    @Override
    @NotNull(message = "El campo nombres no puede ser nulo")
    @NotBlank(message = "El campo nombres no puede ser vacio, en blanco o nulo")
    public String getNombres() {
        return super.getNombres();
    }

    @Override
    @NotNull(message = "El campo apellidos no puede ser nulo")
    @NotBlank(message = "El campo apellidos no puede estar vacío o en blanco")
    public String getApellidos() {
        return super.getApellidos();
    }

    @Override
    @NotNull(message = "El campo especialidad no puede ser nulo")
    @NotBlank(message = "El campo especialidad no puede estar vacío o en blanco")
    public String getEspecialidad() {
        return super.getEspecialidad();
    }
}
