
package org.acme.interfaces.resources.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.acme.reservas.api.beans.ProfesionalSchemaResponse;

import javax.annotation.processing.Generated;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "id",
        "profesional",
        "fecha",
        "horaInicio",
        "horaFin"
})
@Generated("jsonschema2pojo")
public class HorarioDisponibleSchemaResponse {

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
    @JsonProperty("profesional")
    private ProfesionalSchemaResponse profesional;
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

    @JsonProperty("horaFin")
    private String horaFin;

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
    @JsonProperty("profesional")
    public ProfesionalSchemaResponse getProfesional() {
        return profesional;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("profesional")
    public void setProfesional(ProfesionalSchemaResponse profesional) {
        this.profesional = profesional;
    }

    /**
     *
     * (Required)
     *
     */
    @JsonProperty("fecha")
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
