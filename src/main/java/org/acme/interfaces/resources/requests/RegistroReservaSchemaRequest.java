package org.acme.interfaces.resources.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import javax.annotation.processing.Generated;

import static org.acme.infraestructure.constants.Constant.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "fecha",
        "horaInicio",
        "horaFin",
        "clienteId",
        "profesionalId"
})
@Generated("jsonschema2pojo")
public class RegistroReservaSchemaRequest {

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    private String id;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("fecha")
    private String fecha;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaInicio")
    private String horaInicio;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaFin")
    private String horaFin;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("clienteId")
    private String clienteId;
    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesionalId")
    private String profesionalId;

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    @NotNull(message = "El campo 'id' no puede ser nulo")
    @NotBlank(message = "El campo 'id' no puede estar vacío o en blanco")
    @Pattern(regexp = UUID_PATTERN, message = "El campo 'id' debe ser un UUID válido")
    public String getId() {
        return id;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("fecha")
    @NotNull(message = "El campo 'fecha' no puede ser nulo")
    @Pattern(regexp = DATE_PATTERN, message = "El campo 'fecha' debe tener formato YYYY-MM-DD (ej. 2026-01-10)")
    public String getFecha() {
        return fecha;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("fecha")
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaInicio")
    @NotNull(message = "El campo 'horaInicio' no puede ser nulo")
    @Pattern(regexp = TIME_PATTERN, message = "El campo 'horaInicio' debe tener formato HH:mm:ss (ej. 09:30:00)")
    public String getHoraInicio() {
        return horaInicio;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaInicio")
    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaFin")
    @NotNull(message = "El campo 'horaFin' no puede ser nulo")
    @Pattern(regexp = TIME_PATTERN, message = "El campo 'horaFin' debe tener formato HH:mm:ss (ej. 09:35:00)")
    public String getHoraFin() {
        return horaFin;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("horaFin")
    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("clienteId")
    @NotNull(message = "El campo 'clienteId' no puede ser nulo")
    @NotBlank(message = "El campo 'clienteId' no puede estar vacío o en blanco")
    @Pattern(regexp = UUID_PATTERN, message = "El campo 'clienteId' debe ser un UUID válido")
    public String getClienteId() {
        return clienteId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("clienteId")
    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesionalId")
    @NotNull(message = "El campo 'profesionalId' no puede ser nulo")
    @NotBlank(message = "El campo 'profesionalId' no puede estar vacío o en blanco")
    @Pattern(regexp = UUID_PATTERN, message = "El campo 'profesionalId' debe ser un UUID válido")
    public String getProfesionalId() {
        return profesionalId;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesionalId")
    public void setProfesionalId(String profesionalId) {
        this.profesionalId = profesionalId;
    }

}
