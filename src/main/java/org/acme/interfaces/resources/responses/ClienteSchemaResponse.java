
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
    "email",
    "telefono"
})
@Generated("jsonschema2pojo")
public class ClienteSchemaResponse {

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
    @JsonProperty("email")
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
