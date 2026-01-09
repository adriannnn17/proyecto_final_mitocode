package org.acme.interfaces.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nombres",
    "apellidos",
    "email",
    "telefono"
})
@Generated("jsonschema2pojo")
public class ClienteSchemaRequest {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("nombres")
    private String nombres;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("apellidos")
    private String apellidos;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    private String email;
    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("telefono")
    private String telefono;

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("nombres")
    @NotNull(message = "El campo 'nombres' no puede ser nulo")
    @NotBlank(message = "El campo 'nombres' no puede estar vacío o en blanco")
    public String getNombres() {
        return nombres;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("nombres")
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("apellidos")
    @NotNull(message = "El campo 'apellidos' no puede ser nulo")
    @NotBlank(message = "El campo 'apellidos' no puede estar vacío o en blanco")
    public String getApellidos() {
        return apellidos;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("apellidos")
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    @NotNull(message = "El campo 'email' no puede ser nulo")
    @NotBlank(message = "El campo 'email' no puede estar vacío o en blanco")
    @Email(message = "El campo 'email' debe ser un correo válido")
    public String getEmail() {
        return email;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("telefono")
    @NotNull(message = "El campo 'telefono' no puede ser nulo")
    @NotBlank(message = "El campo 'telefono' no puede estar vacío o en blanco")
    public String getTelefono() {
        return telefono;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("telefono")
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

}
