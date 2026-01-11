
package org.acme.interfaces.resources.responses;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.processing.Generated;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "fecha",
    "horaInicio",
    "horaFin",
    "cliente",
    "profesional",
    "estado"
})
@Generated("jsonschema2pojo")
public class RegistroReservaSchemaResponse {

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
    @JsonProperty("cliente")
    private ClienteSchemaResponse cliente;
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
    @JsonProperty("estado")
    private Estado estado;

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

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cliente")
    public ClienteSchemaResponse getCliente() {
        return cliente;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("cliente")
    public void setCliente(ClienteSchemaResponse cliente) {
        this.cliente = cliente;
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
    @JsonProperty("estado")
    public Estado getEstado() {
        return estado;
    }

    /**
     * 
     * (Required)
     * 
     */
    @JsonProperty("estado")
    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public enum Estado {

        CREADA("CREADA"),
        CANCELADA("CANCELADA"),
        COMPLETADA("COMPLETADA");
        private final String value;
        private final static Map<String, Estado> CONSTANTS = new HashMap<String, Estado>();

        static {
            for (Estado c: values()) {
                CONSTANTS.put(c.value, c);
            }
        }

        private Estado(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return this.value;
        }

        @JsonValue
        public String value() {
            return this.value;
        }

        @JsonCreator
        public static Estado fromValue(String value) {
            Estado constant = CONSTANTS.get(value);
            if (constant == null) {
                throw new IllegalArgumentException(value);
            } else {
                return constant;
            }
        }

    }

}
