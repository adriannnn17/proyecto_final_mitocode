package org.acme.interfaces.resources.requests;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import javax.annotation.processing.Generated;

import static org.acme.infraestructure.constants.Constant.*;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "profesionalId",
        "fecha",
        "horaInicio",
        "horaFin"
})
@Generated("jsonschema2pojo")
public class HorarioDisponibleSchemaRequest {

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
    @JsonProperty("profesionalId")
    @NotNull(message = "El campo 'profesionalId' no puede ser nulo")
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

}
