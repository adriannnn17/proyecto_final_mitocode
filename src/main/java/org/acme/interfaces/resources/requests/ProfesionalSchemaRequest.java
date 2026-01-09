package org.acme.interfaces.resources.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nombres",
    "apellidos",
    "especialidad"
})
@Generated("jsonschema2pojo")
public class ProfesionalSchemaRequest {

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
    @JsonProperty("especialidad")
    private String especialidad;

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
    @JsonProperty("especialidad")
    @NotNull(message = "El campo 'especialidad' no puede ser nulo")
    @NotBlank(message = "El campo 'especialidad' no puede estar vacío o en blanco")
    public String getEspecialidad() {
        return especialidad;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("especialidad")
    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

}
