package org.acme.interfaces.resources.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.annotation.processing.Generated;
import java.util.Date;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("horaInicio")
    private Date horaInicio;
    /**
     * 
     * (Required)
     * 
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "UTC")
    @JsonProperty("horaFin")
    private Date horaFin;

    private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";

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

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("fecha")
    @NotNull(message = "El campo 'fecha' no puede ser nulo")
    @NotBlank(message = "El campo 'fecha' no puede estar vacío o en blanco")
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
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("horaInicio")
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("horaFin")
    @NotNull(message = "El campo 'horaFin' no puede ser nulo")
    public Date getHoraFin() {
        return horaFin;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("horaFin")
    public void setHoraFin(Date horaFin) {
        this.horaFin = horaFin;
    }

}
