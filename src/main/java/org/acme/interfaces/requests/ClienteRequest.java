package org.acme.interfaces.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.acme.reservas.api.beans.ClienteSchemaRequest;

public class ClienteRequest extends ClienteSchemaRequest {

    @Override
    @NotNull(message = "El campo nombres no puede ser nulo")
    @NotBlank(message = "El campo nombres no puede estar vacío o en blanco")
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
    @NotNull(message = "El campo email no puede ser nulo")
    @NotBlank(message = "El campo email no puede estar vacío o en blanco")
    @Email(message = "El campo emailAddress debe ser un email válido")
    public String getEmail() {
        return super.getEmail();
    }

    @Override
    @NotNull(message = "El campo telefono no puede ser nulo")
    @NotBlank(message = "El campo telefono no puede estar vacío o en blanco")
    public String getTelefono() {
        return super.getTelefono();
    }
}
