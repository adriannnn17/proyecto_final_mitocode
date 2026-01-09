
package org.acme.interfaces.resources.responses;

import java.util.Date;
import java.util.UUID;
import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.acme.reservas.api.beans.ProfesionalSchemaResponse;

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
    private org.acme.reservas.api.beans.ProfesionalSchemaResponse profesional;
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
    public org.acme.reservas.api.beans.ProfesionalSchemaResponse getProfesional() {
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
