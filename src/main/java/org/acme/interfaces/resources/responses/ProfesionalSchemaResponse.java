
package org.acme.interfaces.resources.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "nombres",
    "apellidos",
    "especialidad"
})
@Generated("jsonschema2pojo")
public class ProfesionalSchemaResponse {

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    private UUID id;
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
    @JsonProperty("id")
    public UUID getId() {
        return id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("id")
    public void setId(UUID id) {
        this.id = id;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("nombres")
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
